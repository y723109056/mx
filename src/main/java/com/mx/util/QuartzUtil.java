
package com.mx.util;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * @author 小米线儿
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class QuartzUtil {

    private static SchedulerFactory sf                 = new StdSchedulerFactory();
    private static String           JOB_GROUP_NAME     = "group1";
    private static String           TRIGGER_GROUP_NAME = "trigger1";

    /** */
    /**
    * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
    * @param jobName 任务名
    * @param job     任务
    * @param time    时间设置，参考quartz说明文档
    * @throws SchedulerException
    * @throws java.text.ParseException
     * @throws java.text.ParseException 
    */
    public static void addJob(String jobName, Job job, String time, Map<Object, Object> params) throws Exception{
    	removeJob(jobName);
        Scheduler sched = sf.getScheduler();
        sched.startDelayed(60000);
        JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, job.getClass());//任务名，任务组，任务执行类
//        jobDetail.setRequestsRecovery(false);
        JobDataMap dataMap = jobDetail.getJobDataMap();
        if(params!=null){
	        Iterator<?> iter = params.keySet().iterator();
	        while (iter.hasNext()) {
	            Object key = iter.next();
	            Object value = params.get(key);
	            dataMap.put(key, value);
	        }
        }
        //触发器
        CronTrigger cronTrigger = new CronTrigger(jobName, TRIGGER_GROUP_NAME);//触发器名,触发器组
//        cronTrigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
        cronTrigger.setCronExpression(time);//触发器时间设定
        sched.scheduleJob(jobDetail, cronTrigger);
        //启动
        if (!sched.isShutdown())
            sched.start();
    }

    /**
     * 添加一个定时任务
     * @param jobName 任务名
     * @param jobGroupName 任务组名
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名
     * @param job 任务
     * @param jobType 定时类型 onetime,repeat
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param time 时间设置，参考quartz说明文档     
     * @param params 任务需要的参数
     * @throws SchedulerException
     * @throws java.text.ParseException
     * @throws java.text.ParseException
     */
    public static void addJob(String jobName, String jobGroupName, String triggerName,
                              String triggerGroupName, Job job, String jobType, Date startTime,
                              Date endTime, String time, Map<Object, Object> params) throws SchedulerException, ParseException{
    	Scheduler sched = sf.getScheduler();  
        if (sched.getTrigger(triggerName, triggerGroupName) == null) {//判断该任务是否已存在调度器中
            JobDetail jobDetail = new JobDetail(jobName, jobGroupName, job.getClass());//任务名，任务组，任务执行类
            JobDataMap dataMap = jobDetail.getJobDataMap();
            Iterator<?> iter = params.keySet().iterator();
            while (iter.hasNext()) {
                Object key = iter.next();
                Object value = params.get(key);
                dataMap.put(key, value);
            }
            CronTrigger cronTrigger = null;
            //如果定时模式是单次而不是重复
            if (jobType.equals("onetime")) {
                //触发器
                cronTrigger = new CronTrigger(triggerName, triggerGroupName);//触发器名,触发器组
                cronTrigger.setCronExpression(time);//触发器时间设定表达式
            } else {
                //触发器
                cronTrigger = new CronTrigger(triggerName, triggerGroupName, jobName, jobGroupName,
                    startTime, endTime, time);//触发器名,触发器组

            }
            sched.scheduleJob(jobDetail, cronTrigger);
            if (!sched.isShutdown())
                sched.start();
        }
    }

    /** */
    /**
    * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
    * @param jobName
    * @throws SchedulerException
    */
    public static void removeJob(String jobName) throws SchedulerException {
        Scheduler sched = sf.getScheduler();
        sched.pauseTrigger(jobName, TRIGGER_GROUP_NAME);//停止触发器
        sched.unscheduleJob(jobName, TRIGGER_GROUP_NAME);//移除触发器
        sched.deleteJob(jobName, JOB_GROUP_NAME);//删除任务
    }

    /** */
    /**
    * 移除一个任务
    * @param jobName
    * @param jobGroupName
    * @param triggerName
    * @param triggerGroupName
    * @throws SchedulerException
    */
    public static void removeJob(String jobName, String jobGroupName, String triggerName,
                                 String triggerGroupName) throws SchedulerException {
        Scheduler sched = sf.getScheduler();
        sched.pauseTrigger(triggerName, triggerGroupName);//停止触发器
        sched.unscheduleJob(triggerName, triggerGroupName);//移除触发器
        sched.deleteJob(jobName, jobGroupName);//删除任务
    }
}
