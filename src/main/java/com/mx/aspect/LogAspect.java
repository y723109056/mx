package com.mx.aspect;


import com.mx.dao.ActionLogMapper;
import com.mx.entity.ActionLog;
import com.mx.util.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 小米线儿
 * @time 2019/2/12 0012
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */
public class LogAspect {

    @Autowired
    private ActionLogMapper actionLogMapper;

    private final static Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    private String requestUrl = "";

    private long start;

    private long end;

    private HttpServletRequest request = null;

    private Integer userId = null;

    public void before(JoinPoint joinPoint) {
        start = System.currentTimeMillis();
    }

    public void after(JoinPoint joinPoint) {
        end = System.currentTimeMillis();

        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        request = servletRequestAttributes.getRequest();

        requestUrl = request.getRequestURL().toString();



        if(StringUtil.isNotEmpty(request.getParameter("userId"))){
            userId = Integer.valueOf(request.getParameter("userId"));
        }

        Object target = joinPoint.getTarget();

        //方法级 和 类级
        //1.若类有@ActionTime则方法下全部标中
        Class<?> clazz = target.getClass();

        String name = "";
        if(clazz.isAnnotationPresent(ActionTime.class)){
            printLog(joinPoint,clazz.getAnnotation(ActionTime.class).name());
        }else {
            //2.方法级只针对该方法
            MethodSignature signature = (MethodSignature)joinPoint.getSignature();
            ActionTime annotation = signature.getMethod().getAnnotation(ActionTime.class);
            if(annotation != null){
                printLog(joinPoint,annotation.name());
            }
        }
    }


    private void printLog(JoinPoint joinPoint,String memo){

        actionLogMapper.insert(new ActionLog(joinPoint.getSignature().toShortString(),(end-start)+"ms",requestUrl,userId,memo));

        LOG.info("RequestUrl:"+requestUrl+",执行方法："+joinPoint.getSignature().toShortString()+",操作时间:"+(end-start)+"ms,memo="+memo);

    }

}
