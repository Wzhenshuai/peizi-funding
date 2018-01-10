package com.icaopan.framework.beanfactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

//@Service
public class BeanFactory implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private static ApplicationContext applicationContext;

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
        Object object = null;
        try {
            object = applicationContext.getBean(clazz);
        } catch (NoSuchBeanDefinitionException e) {
            logger.error(e.getMessage());
        }
        return (T) object;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String clazz) {
        Object object = null;
        try {
            object = applicationContext.getBean(clazz);
        } catch (NoSuchBeanDefinitionException e) {
            logger.error(e.getMessage());
        }
        return (T) object;
    }

    @Override
    @SuppressWarnings("all")
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }
}
