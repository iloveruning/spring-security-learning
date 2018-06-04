package com.cll.security.web.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author chenliangliang
 * @date 2018/6/4
 */
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.context=applicationContext;
    }

    public static Object getBean(String beanName) {
        assertApplicationContext();
        return  context.getBean(beanName);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertApplicationContext();
        return context.getBean(requiredType);
    }

    public static ApplicationContext getContext(){
        return context;
    }

    private static void assertApplicationContext() {
        if (SpringContextHolder.context == null) {
            throw new RuntimeException("applicaitonContext属性为null,请检查是否注入了SpringContextHolder!");
        }
    }
}
