package com.mx.quartz.listener;

import com.mx.quartz.service.IQuartzJobService;
import com.mx.spring.SpringContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author 小米线儿
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class QuartzListener implements ServletContextListener {

    private static final Log logger = LogFactory.getLog(QuartzListener.class);
    
    private IQuartzJobService quartzJobService;
    
    public void contextDestroyed(ServletContextEvent ctx) {
    	logger.info("QuartzListener stop: ");
    	if(null != quartzJobService){
    		quartzJobService.stopQuartzJob();
    		logger.info("QuartzListener stop done");
    	}
    }

    public void contextInitialized(ServletContextEvent ctx) {
        logger.info("QuartzListener startup: ");
        try {
        	quartzJobService =(IQuartzJobService) SpringContext.instance().get("quartzJobService");
            String root = ctx.getServletContext().getRealPath("/");
            String newPath = root+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"quartz-job.xml";
            logger.info("quartz-job:"+newPath);
            File file = new File(newPath);
            if(file.isFile()){
            	quartzJobService.AutoReplaceCronJob(newPath);
            	quartzJobService.addAutoQuartzJob(newPath);
            }else{
                throw new FileNotFoundException("quartz Job Configuration file not found!");
            }
        } catch (Exception e) {
            logger.error("QuartzListener error:"+e.getMessage() ,e);
        }
    }

}
