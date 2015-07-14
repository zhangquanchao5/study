package com.study.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by Star on 2015/7/14.
 */
@Component
public class SpringContextHelper implements ApplicationContextAware {

    private  ApplicationContext context ;

    /*
    * 注入ApplicationContext
    */
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        //在加载Spring时自动获得context
        this.context = context;
    }

    /**
     * Get application context.
     *
     * @return the application context
     */
    public ApplicationContext getApplicationContext(){
        return context;
    }

    /**
     * Get bean.
     *
     * @param beanName the bean name
     * @return the object
     */
    public  Object getBean(String beanName){
        return context.getBean(beanName);
    }
}
