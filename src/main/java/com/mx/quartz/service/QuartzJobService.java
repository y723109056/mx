package com.mx.quartz.service;

import com.mx.quartz.job.ReplaceCornJob;
import com.mx.quartz.listener.QuartzListener;
import com.mx.quartz.util.QuartzUtil;
import com.mx.spring.SpringContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author 小米线儿
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class QuartzJobService implements IQuartzJobService {

    private static final Logger logger = LoggerFactory.getLogger(QuartzListener.class);

    /**
     * 启动定时数据接口任务
     * @param config
     * @throws org.quartz.SchedulerException
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	public void addAutoQuartzJob(String config) throws SchedulerException, Exception {
        String jobName = "";
        String job = "";
        String time = "";
        String delay = "";
        //格式： [秒] [分] [小时] [日] [月] [周]
        // 0 {0} {1} ? * * 此处是占位符时间为XML配置的时间触发
        String cronExpression = "0 {0} {1} {2} {3} {4}";
        String cornTime = "";
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
                    jobName = it.element("job-name").getText();
                    job = it.element("job-bean").getText();
                    params.put(jobName+"_bean", job);
                    
                    //延迟处理
                    if(it.element("delay")!=null){
                        delay = it.element("delay").getText();
                        params.put(jobName+"_delay", delay);
                    }
                    
                    //返回job实例
                    Object obj = SpringContext.instance().get(job);
                    time = it.element("time").getText();
                    String[] times = time.split(":");
                    
                    if(it.getName().equals("month")){
                        //0 15 10 15 * ? 每月15号上午10点15分触发 
                        cronExpression = "0 {0} {1} {2} * ?";
                        String numbers = it.element("numbers").getText();
                        cornTime = QuartzUtil.format(cronExpression, times[1], times[0], numbers);
                    }else if(it.getName().equals("week")){
                        //0 15 10 ? * MON-FRI 从周一到周五每天上午的10点15分触发
                        cronExpression = "0 {0} {1} ? * {2}";
                        String days = it.element("days").getText();
                        cornTime = QuartzUtil.format(cronExpression, times[1], times[0], QuartzUtil.formatWeek(days));
                    }else if(it.getName().equals("day")){
                        Element round = it.element("round");
                        if(round != null){//轮循
                            if(round.element("minute")!=null){
                                String minute = round.element("minute").getText();
                                cornTime = "0 0/"+minute+" * * * ?";
                            }else if(round.element("second")!=null){
                                String second = round.element("second").getText();
                                cornTime = "0/"+second+" * * * * ?";
                            }
                        }else{
                            cronExpression = "0 {0} {1} ? * *";
                            cornTime = QuartzUtil.format(cronExpression, times[1], times[0]);
                        }
                    }
                    params.put(jobName+"_cornTime", cornTime);
                    //移除任务定时器
                    QuartzUtil.removeJob(jobName);
                    //加入定时任务
                    QuartzUtil.addJob(jobName, (Job) obj, cornTime, params);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            logger.error("addAutoQuartzJob error："+e.getMessage(),e);
        } catch (DocumentException e) {
            e.printStackTrace();
            logger.error("addAutoQuartzJob error："+e.getMessage(),e);
        }
    }

    /**
     * 监听业务job处理状态
     * @param context job运行时的信息
     * @param bool 业务job处理结果
     */
    public void CallbackJob(JobExecutionContext context, boolean bool) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String jobName = context.getJobDetail().getName();
        jobDataMap.remove(jobName+"_Flag");
        jobDataMap.put(jobName+"_Flag", bool);   
        context.getJobDetail().setJobDataMap(jobDataMap);
    }

    /**
     * 启动时间特俗处理Job
     */
    public void AutoReplaceCronJob(String config) throws SchedulerException, Exception {
        String jobName="AutoReplaceCronJob";
        String cronExpression = "0 05 0 * * ?";
        Map<Object, Object> params = new HashMap<Object, Object>();
        //移除任务定时器
        QuartzUtil.removeJob(jobName);
        //加入定时任务
        params.put("AutoReplaceCronJob_config", config);
        QuartzUtil.addJob(jobName, new ReplaceCornJob(), cronExpression, params);
    }

	@Override
	public void stopQuartzJob() {
		logger.info("QuartzJobService stopQuartzJob");
		QuartzUtil.shutdown();
	}
}
