/* 
 * Copyright 2012-2013 nqcx.org All right reserved. This software is the 
 * confidential and proprietary information of nqcx.org ("Confidential 
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with nqcx.org.
 */

package nqcx.worker.startup;

import nqcx.worker.spring.LoadBeans;
import org.nqcx.worker.test.TestInterface;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author naqichuan Nov 27, 2013
 */
public final class Bootstrap extends LoadBeans {

    @Autowired
    private TestInterface testInterface;

    public void testMethod() {
        testInterface.callTestMethod();
    }

    public static void main(String[] args) {
        Bootstrap b = new Bootstrap();
        b.testMethod();
    }
}
