package com.mx.transport.zmq;


import com.mx.spring.SpringContext;
import com.mx.util.DateUtil;
import com.mx.util.FileUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ZMQRestartTask implements Runnable {
	private Map<String,String> configMap;
	private final String defaultTime = "23:59";
	private final String defaultEnable = "false";
	private boolean restartFlag = false;
	private String propertyfile;
	
	public ZMQRestartTask(String path){
		this.propertyfile = FileUtil.contact(path, "WEB-INF/classes/timetask.properties");
		configMap = new HashMap<String,String>();
		configMap.put("time", defaultTime);
		configMap.put("enable", defaultEnable);
	}
	
	public boolean isRestartTime(){
		boolean flag = false;
		try {
			loadPropertyMap(configMap);
			String enable = configMap.get("enable");
			String time = configMap.get("time");
			if("true".equals(enable)){
				String nowTime = DateUtil.format(new Date(), "HH:mm");
				//System.out.println(nowTime+" "+time);
				return nowTime.equals(time);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	private void loadPropertyMap(Map<String,String> map) throws FileNotFoundException, IOException {
		Properties config = new Properties();
		config.load(new FileInputStream(propertyfile));
		String zmqRestartTime = config.getProperty("zmqRestartTime",defaultTime);
		String zmqRestartEnable = config.getProperty("zmqRestartEnable",defaultEnable);
		map.put("time", zmqRestartTime);
		map.put("enable", zmqRestartEnable);
	}

	public void run() {
		while(!Thread.currentThread().isInterrupted()){
			if(!restartFlag){
				try {
					Thread.sleep(30000);
					if(isRestartTime()){
						restartFlag = true;
						Thread.sleep(60*1000);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				restartFlag = false;
				IZMQServer xmlServer = (IZMQServer) SpringContext.instance().get("zmqXmlServer");
				IZMQServer fileService = (IZMQServer)SpringContext.instance().get("zmqFileServer");
				IZMQServer messageService = (IZMQServer)SpringContext.instance().get("zmqMessageServer");
				
				System.out.println("----------------restart zeromq server begin------------------");
				fileService.stop();
				xmlServer.stop();
				messageService.stop();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fileService.start();
				xmlServer.start();
				messageService.start();
				System.out.println("----------------restart zeromq server end------------------");
			}
		}
	}

}
