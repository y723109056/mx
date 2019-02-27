package com.mx.quartz.job;

import java.io.File;
import java.util.Calendar;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 小米线儿
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class AutoImportJob extends QuartzJob {
    private static final Logger logger = LoggerFactory.getLogger(AutoImportJob.class);

    /**
     * @param arg0
     * @throws org.quartz.JobExecutionException
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @SuppressWarnings({ "deprecation", "unused" })
	public void execute(JobExecutionContext context) throws JobExecutionException {
        String txt="In　QuartzJob　-　executing　its　JOB　at "
            + Calendar.getInstance().getTime().toLocaleString().toString()+" by "+context.getTrigger().getName();
        logger.info(txt);
        File file = new File("F:\\SVN.txt");
        boolean b=file.exists();
//        CallBackJob(context,b);
    }
}
