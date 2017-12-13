/*
 * Copyright 2017 nqcx.org All right reserved. This software is the confidential and proprietary information
 * of nqcx.org ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with nqcx.org.
 */

package org.nqcx.worker.ob;

import java.util.Observable;

public class ObserverTest extends Observable {

    {
        addObserver((o, a) -> {
            System.out.println(a);
            System.out.println("1");
        });

        addObserver((o, a) -> {
            System.out.println(a);
            System.out.println("2");
        });

    }


    public static void main(String[] args) {

        ObserverTest ot = new ObserverTest();

        ot.setChanged();
        ot.notifyObservers("aa,kkk");
    }


}