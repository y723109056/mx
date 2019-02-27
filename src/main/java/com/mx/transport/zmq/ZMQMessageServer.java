package com.mx.transport.zmq;

import com.mx.enums.CommonFilePath;
import com.mx.spring.SpringContext;
import com.mx.transport.zmq.message.*;
import com.mx.util.DateUtil;
import com.mx.util.FileUtil;
import org.apache.log4j.Logger;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ZMQMessageServer extends ZMQServer implements Runnable {
	private static final Logger logger = Logger.getLogger(ZMQMessageServer.class);
	
	private short QUEUE_SIZE = 20;			//工作线程数
	private ZMQWorkerPool pool;
	private Map<String,FileProcessItem> files;
	private String uploadPath;
	private String port;
	private String enable;
	private List<IMessageListener> listeners;
	private Socket inSocket;
	private Socket outSocket;
	private IMessageParser parser;
	private long cumulative;			//累计文件传输大小
	private boolean running = false;
	private String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}
	
	public boolean enable(){
		return "true".equalsIgnoreCase(this.enable);
	}
	
	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	
	public long getCumulative() {
		return cumulative;
	}

	public void setThreadCount(Short threadCount){
		this.QUEUE_SIZE = threadCount;
	}
	
	public ZMQMessageServer(){
		this.port = "4343";
		this.running = false;
		this.cumulative = 0;
		this.enable = "true";
		this.parser = new MessageParser();
		this.files = new ConcurrentHashMap<String,FileProcessItem>();
	}
	
	public void start(){
		
		inSocket = ZMQServer.getContext().createSocket(ZMQ.ROUTER);  
		inSocket.bind("tcp://*:"+this.getPort());
		inSocket.setLinger(0);
		
		outSocket = ZMQServer.getContext().createSocket(ZMQ.DEALER);
		outSocket.bind("inproc://msg");
		outSocket.setLinger(0);
		
		pool = new ZMQWorkerPool(ZMQServer.getContext().getContext(),inSocket,outSocket,this,QUEUE_SIZE);
		
		pool.start();
		
		this.running = true;
		
		System.out.println("zeromq message server start with tcp://*:"+this.getPort());
		
	}
	
	public void stop(){
		try {
			
			if(this.running){
				
				pool.stop();
				
				this.running = false;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
            logger.error("zeromq message server stop error:" + e.getMessage(),e);
        }
	}
	
	@SuppressWarnings("rawtypes")
	public List<IMessageListener> getMessageListeners(){
		if(this.listeners==null){
			this.listeners = new ArrayList<IMessageListener>();
			Map map = SpringContext.instance().getMap(IMessageListener.class);
			for(Object obj : map.values()){
				IMessageListener listener = (IMessageListener)obj;
				this.listeners.add(listener);
			}
		}
		return this.listeners;
	}

	
	public void run() {
		
		Socket socket = ZMQServer.getContext().createSocket(ZMQ.REP);
		socket.connect("inproc://msg");
		socket.setLinger(0);
		int timeout  = 60 * 1000;
		socket.setReceiveTimeOut(timeout);
		
		System.out.println("[Thread-"+Thread.currentThread().getId()+"] start receive message thread");
		
		try{

			while(!Thread.currentThread().isInterrupted()){
				byte[] data= socket.recv(0);
				
				byte[] replay = new byte[0];
				
				if(data!=null){
					
					this.cumulative += data.length;
					
					if(data.length>0){
						try {
							IMessage message = parser.parser(data);
							if(message instanceof IBlobMessage){
								IBlobMessage msg = (IBlobMessage)message;
								String tempPath = this.getTempUploadPath(msg.getFix());
								String filename = FileUtil.contact(tempPath, msg.getMessageId() + ".temp");
								if(!files.containsKey(filename) || !new File(filename).exists()){
									files.put(filename,new FileProcessItem(filename,tempPath));
								}
								FileProcessItem item  = files.get(filename);
								item.saveFilePart(msg);
								if(item.isCompleted()){
									files.remove(filename);
									System.out.println(DateUtil.format(new Date(), DEFAULT_DATETIME_FORMAT)+" 接受文件完毕:"+filename);
								}
							}else if(message instanceof ITextMessage){
								ITextMessage msg = (ITextMessage)message;
								String msgId = msg.getMessageId();
								String text = msg.getContent();
								System.out.println(DateUtil.format(new Date(), DEFAULT_DATETIME_FORMAT)+" << "+text);
								for(IMessageListener listener : this.getMessageListeners()){
									String result = listener.onMessage(msgId,text);
									if(result!=null){
										ITextMessage replayMsg = new TextMessage(msgId);
										replayMsg.setContent(result);
										System.out.println(DateUtil.format(new Date(), DEFAULT_DATETIME_FORMAT)+" >> "+result);
										replay = replayMsg.getData();
										result = null;
										break;
									}
								}
							}
						}catch(IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							logger.error("zeromq消息通道接收消息 error:"+e.getMessage(),e);
						}catch(Exception e){
							e.printStackTrace();
							logger.error("zeromq消息通道接收消息 error:"+e.getMessage(),e);
						}
					}
					
					socket.send(replay,ZMQ.NOBLOCK);
				}
				
				replay = null;
				data = null;
				
			}
		}catch(Exception e){
			if(!this.filterErrorLog(e)) {
				e.printStackTrace();
				logger.error(e);
			}
		}finally{
			ZMQServer.getContext().destroySocket(socket);
		}
		
		System.out.println("[Thread-"+Thread.currentThread().getId()+"] stop receive message thread");
	}

	private boolean filterErrorLog(Exception e){
		if("org.zeromq.ZMQException".equals(e.getClass().getName())){
			return true;
		}else if("java.nio.channels.ClosedChannelException".equals(e.getClass().getName())){
			return true;
		}else if("java.nio.channels.ClosedByInterruptException".equals(e.getClass().getName())){
			return true;
		}
		return false;
	}
	
    private String getTempUploadPath(String fix){
		String path = FileUtil.contact(uploadPath, CommonFilePath.RECORD_TEMP_PATH.getPath());
		String type = (fix.charAt(0)=='.')?fix.substring(1):fix;
		path = FileUtil.contact(path, type);
		return path;
	}
	
	class FileProcessItem{
	    //private File file;
	    private int receiveCount;
	    private int partCount;
	    private String filename;
	    
	    public String getFilename(){
	    	return this.filename;
	    }

	    /// <summary>
	    /// 文件是否写入完毕
	    /// </summary>
	    public boolean isCompleted(){
	    	return this.receiveCount == this.partCount;
	    }

	    public FileProcessItem(String filename,String filePath){
	    	FileUtil.createDirectory(filePath);
	    	this.filename = filename;
	        //this.file = new File(filename);
	        this.receiveCount = 0;
	        this.partCount = -1;
	    }

	    public void saveFilePart(IBlobMessage msg) throws IOException{
			RandomAccessFile r = null;
			try{
				r = new RandomAccessFile(this.filename,"rwd");//只读方式打开文件
				int size = msg.getPartSize();
				int index = msg.getPartIndex();
				int pos = index*size;
				byte[] data = msg.getPartData();

				r.seek(pos);
				r.write(data);
			}catch(Exception e){
				e.printStackTrace();
				logger.error("文件分包写入 error:"+e.getMessage(),e);
			}finally{
				try{
					if(r!=null)r.close();
				}catch(Exception e){
					//nothing
				}
			}

			this.receiveCount++;
			this.partCount = msg.getPartCount();
		}
	}
}
