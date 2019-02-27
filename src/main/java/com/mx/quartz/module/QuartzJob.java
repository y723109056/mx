
package com.mx.quartz.module;

import com.mx.quartz.service.IQuartzJobService;
import com.mx.spring.SpringContext;
import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;


/**
 * @author 小米线儿
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public abstract class QuartzJob implements StatefulJob {
    
    public void CallBackJob(JobExecutionContext context, boolean bool){
        IQuartzJobService quartzJobService = (IQuartzJobService) SpringContext.instance().get("quartzJobService");
        if(quartzJobService!=null){
            quartzJobService.CallbackJob(context, bool);
        }
    }
}
