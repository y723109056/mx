
package com.mx.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.util.Calendar;

/**
 * @author 小米线儿
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class AutoCustomerJob extends QuartzJob{

    /**
     * @param arg0
     * @throws org.quartz.JobExecutionException
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @SuppressWarnings({ "deprecation", "unused" })
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.err.println(Calendar.getInstance().getTime().toLocaleString().toString()+"--------》自动任务开始执行");
        File file = new File("F:\\SVN.txt");
        boolean b=file.exists();
//        CallBackJob(arg0,b);
    }

}
