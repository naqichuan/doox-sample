/*
 * Copyright 2017 nqcx.org All right reserved. This software is the confidential and proprietary information
 * of nqcx.org ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with nqcx.org.
 */

package nqcx.tools.jdk8;

/**
 * @author naqichuan 2017/11/14 09:42
 */
public interface JDK8Interface2 {
    //接口中可以定义静态方法了
    static void staticMethod() {
        System.out.println("接口中的静态方法");
    }

    //使用default之后就可以定义普通方法的方法体了
    default void defaultMethod() {
        System.out.println("接口中的默认方法");
    }
}
