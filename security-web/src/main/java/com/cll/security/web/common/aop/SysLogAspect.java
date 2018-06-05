package com.cll.security.web.common.aop;

import com.cll.security.web.common.express.SysLogExpressContext;
import com.cll.security.web.entity.SysLog;
import com.cll.security.web.repository.SysLogRepository;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author chenliangliang
 * @date 2018/6/4
 */
@Component
@Aspect
public class SysLogAspect {


    @Autowired
    private SysLogRepository logRepository;

    @Autowired
    private SysLogExpressContext expressContext;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    private ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();

    @Pointcut("@annotation(com.cll.security.web.common.annotation.SysLog)")
    public void logPointCut() {
    }


    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {


        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        taskExecutor.execute(() -> handleLog(joinPoint, result, start));

        return result;

    }

    private void handleLog(ProceedingJoinPoint joinPoint, Object result, long start) {
        int time = (int) (System.currentTimeMillis() - start);

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        String[] parameterNames = discoverer.getParameterNames(method);
        Object[] parameters = joinPoint.getArgs();
        for (int i = 0, l = parameterNames.length; i < l; i++) {
            expressContext.addVariable("$" + String.valueOf(i + 1), parameters[i]);
            expressContext.addVariable(parameterNames[i], parameters[i]);
        }

        expressContext.addVariable("returnObject", result);
        com.cll.security.web.common.annotation.SysLog log = method.getAnnotation(com.cll.security.web.common.annotation.SysLog.class);

        String appid = log.appid();
        if (StringUtils.isNotBlank(appid)) {
            appid = expressContext.parseSpel(appid).toString();
        }

        String data = log.data();
        if (StringUtils.isNotBlank(data)) {
            data = expressContext.parseSpel(data).toString();
        }

        String sevid = log.sevid();
        if (StringUtils.isNotBlank(sevid)) {
            sevid = expressContext.parseSpel(sevid).toString();
        }

        String level = log.level();

        SysLog sysLog = new SysLog();
        sysLog.setStartTime(new Date(start));
        sysLog.setConsume(time);
        sysLog.setAppid(appid);
        sysLog.setSevid(sevid);
        sysLog.setData(data);
        sysLog.setLevel(level);


        SysLog save = logRepository.save(sysLog);
        System.out.println(save);

        for (int i = 0, l = parameterNames.length; i < l; i++) {
            expressContext.removeVariable("$" + String.valueOf(i + 1));
            expressContext.removeVariable(parameterNames[i]);
        }
        expressContext.removeVariable("returnObject");
    }
}
