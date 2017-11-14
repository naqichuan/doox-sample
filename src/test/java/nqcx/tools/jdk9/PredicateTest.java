/*
 * Copyright 2017 nqcx.org All right reserved. This software is the confidential and proprietary information
 * of nqcx.org ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with nqcx.org.
 */

package nqcx.tools.jdk9;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author naqichuan 17/11/13 15:29
 */
public class PredicateTest {

    public static void main(String[] args) {

        Predicate<String> predicate = (s) -> s.length() > 0;

        System.out.println(predicate.test(""));
        System.out.println(predicate.negate().test(""));


        Predicate<Boolean> nonNull = Objects::nonNull;
        System.out.println(nonNull);

    }
}
