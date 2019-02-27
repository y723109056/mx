package com.mx.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.util.*;

/**
 * HTTP 请求工具类 简化的httpClient调用
 *
 */
public class HttpUtil {
	private static final Logger logger = Logger.getLogger("简化的httpClient工具类");
	private static PoolingHttpClientConnectionManager connMgr;
	private static RequestConfig requestConfig;
	private static final int MAX_TIMEOUT = 7000;
	private final static String PROTOCAL_NAME = "SSL";
	static {
		// 设置连接池
		connMgr = new PoolingHttpClientConnectionManager();
		// 设置连接池大小
		connMgr.setMaxTotal(100);
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());
		connMgr.setValidateAfterInactivity(30000);
		requestConfig = RequestConfig.custom().setConnectTimeout(MAX_TIMEOUT).setSocketTimeout(MAX_TIMEOUT)
				// 设置从连接池获取连接实例的超时
				.setConnectionRequestTimeout(MAX_TIMEOUT).build();
	}

	// 自定义超时时间
	public static void setTimeOut(int timeOut) {
		requestConfig = RequestConfig.custom().setConnectTimeout(timeOut).setSocketTimeout(timeOut)
				// 设置从连接池获取连接实例的超时
				.setConnectionRequestTimeout(timeOut).build();
	}

	/**
	 * 发送 GET 请求（HTTP），不带输入数据
	 *
	 * @param url
	 * @return
	 */
	public static String doGet(String url) {
		return doGet(url, new HashMap<String, Object>());
	}

	/**
	 * 发送 GET 请求（HTTP），K-V形式
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doGet(String url, Map<String, Object> params) {
		String apiUrl = url;
		StringBuffer param = new StringBuffer();
		int i = 0;
		for (String key : params.keySet()) {
			if (i == 0)
				param.append("?");
			else
				param.append("&");
			param.append(key).append("=").append(params.get(key));
			i++;
		}
		apiUrl += param;
		String result = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpGet = new HttpGet(apiUrl);
			httpGet.setConfig(requestConfig);
			HttpResponse response = httpclient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();

			logger.info(String.format("执行状态码 : %s ", statusCode));
			result = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return result;
	}


	/**
	 * 发送 POST 请求（HTTP），不带输入数据
	 *
	 * @param apiUrl
	 * @return
	 */
	public static String doPost(String apiUrl) {
		return doPost(apiUrl, new HashMap());
	}

	/**
	 * 发送 POST 请求（HTTP），K-V形式
	 *
	 * @param apiUrl
	 *            API接口URL
	 * @param params
	 *            参数map
	 * @return
	 */
	public static String doPost(String apiUrl, Map<String, Object> params) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;

		try {
			httpPost.setConfig(requestConfig);
			List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("GBK")));
			response = httpClient.execute(httpPost);
			logger.info(String.format("response : %s ", response.toString()));
			HttpEntity entity = response.getEntity();
			httpStr = EntityUtils.toString(entity, "GBK");
		} catch (IOException e) {
			logger.error(e.toString());
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					logger.error(e.toString());
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 POST 请求（HTTP），JSON形式
	 *
	 * @param apiUrl
	 * @param json
	 *            json对象
	 * @return
	 */
	public static String doPost(String apiUrl, Object json) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String httpStr = null;
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;

		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			logger.info(String.format("执行状态码 : %s", response.getStatusLine().getStatusCode()));
			httpStr = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			logger.error(e.toString());
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					logger.error(e.toString());
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 SSL POST 请求（HTTPS），K-V形式
	 *
	 * @param apiUrl
	 *            API接口URL
	 * @param params
	 *            参数map
	 * @return
	 */
	public static String doPostSSL(String apiUrl, Map<String, Object> params) {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
				.setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		String httpStr = null;

		try {
			httpPost.setConfig(requestConfig);
			List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairList.add(pair);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					logger.error(e.toString());
				}
			}
		}
		return httpStr;
	}

	/**
	 * 发送 SSL POST 请求（HTTPS），JSON形式
	 *
	 * @param apiUrl
	 *            API接口URL
	 * @param json
	 *            JSON对象
	 * @return
	 */
	public static String doPostSSL(String apiUrl, Object json) {
		return doPostSSL(apiUrl, json, createSSLConnSocketFactory());
	}

	/**
	 * 发送 SSL POST 请求（HTTPS），JSON形式
	 *
	 * @param apiUrl
	 *            API接口URL
	 * @param json
	 *            JSON对象
	 * @return
	 */
	public static String doPostSSL(String apiUrl, Object json, String certFile, String hostName) {
		return doPostSSL(apiUrl, json, createSSLConnSocketFactory(certFile, hostName));
	}
	/**
	 * 发送 SSL POST 请求（HTTPS），JSON形式
	 *
	 * @param apiUrl
	 *            API接口URL
	 * @param json
	 *            JSON对象
	 * @return
	 */
	public static String doPostSSL(String apiUrl, Object json, SSLConnectionSocketFactory sslSocketFactory) {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslSocketFactory)
				.setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpResponse response = null;
		String httpStr = null;

		try {
			httpPost.setConfig(requestConfig);
			StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			httpStr = EntityUtils.toString(entity, "utf-8");
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					logger.error(e.toString());
				}
			}
		}
		return httpStr;
	}

	/**
	 * 创建SSL安全连接 无证书信任所有站点
	 * 
	 * @return
	 */
	private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
		return SSLConnectionSocketFactory.getSocketFactory();
	}

	/**
	 * 创建SSL安全连接 带证书校验站点
	 * 
	 * @return
	 */
	private static SSLConnectionSocketFactory createSSLConnSocketFactory(String certFile, String hostName) {
		SSLConnectionSocketFactory sslConnectionSocketFactory = createSSLConnSocketFactory();
		try {
			final String hostNameA = hostName;

			SSLContext sslContext = SSLContext.getInstance(PROTOCAL_NAME);
			X509TrustManager tm = new LocalX509TrustManager(certFile);
			sslContext.init(null, new TrustManager[]{tm}, new SecureRandom());// TrustSelfSignedStrategy
			sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {// 信任站点
					// if (arg0.equals("192.168.2.177") ||
					// arg0.equals("cyber-space2015.imwork.net"))
					if (arg0.equals(hostNameA) || arg0.equals(hostNameA))
						return true;
					else
						return false;
				}
			});
		} catch (Exception e) {
			logger.error(String.format("创建ssl链接失败!证书文件:%s,信任站点:%s", certFile, hostName));
		}
		return sslConnectionSocketFactory;
	}

	/**
	 * 测试方法
	 *
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
//		String url = "https://api.newdun.com/domain.info";
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("user_token", "NS8OEDDSNMK7ZLB55FEZSDCO7B62UKW1");
//		params.put("email", "huyb@baihang-china.com");
//		params.put("domain", "zhudb.com");
//		String str = doPostSSL(url, params);
		String url = "http://whois.pconline.com.cn/ip.jsp";
		System.out.println(doPost(url));
	}
	/**
	 * 带header的get请求
	 *
	 * @param url
	 *            服务器地址
	 * @param headers
	 *            添加的请求header信息
	 * @param params
	 *            请求参数
	 * @return 返回服务器响应的文本，出错抛出Exception异常
	 * @throws Exception
	 */
	public static String getData(String url, Map<String, String> headers, Map<String, Object> params) throws Exception {
		StringBuffer param = new StringBuffer();
		int i = 0;
		for (String key : params.keySet()) {
			if (i == 0)
				param.append("?");
			else
				param.append("&");
			param.append(key).append("=").append(params.get(key));
			i++;
		}
		url += param;
		long start = System.currentTimeMillis();
		HttpGet httpGet = new HttpGet(url);
		if (headers != null && headers.size() > 0) {
			Set<Map.Entry<String, String>> set = headers.entrySet();
			for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();) {
				Map.Entry<String, String> header = it.next();
				if (header != null) {
					httpGet.setHeader(header.getKey(), header.getValue());
				}
			}
		}
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			String info = EntityUtils.toString(entity, "UTF-8");
			return info;
		} catch (Exception e) {
			logger.debug(String.format("getData Exception url: %s", url), e);
			throw new Exception(url + "dajie getData exception：", e);
		} finally {
			httpGet.releaseConnection();
			long interval = System.currentTimeMillis() - start;
			logger.debug(String.format("%s 请求耗时： %s", url, interval));
		}
	}

	// 从服务器获得一个文件输入流
	public static InputStream getInputStream(String ossUrl) {
		InputStream inputStream = null;
		HttpURLConnection httpURLConnection;

		try {
			URL url = new URL(ossUrl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			// 设置网络连接超时时间
			httpURLConnection.setConnectTimeout(MAX_TIMEOUT);
			// 设置应用程序要从网络连接读取数据
			httpURLConnection.setDoInput(true);

			httpURLConnection.setRequestMethod("GET");
			int responseCode = httpURLConnection.getResponseCode();
			if (responseCode == 200) {
				// 从服务器返回一个输入流
				inputStream = httpURLConnection.getInputStream();
			}
		} catch (MalformedURLException e) {
			logger.error(e.toString());
		} catch (IOException e) {
			logger.error(e.toString());
		}

		return inputStream;

	}
}