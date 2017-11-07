/* 
 * Copyright 2012-2013 nqcx.org All right reserved. This software is the 
 * confidential and proprietary information of nqcx.org ("Confidential 
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with nqcx.org.
 */

package nqcx.worker.spring;


import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 类LoadBean.java的实现描述：加载 Spring 配置
 * 
 * @author huangbaoguang 2012-10-10 下午9:10:51
 */
public class LoadBeans {

    protected ApplicationContext applicationContext = null;

    {
        applicationContext = new ClassPathXmlApplicationContext(new String[] { "beans.xml" });
        applicationContext.getAutowireCapableBeanFactory().autowireBeanProperties(this,
                DefaultListableBeanFactory.AUTOWIRE_BY_NAME, false);
    }
}
