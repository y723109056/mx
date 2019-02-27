package com.mx.transport.zmq.message;

public interface IMessageParser {
	
	IMessage parser(byte[] data);
	
}
