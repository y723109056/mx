/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :ZeroMQ消息收发队列封装
 * Author     :zhusw
 * Create Date:2013-7-30
 */

package com.mx.transport.zmq;

import org.apache.log4j.Logger;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZMQQueue;

import java.util.ArrayList;
import java.util.List;

public class ZMQWorkerPool extends ZMQQueue {
	private static final Logger logger = Logger.getLogger(ZMQWorkerPool.class);
	private List<Thread> workThreads;
	private Thread poolThread;
	private Socket inSocket;
	private Socket outSocket;
	
	/**
	 * 
	 * @param context 设备上下文
	 * @param inSocket 消息入站
	 * @param outSocket	消息出站
	 * @param worker 消息线程
	 * @param workerCount 工作线程序
	 */
	public ZMQWorkerPool(Context context, Socket inSocket, Socket outSocket ,Runnable worker, short workerCount) {
		super(context, inSocket, outSocket);
		this.inSocket = inSocket;
		this.outSocket = outSocket;
		workThreads = new ArrayList<Thread>();
		for (short count = 0; count < workerCount; count++) {
			Thread thread = new Thread(worker);
			workThreads.add(thread);
	    }
		poolThread = new Thread(this);
	}
	
	public void start(){
		poolThread.start();
		for(Thread thread : workThreads){
			thread.start();
		}
	}
	
	public void stop() {
		poolThread.interrupt();
		for(Thread thread : workThreads){
			thread.interrupt();
		}
		this.close();
	}
	
	@Override
	public void run(){
		try {
			super.run();
		}
		catch(Exception e){

		}
		//}catch(ZMQException e){
		//	if(e.getErrorCode() != 128){
		//		throw e;
		//	}
		//}
	}
	
	public void close(){
		//try {
			//super.close();
			
			ZMQServer.getContext().destroySocket(inSocket);
			ZMQServer.getContext().destroySocket(outSocket);
			
		//} catch (IOException e) {
		//	logger.error("close zeromq pool error:"+e.getMessage(), e);
		//}
	}

}
