/*
 * Copyright 2018 nqcx.org All right reserved. This software is the confidential and proprietary information
 * of nqcx.org ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with nqcx.org.
 */

package nqcx.tools.jdk8;

import java.lang.reflect.Array;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static sun.misc.Version.println;

/**
 * Created by naqichuan 2018/1/24 14:30
 */
public class Reduce {

    public static void main(String[] args) {
        String[] lines = {"wqbcd ssq aacc Created by naqichuan 2018/1/24 14:30 ssq jsq", "amw ssq nqcx", "dfalkfdl jsq"};

        Stream<String> stream = Arrays.stream(lines);
        Stream<String[]> stream1 = Arrays.stream(lines).map(line -> line.trim().split(" "));
        Stream<String> stream2 = Arrays.stream(lines).flatMap(line -> Stream.of(line.trim().split(" ")));
        Map<String, Long> wordcount = Arrays.stream(lines).flatMap(line -> Stream.of(line.trim().split(" "))).map(word -> new AbstractMap.SimpleEntry<>(word, 1))
                .collect(groupingBy(AbstractMap.SimpleEntry::getKey, counting()));

        stream.forEach(System.out::println);
        System.out.println("=============");
        stream1.forEach(line -> System.out.println(Arrays.toString(line)));
        System.out.println("=============");
        stream2.forEach(line -> System.out.println(line));
        System.out.println("=============");
        wordcount.forEach((k, v) -> System.out.println(String.format("%s=%s", k, v)));
    }

}
