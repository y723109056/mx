package com.mx.transport.zmq;

public interface IMessageListener {
	
	/**
	 * 监听处理消息
	 * @return 注:返回值为 null表示不处理该消息,而非空字符串
	 */
	String onMessage(String messageId, String content);
	
}
