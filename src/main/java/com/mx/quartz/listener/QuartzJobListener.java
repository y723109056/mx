package com.mx.quartz.listener;

import com.mx.quartz.util.QuartzUtil;
import com.mx.spring.SpringContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 小米线儿
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class QuartzJobListener implements JobListener {
    Log logger = LogFactory.getLog(QuartzJobListener.class);

    private String listenerName = "QuartzJobListener";  
    
    public QuartzJobListener(String listenerName) {  
        this.listenerName = listenerName;  
    }  
      
    public String getName() {  
        return this.listenerName;  
    }  

    //job执行之前调用
    public void jobToBeExecuted(JobExecutionContext context) {
    }

    //Job执行被拒接时调用
    public void jobExecutionVetoed(JobExecutionContext context) {
        String jobName = context.getJobDetail().getName();
        logger.info(jobName + " was vetoed and not executed()");
    }

    //job执行之后调用
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String jobName = context.getJobDetail().getName();
        
        if(context.getJobDetail().getJobDataMap().get(jobName+"_Flag")!=null){
            boolean b = Boolean.parseBoolean(context.getJobDetail().getJobDataMap().get(jobName+"_Flag").toString());
            //立即重新运行
            //jobException.setRefireImmediately(true);//打火策略
            String job = context.getJobDetail().getJobDataMap().get(jobName+"_bean").toString();
            String cornTime = context.getJobDetail().getJobDataMap().get(jobName+"_cornTime").toString();
            //返回job实例
            Object obj = SpringContext.instance().get(job);
            Map<Object, Object> params = new HashMap<Object, Object>();
            params.put(jobName+"_bean", job);
            params.put(jobName+"_cornTime", cornTime);
            
            //延迟处理
            String delay="";
            if(context.getJobDetail().getJobDataMap().get(jobName+"_delay")!=null){
                delay = context.getJobDetail().getJobDataMap().get(jobName+"_delay").toString();
                params.put(jobName+"_delay", delay);
            }
            
            try {
                if(!b){
                    //重设时间
                    String cron="";
                    if(delay!=""){
                        cron = "0 "+ QuartzUtil.getDateMinut(Integer.parseInt(delay))+" * * ?";
                    }else{
                        cron = cornTime;
                    }
                    QuartzUtil.removeJob(jobName);
                    QuartzUtil.addJob(jobName, (Job)obj, cron, params);
                }else{
                    //恢复初始时间设定
                    QuartzUtil.removeJob(jobName);
                    QuartzUtil.addJob(jobName, (Job)obj, cornTime, params);
                }
            } catch (SchedulerException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
