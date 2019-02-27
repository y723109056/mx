/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :ZeroMQ消息监听
 * Author     :zhusw
 * Create Date:2013-7-30
 */

package com.mx.transport.zmq;

import com.mx.spring.SpringContext;
import com.mx.util.SystemUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ZMQServerListener implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(ZMQServerListener.class);
    private List<IZMQServer> servers;
    
	public void contextInitialized(ServletContextEvent ctx) {
		try {
			
			String root = ctx.getServletContext().getRealPath("/");

			/*
			if(ctx!=null){
				if(SystemUtil.isWindows()){
					String jdkBit = System.getProperty("sun.arch.data.model");
					String newPath = FileUtil.contact(root,"WEB-INF/lib/x"+(jdkBit.equals("64")?jdkBit:"86"));
					String dllFile = newPath+"\\libzmq.dll";
					this.load(dllFile);
					this.load(newPath+"\\msvcr100.dll");
					LibUtil.setLibraryPath(newPath);
					LibUtil.addLibraryPath(newPath);
				}else{
					String jdkBit = System.getProperty("sun.arch.data.model");
					String newPath = FileUtil.contact(root,"WEB-INF/lib/x"+(jdkBit.equals("64")?jdkBit:"86"));
					this.load(newPath+"/libzmq.so");
					LibUtil.setLibraryPath(newPath);
					LibUtil.addLibraryPath(newPath);
				}
			}
			*/
			
			this.initManyOpenFiles();
			
			ZMQServer.getContext();
			
			System.out.println("------------------- zeromq servers starting ----------------------");
			
			this.servers = new ArrayList<IZMQServer>();
			this.servers.add((IZMQServer) SpringContext.instance().get("zmqMessageServer"));
            
			for(IZMQServer server : servers){
				if(server.enable())server.start();
			}
			
			//new Thread(new ZMQRestartTask(root)).start();
			
			System.out.println("------------------- zeromq servers running ----------------------");
			
		} catch (Exception e) {
			System.out.println(e);
            logger.error("ZMQServerListener start error:" + e.getMessage(),e);
        }
	}
	
	private void load(String lib){
		try {
			System.load(lib);
		}catch (Exception e) {
			System.out.println(e);
			logger.error("ZMQServerListener start error:" + e.getMessage(),e);
		}
	}
	
	private void initManyOpenFiles(){
		if(!SystemUtil.isWindows()){
			try {
		    	File file=new File("/etc/security/limits.conf");
		    	FileInputStream stream=new FileInputStream(file);
		    	InputStreamReader inputReader = new InputStreamReader(stream);
		    	BufferedReader reader=new BufferedReader(inputReader);
		    	String line;
		    	boolean hasSet = false;
				while ((line = reader.readLine()) != null) {
					if(line.indexOf("65536")>-1){
						hasSet = true;
						break;
					}
				}
				stream.close();
				inputReader.close();
				reader.close();
				
				if(!hasSet){
					FileOutputStream fos = new FileOutputStream(file,true);
					OutputStreamWriter writer = new OutputStreamWriter(fos);
					writer.write("*	soft	nofile	65536\n");
					writer.write("*	hard	nofile	65536\n");
					writer.close();
					fos.close();
				}
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			for(IZMQServer server : servers){
				//server.stop();
			}
			//ZMQServer.getContext().destroy();
		} catch (Exception e) {
			System.out.println(e);
            logger.error("ZMQServerListener stop error:" + e.getMessage(),e);
        }
	}
	
}
