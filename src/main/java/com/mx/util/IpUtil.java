package com.mx.util;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpUtil {
	
	private static String ip = "127.0.0.1";

	public static String getIp() {
		if ("127.0.0.1".equals(ip)) {
			ip = getLocalIP();
		}
		return ip;
	}

	/**
	 * 获得主机IP
	 * 
	 * @return String
	 */
	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

	/**
	 * 获取本机ip地址，并自动区分Windows还是linux操作系统
	 * 
	 * @return String
	 */
	public static String getLocalIP() {
		String sIP = "";
		InetAddress ip = null;
		try {
			// 如果是Windows操作系统
			if (isWindowsOS()) {
				ip = InetAddress.getLocalHost();
			}
			// 如果是Linux操作系统
			else {
				boolean bFindIP = false;
				Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
						.getNetworkInterfaces();
				while (netInterfaces.hasMoreElements()) {
					if (bFindIP) {
						break;
					}
					NetworkInterface ni = (NetworkInterface) netInterfaces
							.nextElement();

					// ----------特定情况，可以考虑用ni.getName判断
					// 遍历所有ip
					Enumeration<InetAddress> ips = ni.getInetAddresses();
					while (ips.hasMoreElements()) {
						ip = (InetAddress) ips.nextElement();

						//if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
						if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1 && ni.getName().indexOf("eth0")>-1) {
							bFindIP = true;
							break;
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null != ip) {
			sIP = ip.getHostAddress();
		}
		return sIP;
	}
	
	public static String getMacAddress() {
		StringBuffer sb = new StringBuffer();
		try {
			InetAddress ia = InetAddress.getLocalHost();
			// 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
			byte[] mac = NetworkInterface.getByInetAddress(ia)
					.getHardwareAddress();

			// 下面代码是把mac地址拼装成String
			for (int i = 0; i < mac.length; i++) {
				// mac[i] & 0xFF 是为了把byte转化为正整数
				String s = Integer.toHexString(mac[i] & 0xFF);
				sb.append(s.length() == 1 ? 0 + s : s);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();

	}
	
	/**
	 * 判断Ip在范围内
	 * @param ip
	 * @param beginIp
	 * @param endIp
	 * @return
	 */
	public static boolean inIpRange(String ip,String beginIp,String endIp){
		Long p1 = getIpLongValue(ip);
		Long p2 = getIpLongValue(beginIp);
		Long p3 = getIpLongValue(endIp);
		if(p1>0 && p2>0 && p3>0 && ((p1>p2 && p1<p3) || (p1>p3 && p1<p2))){
			return true;
		}
		return false;
	}
	
	/**
	 * 得到Ip数字
	 * @param ip
	 * @return
	 */
	public static Long getIpLongValue(String ip){
		Long intVal = 0L;
		if(!StringUtil.isNullOrEmpty(ip)&&isIpv4(ip)){
			String part[] = ip.split("\\.");
			if(part.length==4){
				intVal+=Long.parseLong(part[0])*256*256*256;
				intVal+=Long.parseLong(part[1])*256*256;
				intVal+=Long.parseLong(part[2])*256;
				intVal+=Long.parseLong(part[3]);
			}
			if(intVal > Integer.MAX_VALUE){
				return intVal % Integer.MAX_VALUE;
			}
		}else if(!StringUtil.isNullOrEmpty(ip)&&!isIpv4(ip)){//查询时，如果IP地址不合法的时候，查询结果为空
			intVal=1L;
		}
		return intVal;
	}

	/**
	 * 验证IP地址是否合法
	 * @param ipAddress
	 * @return
	 */
	public static boolean isIpv4(String ipAddress) {
		String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+"(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		Pattern pattern = Pattern.compile(ip);
		Matcher matcher = pattern.matcher(ipAddress);
		return matcher.matches();

	}

	/**
	 * @param urlStr
	 *            请求的地址
	 * @param content
	 * @author www.zuidaima.com
	 *            请求的参数 格式为：name=xxx&pwd=xxx
	 * @param encoding
	 *            服务器端请求编码。如GBK,UTF-8等
	 * @return
	 */
	private static String getResult(String urlStr, String content, String encoding) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();// 新建连接实例
			connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
			connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒
			connection.setDoOutput(true);// 是否打开输出流 true|false
			connection.setDoInput(true);// 是否打开输入流true|false
			connection.setRequestMethod("POST");// 提交方法POST|GET
			connection.setUseCaches(false);// 是否缓存true|false
			connection.connect();// 打开连接端口
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());// 打开输出流往对端服务器写数据
			out.writeBytes(content);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx
			out.flush();// 刷新
			out.close();// 关闭输出流
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据,以BufferedReader流来读取
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();// 关闭连接
			}
		}
		return null;
	}

	/**
	 * @Title: decodeUnicode
	 * @author kaka
	 * @Description: unicode转换成中文
	 * @param @param theString
	 * @param @return
	 * @return String
	 * @throws
	 */
	private static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
							case '0':
							case '1':
							case '2':
							case '3':
							case '4':
							case '5':
							case '6':
							case '7':
							case '8':
							case '9':
								value = (value << 4) + aChar - '0';
								break;
							case 'a':
							case 'b':
							case 'c':
							case 'd':
							case 'e':
							case 'f':
								value = (value << 4) + 10 + aChar - 'a';
								break;
							case 'A':
							case 'B':
							case 'C':
							case 'D':
							case 'E':
							case 'F':
								value = (value << 4) + 10 + aChar - 'A';
								break;
							default:
								throw new IllegalArgumentException("Malformed encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}

	public static String getAddress(){
		try {
			return getAddresses(null,"utf-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	/**
	 *
	 * @param content
	 *            请求的参数 格式为：name=xxx&pwd=xxx
	 * @param encoding
	 *            服务器端请求编码。如GBK,UTF-8等
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getAddresses(String content, String encodingString) throws UnsupportedEncodingException {
		String result = null;
		StringBuffer sb = new StringBuffer();
		// 这里调用pconline的接口
		String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
		// 从http://whois.pconline.com.cn取得IP所在的省市区信息
		String returnStr = getResult(urlStr, content, encodingString);
		if (returnStr != null) {
			// 处理返回的省市区信息
			String[] temp = returnStr.split(",");
			if(temp.length<3){
				return "0";//无效IP，局域网测试
			}

			String country = "";
			String area = "";
			String region = "";
			String city = "";
			String county = "";
			String isp = "";
			Boolean flag = false;
			for(int i=0;i<temp.length;i++){
				if(flag){
					return result;
				}
				switch(i){
					case 1:
						country = (temp[i].split(":"))[2].replaceAll("\"", "");
						country = decodeUnicode(country);//国家
						if(country.equals("未分配或者内网IP")){
							flag = true;
							sb.append(country);
							result = sb.toString();
							break;
						}
						sb.append("国家:");
						sb.append(country);
						sb.append(",");
						break;
					case 3:
						area =(temp[i].split(":"))[1].replaceAll("\"", "");
						area = decodeUnicode(area);//地区
						break;
					case 5:
						region = (temp[i].split(":"))[1].replaceAll("\"", "");
						region = decodeUnicode(region);//省份
						sb.append("省份:");
						sb.append(region);
						sb.append(",");
						break;
					case 7:
						city = (temp[i].split(":"))[1].replaceAll("\"", "");
						city = decodeUnicode(city);//市区
						sb.append("城市:");
						sb.append(city);
						sb.append(",");
						break;
					case 9:
						county = (temp[i].split(":"))[1].replaceAll("\"", "");
						county = decodeUnicode(county);//地区
						sb.append("地区:");
						sb.append(county);
						break;
					case 11:
						isp = (temp[i].split(":"))[1].replaceAll("\"", "");
						isp = decodeUnicode(isp);//ISP公司
						break;
				}
			}
			result = sb.toString();
		}
		return result;
	}

	public static String getCity(){
		String urlStr = "http://whois.pconline.com.cn/ip.jsp";
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();// 新建连接实例
			connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
			connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒
			connection.setDoOutput(true);// 是否打开输出流 true|false
			connection.setDoInput(true);// 是否打开输入流true|false
			connection.setRequestMethod("POST");// 提交方法POST|GET
			connection.setUseCaches(false);// 是否缓存true|false
			connection.connect();// 打开连接端口
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), "utf-8"));// 往对端写完数据对端服务器返回数据,以BufferedReader流来读取
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();// 关闭连接
			}
		}
		return null;
	}

}
