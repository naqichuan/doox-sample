/*
 * Copyright 2017 nqcx.org All right reserved. This software is the confidential and proprietary information
 * of nqcx.org ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with nqcx.org.
 */

package nqcx.tools.aspect;

/**
 * @author naqichuan 17/11/13 13:59
 */
public class Aspect {

    public static void main(String[] args) {
        Aspect a = new Aspect();

        a.new ClassA().a();
        a.new ClassB().b();
    }

    class ClassA {
        public void a() {
            System.out.println("is a");
        }
    }

    class ClassB {

        public void b() {
            System.out.println("is b");
        }

    }


    class ClassAA extends ClassA {

        @Override
        public void a() {
            System.out.println("a");
            super.a();
        }
    }

}
