package com.mx.transport.zmq;

import org.zeromq.ZContext;

public abstract class ZMQServer implements IZMQServer {
	private static ZContext context;
	
	public static ZContext getContext(){
		if(context==null){
			context = new ZContext();
			context.setIoThreads(4);
			//context.setMain(false);
		}
		return context;
	}

}
