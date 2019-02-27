package com.mx.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XStreamUtil {
	
	private static XStream stream;
	
	public static XStream getXStream(){
		if(stream==null)stream=new XStream(new DomDriver("utf-8"));
		return stream;
	}
	
	/**
	 * 把对像序例化为Xml字符串
	 * @param obj
	 * @return
	 */
	public static String toXML(Object obj){
		return getXStream().toXML(obj);
	}
	
	/**
	 * 把Xml字符串反序例化为对像
	 * @param xml
	 * @return
	 */
	public static Object formXML(String xml){
		return getXStream().fromXML(xml);
	}
}
