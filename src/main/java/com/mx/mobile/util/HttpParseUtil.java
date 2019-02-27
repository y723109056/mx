package com.mx.mobile.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpParseUtil {
	private static final Logger log = LoggerFactory.getLogger(HttpParseUtil.class);

	/**
	 * 获取http请求数据流
	 * 
	 * @param request
	 * @return
	 */
	public static String getJsonStr(HttpServletRequest request) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
			String line = "";
			// 使用默认容量，16字节
			StringBuffer buf = new StringBuffer();
			while ((line = br.readLine()) != null) {
				buf.append(line);
			}
			// 是否有数据传入
			if (buf.length() <= 0) {
				return null;
			} else {
				//日志移动后面一起打印
				log.info("接收的报文={}", buf.toString());
				return buf.toString();
			}
		} catch (Exception e) {
			log.error("获取http请求数据异常：{}", e.getMessage());
			return null;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					log.error("BufferedReader关闭异常：" + e.getMessage());
					return null;
				}
			}
		}
	}
}
