package com.mx.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamUtil {
	public static String read(InputStream stream) throws IOException
	{
		return read(stream,"utf-8");
	}
	
	public static String read(InputStream stream,String encoding) throws IOException{
		BufferedReader reader=new BufferedReader(new InputStreamReader(stream,encoding));
		String line;
		StringBuilder sb=new StringBuilder();
		while ((line = reader.readLine()) != null) {
			sb.append(line+"");
		}
		reader.close();
		return sb.toString();
	}
}
