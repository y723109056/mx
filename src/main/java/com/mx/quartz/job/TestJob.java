package com.mx.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author 小米线儿
 * @time 2019/2/24 0024
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class TestJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("test job is running......");
    }
}
