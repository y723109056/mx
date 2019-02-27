package com.mx.quartz.util;

import com.mx.quartz.constant.QuartzConstants;
import com.mx.quartz.listener.QuartzJobListener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 小米线儿
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class QuartzUtil {
    private static SchedulerFactory sf                 = new StdSchedulerFactory();
    private static String           JOB_GROUP_NAME     = "group1";
    private static String           TRIGGER_GROUP_NAME = "trigger1";

    /**
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     * @param jobName 任务名
     * @param job     任务
     * @param time    时间设置，参考quartz说明文档
     * @throws org.quartz.SchedulerException
     * @throws java.text.ParseException
     * @throws java.text.ParseException 
     */
    @SuppressWarnings("rawtypes")
	public static void addJob(String jobName, Job job, String time, Map<Object, Object> params)
                                                                                               throws Exception {
        Scheduler sched = sf.getScheduler();
        JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, job.getClass());//任务名，任务组，任务执行类
        JobDataMap dataMap = jobDetail.getJobDataMap();
        Iterator iter = params.keySet().iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            Object value = params.get(key);
            dataMap.put(key, value);
        }
        //触发器
        CronTrigger trigger = new CronTrigger(jobName, TRIGGER_GROUP_NAME);//触发器名,触发器组
        trigger.setCronExpression(time);//触发器时间设定
        
        //job监听
        JobListener jobListener = new QuartzJobListener("QuartzJobListener");
        sched.addJobListener(jobListener);
        jobDetail.addJobListener(jobListener.getName());
        
        sched.scheduleJob(jobDetail, trigger);
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
     * @throws org.quartz.SchedulerException
     * @throws java.text.ParseException
     * @throws java.text.ParseException
     */
    @SuppressWarnings("rawtypes")
	public static void addJob(String jobName, String jobGroupName, String triggerName,
                              String triggerGroupName, Job job, String jobType, Date startTime,
                              Date endTime, String time, Map<Object, Object> params)
                                                                                    throws SchedulerException,
                                                                                    ParseException {
        Scheduler sched = sf.getScheduler();
        if (sched.getTrigger(triggerName, triggerGroupName) == null) {//判断该任务是否已存在调度器中
            JobDetail jobDetail = new JobDetail(jobName, jobGroupName, job.getClass());//任务名，任务组，任务执行类
            JobDataMap dataMap = jobDetail.getJobDataMap();
            Iterator iter = params.keySet().iterator();
            while (iter.hasNext()) {
                Object key = iter.next();
                Object value = params.get(key);
                dataMap.put(key, value);
            }
            CronTrigger trigger = null;
            //如果定时模式是单次而不是重复
            if (jobType.equals("onetime")) {
                //触发器
                trigger = new CronTrigger(triggerName, triggerGroupName);//触发器名,触发器组
                trigger.setCronExpression(time);//触发器时间设定表达式
            } else {
                //触发器
                trigger = new CronTrigger(triggerName, triggerGroupName, jobName, jobGroupName,
                    startTime, endTime, time);//触发器名,触发器组

            }
            sched.scheduleJob(jobDetail, trigger);
            if (!sched.isShutdown())
                sched.start();
        }
    }

    /**
     * 修改定时任务
     * @param jobName 任务名
     * @param jobGroupName 任务组名
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名
     * @param jobType 定时类型 onetime,repeat
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param time 时间设置，参考quartz说明文档     
     * @throws org.quartz.SchedulerException
     * @throws java.text.ParseException
     * @throws java.text.ParseException
     */
    public static void modifyJob(String jobName, String jobGroupName, String triggerName,
                                 String triggerGroupName, String jobType, Date startTime,
                                 Date endTime, String time) throws SchedulerException,
                                                           ParseException {
        Scheduler sched = sf.getScheduler();
        Trigger trigger = sched.getTrigger(triggerName, triggerGroupName);
        if (trigger != null) {
            CronTrigger ct = (CronTrigger) trigger;
            //如果定时模式是单次而不是重复
            if (jobType.equals("onetime")) {
                ct.setCronExpression(time);
                sched.resumeTrigger(triggerName, triggerGroupName);
            } else {
                //修改时间
                ct.setCronExpression(time);
                ct.setStartTime(startTime);
                ct.setEndTime(endTime);
                //重启触发器
                sched.resumeTrigger(triggerName, triggerGroupName);
            }
        }

    }

    /**
     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     * @param jobName
     * @param time
     * @throws org.quartz.SchedulerException
     * @throws java.text.ParseException
     * @throws java.text.ParseException 
     */
    public static void modifyJobTime(String jobName, String time) throws SchedulerException,
                                                                 ParseException {
        Scheduler sched = sf.getScheduler();
        Trigger trigger = sched.getTrigger(jobName, TRIGGER_GROUP_NAME);
        if (trigger != null) {
            CronTrigger ct = (CronTrigger) trigger;
            ct.setCronExpression(time);
            sched.resumeTrigger(jobName, TRIGGER_GROUP_NAME);
        }
    }

    /**
     * 修改一个任务的触发时间
     * @param triggerName
     * @param triggerGroupName
     * @param time
     * @throws org.quartz.SchedulerException
     * @throws java.text.ParseException
     * @throws java.text.ParseException 
    */
    public static void modifyJobTime(String triggerName, String triggerGroupName, String time)
                                                                                              throws SchedulerException,
                                                                                              ParseException {
        Scheduler sched = sf.getScheduler();
        Trigger trigger = sched.getTrigger(triggerName, triggerGroupName);
        if (trigger != null) {
            CronTrigger ct = (CronTrigger) trigger;
            //修改时间
            ct.setCronExpression(time);
            //重启触发器
            sched.resumeTrigger(triggerName, triggerGroupName);
        }
    }

    public static void resumeJob(String triggerName, String triggerGroupName, String time)
                                                                                          throws SchedulerException,
                                                                                          ParseException {
        Scheduler sched = sf.getScheduler();
        Trigger trigger = sched.getTrigger(triggerName, triggerGroupName);
        if (trigger != null) {
            CronTrigger ct = (CronTrigger) trigger;
            //修改时间
            ct.setCronExpression(time);
            //重启触发器
            sched.resumeJob(triggerName, triggerGroupName);
        }
    }

    /**
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     * @param jobName
     * @throws org.quartz.SchedulerException
    */
    public static void removeJob(String jobName) throws SchedulerException {
        Scheduler sched = sf.getScheduler();
        sched.pauseTrigger(jobName, TRIGGER_GROUP_NAME);//停止触发器
        sched.unscheduleJob(jobName, TRIGGER_GROUP_NAME);//移除触发器
        sched.deleteJob(jobName, JOB_GROUP_NAME);//删除任务
    }

    /**
     * 移除一个任务
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @throws org.quartz.SchedulerException
    */
    public static void removeJob(String jobName, String jobGroupName, String triggerName,
                                 String triggerGroupName) throws SchedulerException {
        Scheduler sched = sf.getScheduler();
        sched.pauseTrigger(triggerName, triggerGroupName);//停止触发器
        sched.unscheduleJob(triggerName, triggerGroupName);//移除触发器
        sched.deleteJob(jobName, jobGroupName);//删除任务
    }

    public static String format(String str, Object... args) {
        return format(str, Pattern.compile("\\{(\\d+)\\}"), args);
    }

    /**
     * 字符串参数格式化
     * @param str
     * @param args
     * @return
     */
    public static String format(final String str, Pattern pattern, Object... args) {
        // 这里用于验证数据有效性
        if (str == null || "".equals(str))
            return "";
        if (args.length == 0) {
            return str;
        }

        String result = str;

        // 这里的作用是只匹配{}里面是数字的子字符串
        Pattern p = pattern;
        Matcher m = p.matcher(str);

        while (m.find()) {
            // 获取{}里面的数字作为匹配组的下标取值
            int index = Integer.parseInt(m.group(1));
            // 这里得考虑数组越界问题，{1000}也能取到值么？？
            if (index < args.length) {
                // 替换，以{}数字为下标，在参数数组中取值
                result = result.replace(m.group(), args[index].toString());
            } else {
                result = result.replace(m.group(), "");
            }
        }
        return result;
    }

    /**
     * 把数字转换成周(英文)
     * @param days
     * @return
     */
    public static String formatWeek(String days) {
        String day = "";
        if (days != null && !days.equals("")) {
            String ds[] = days.split(",");
            if (ds.length == 1) {
                day = week2String(ds[0]);
            } else {
                for (String s : ds) {
                    day = day + week2String(s) + ",";
                }
                day = day.substring(0, day.lastIndexOf(","));
            }
        } else {
            day = "*";
        }
        return day;
    }

    /**
     * 周转换
     * @param day
     * @return
     */
    private static String week2String(String day) {
        if ("1".equalsIgnoreCase(day))
            return QuartzConstants.MONDAY;
        else if ("2".equalsIgnoreCase(day))
            return QuartzConstants.TUESDAY;
        else if ("3".equalsIgnoreCase(day))
            return QuartzConstants.WEDNESDAY;
        else if ("4".equalsIgnoreCase(day))
            return QuartzConstants.THURSDAY;
        else if ("5".equalsIgnoreCase(day))
            return QuartzConstants.FRIDAY;
        else if ("6".equalsIgnoreCase(day))
            return QuartzConstants.SATURDAY;
        else if ("7".equalsIgnoreCase(day))
            return QuartzConstants.SUNDAY;
        return "";
    }

    /**
     * 获取当前时间几分钟后的时间
     * @param minute 分
     * @return
     */
    public static String getDateMinut(int minute) {
        SimpleDateFormat format = new SimpleDateFormat("mm HH");// 24小时制   
        String time = format.format(Calendar.getInstance().getTime());
        Date date = null;
        try {
            date = format.parse(time);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null)
            return "";
        //System.out.println("front:" + format.format(date));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);// 24小时制    
        date = cal.getTime();
        //System.out.println("after:" + format.format(date));
        cal = null;
        return format.format(date);

    }
    
   
    /**  
     * 将日期转换为日历 
     * @param date 日期 
     * @return 日历 
     */  
    private static Calendar convert(Date date) {   
        Calendar calendar = Calendar.getInstance();   
        calendar.setTime(date);   
        return calendar;   
    }

    /**
     * 返回当月最后一天的日期
     * @return
     */
    public static String getLastDayOfMonth(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = convert(Calendar.getInstance().getTime());   
        calendar.set(Calendar.DATE, calendar.getMaximum(Calendar.DATE));
        String date=format.format(calendar.getTime());
        return date;
    }
    
    /**
     * 关闭quartz added by huyb
     */
    public static void shutdown(){
    	try {
			Scheduler sched = sf.getScheduler();
			if(sched.isStarted()){
				sched.shutdown();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
    }
}
