package com.mx.quartz.job;

import com.mx.quartz.util.QuartzUtil;
import com.mx.spring.SpringContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import java.io.File;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author 小米线儿
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class ReplaceCornJob implements Job {

    @SuppressWarnings("rawtypes")
	public void execute(JobExecutionContext context) throws JobExecutionException {
        String config = context.getJobDetail().getJobDataMap().get("AutoReplaceCronJob_config").toString();
        String lastTime="";
        
        String jobName = "";
        String job = "";
        String delay = "";
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(new File(config));
            Element root = document.getRootElement();
            List trigger = root.elements("trigger");
            for(Iterator i = trigger.iterator();i.hasNext();) {
                Element items = (Element) i.next();
                List item = items.elements();
                for(Iterator x = item.iterator();x.hasNext();) {
                    Map<Object, Object> params = new HashMap<Object, Object>();
                    Element it = (Element) x.next();
                    if(it.getName().equals("month") || it.getName().equals("day")){
                        if(it.element("lastDayTime")!=null){
                            /**
                             * 首先处理初始设定存入JobDateMap中
                             */
                            jobName = it.element("job-name").getText();
                            job = it.element("job-bean").getText();
                            params.put(jobName+"_bean", job);
                            
                            String time = it.element("time").getText();
                            String[] tms = time.split(":");
                            String initcronExp= "";
                            String initCornTime = "";
                            
                            if(it.getName().equals("month")){
                                initcronExp= "0 {0} {1} {2} * ?";
                                String initNumbers = it.element("numbers").getText();
                                initCornTime = QuartzUtil.format(initcronExp, tms[1], tms[0], initNumbers);
                            }else if(it.getName().equals("day")){
                                initcronExp = "0 {0} {1} ? * *";
                                initCornTime = QuartzUtil.format(initcronExp, tms[1], tms[0]);
                            }
                            
                            params.put(jobName+"_cornTime", initCornTime);
                            
                            //延迟处理
                            if(it.element("delay")!=null){
                                delay = it.element("delay").getText();
                                params.put(jobName+"_delay", delay);
                            }
                            //返回job实例
                            Object obj = SpringContext.instance().get(job);
                            
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            String date = format.format(Calendar.getInstance().getTime());
                            String lastDay = QuartzUtil.getLastDayOfMonth();
                            
                            if(date.equals(lastDay)){//当前系统时间和设定的时间一致,重设时间
                                lastTime = it.element("lastDayTime").getText();
                                String cronExpression = "0 {0} {1} * * ?";
                                String[] times = lastTime.split(":");
                                String cornTime = QuartzUtil.format(cronExpression, times[1], times[0]);
                                QuartzUtil.removeJob(jobName);
                                QuartzUtil.addJob(jobName, (Job)obj, cornTime, params);
                            }else{
                                //恢复初始时间设定
                                QuartzUtil.removeJob(jobName);
                                QuartzUtil.addJob(jobName, (Job)obj, initCornTime, params);
                            }
                        }
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
