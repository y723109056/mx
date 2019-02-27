/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :This is Class Description...
 * Author     :huyb
 * Create Date:2014-5-23
 */
package com.mx.listener;

import com.mx.sql.util.SqlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class DataSourceListener implements ServletContextListener {
	
	 private static final Logger logger = LoggerFactory.getLogger(DataSourceListener.class);
	 
	/**
	 * @param arg0
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	/**
	 * @param arg0
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("DataSourceListener initialized");
		try{
			SqlHelper.testConnection();
		}catch(Exception e){
			e.printStackTrace();
		}
		logger.info("DataSourceListener initialize done");
	}

}
