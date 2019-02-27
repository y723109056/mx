package com.mx.sql.config;


import com.mx.sql.SqlDriverException;
import com.mx.util.StringUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

public class DriverDbConfig extends DbConfig {
	protected String password;
	protected String username;
	protected String url;
	protected String jar;
	protected Driver driver;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getJar() {
		return jar;
	}

	public void setJar(String jar) {
		this.jar = jar;
	}

	public DriverDbConfig(){
		this.url = "";
		this.name = "";
		this.password = "";
		this.username = "";
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		if(this.dataSource!=null)return super.getConnection();
		else{
			if(StringUtil.isNullOrEmpty(this.url))throw new SqlDriverException("数据库连接配置 url属性不能为空");
			if(StringUtil.isNullOrEmpty(this.getDriverClass()))throw new SqlDriverException("数据库连接配置 driverClass为空");
			try {
				Driver driver = null;
				if(this.driver==null){
					URLClassLoader classLoader = new URLClassLoader(new URL[]{new URL("file:/"+this.getJar())});
					Class<?> driverClass = classLoader.loadClass(this.getDriverClass());
					if(driverClass==null)throw new SqlDriverException("找不jdbc驱动类:"+this.getDriverClass());
					driver = (Driver)driverClass.newInstance();
				}
				Properties properties = new Properties();
				properties.setProperty("user",this.username);
				properties.setProperty("password", this.password);
				return driver.connect(this.url, properties);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new SqlDriverException("jdbc驱动包不正确:"+this.getJar(),e);
			} catch (ClassNotFoundException e) {
				throw new SqlDriverException("找不jdbc驱动类:"+this.getDriverClass(),e);
			} catch (InstantiationException e) {
				throw new SqlDriverException("实例化jdbc驱动类失败:"+this.getDriverClass(),e);
			} catch (IllegalAccessException e) {
				throw new SqlDriverException("访问jdbc驱动类出错:"+this.getDriverClass(),e);
			}
		}
	}
}
