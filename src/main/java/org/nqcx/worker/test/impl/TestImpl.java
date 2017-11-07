/*
 * Copyright 1998-2012 360buy.com All right reserved. This software is the
 * confidential and proprietary information of 360buy.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with 360buy.com.
 */
package org.nqcx.worker.test.impl;

import org.nqcx.worker.test.TestInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类TestImpl.java的实现描述：用于测试实现类
 *
 * @author huangbaoguang 2012-10-11 下午3:12:02
 */
public class TestImpl implements TestInterface {

    protected static final Logger logger = LoggerFactory
            .getLogger(TestImpl.class);

    private String zipDemonTest;

    @Override
    public void callTestMethod() {
        logger.info("Began to execute method!");

        System.out
                .println("**********************************************************");
        System.out.println("zipDemonTest = " + zipDemonTest);
        System.out
                .println("**********************************************************");

        logger.info("Execution method end!");
    }

    public void setZipDemonTest(String zipDemonTest) {
        this.zipDemonTest = zipDemonTest;
    }
}
