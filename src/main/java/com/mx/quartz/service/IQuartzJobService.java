package com.mx.quartz.service;

import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;

/**
 * @author 小米线儿
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public interface IQuartzJobService {
    /**
     * 启动定时数据接口任务
     * @param config
     * @throws org.quartz.SchedulerException
     * @throws Exception
     */
    public void addAutoQuartzJob(String config) throws SchedulerException, Exception;
    
    /**
     * 启动时间特俗处理Job
     * @throws org.quartz.SchedulerException
     * @throws Exception
     */
    public void AutoReplaceCronJob(String config)throws SchedulerException, Exception;
    
    /**
     * 监听业务job处理状态
     * @param context job运行时的信息
     * @param bool 业务job处理结果
     */
    public void CallbackJob(JobExecutionContext context, boolean bool);
    
    /**
     * 停止全部Job
     */
    public void stopQuartzJob();
}
