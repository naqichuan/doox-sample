///*
// * Copyright 2017 nqcx.org All right reserved. This software is the confidential and proprietary information
// * of nqcx.org ("Confidential Information"). You shall not disclose such Confidential Information and shall use
// * it only in accordance with the terms of the license agreement you entered into with nqcx.org.
// */
//
//package nqcx.tools.aspect;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.function.Predicate;
//
///**
// * @author naqichuan 17/11/13 13:59
// */
//public class Aspect {
//
//    public static void main(String[] args) {
////        Aspect a = new Aspect();
//
//
//
////        a.new ClassA().a();
////        a.new ClassB().b();
//
////        new Thread(() -> System.out.println("aa")).start();
//
//
//        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
//        filter(languages, (str) -> str.startsWith("J"));
//    }
//
//    public static void filter(List<String> names, Predicate<String> condition) {
//        for(String name: names)  {
//            if(condition.test(name)) {
//                System.out.println(name + " ");
//            }
//        }
//    }
//
//
//    class ClassA {
//        public void a(String ccc) {
//            System.out.println("is a");
//        }
//    }
//
//    class ClassB {
//
//        public void b() {
//            System.out.println("is b");
//        }
//
//    }
//
////
////    class ClassAA extends ClassA {
////
////        @Override
////        public void a() {
////            System.out.println("a");
////            super.a();
////        }
////    }
//
//}
