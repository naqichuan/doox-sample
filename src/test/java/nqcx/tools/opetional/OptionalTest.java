/*
 * Copyright 2018 nqcx.org All right reserved. This software is the confidential and proprietary information
 * of nqcx.org ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with nqcx.org.
 */

package nqcx.tools.opetional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by naqichuan 2018/1/22 10:29
 */
public class OptionalTest {

//    http://www.importnew.com/22060.html
//    所以 Optional 中我们真正可依赖的应该是除了 isPresent() 和 get() 的其他方法:
//
//    public<U> Optional<U> map(Function<? super T, ? extends U> mapper)
//    public T orElse(T other)
//    public T orElseGet(Supplier<? extends T> other)
//    public void ifPresent(Consumer<? super T> consumer)
//    public Optional<T> filter(Predicate<? super T> predicate)
//    public<U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper)
//    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X

    public static void main(String[] args) {
        System.out.println(getName());
    }

    public static User getUser() {
        Optional<User> user = Optional.of(new User("naqichuan", 18));
//        return user.orElse(null); ////而不是 return user.isPresent() ? user.get() : null;
        return user.orElseGet(() -> getOtherUser());
    }

    public static List<String> getOrders() {
        Optional<User> user = Optional.of(new User("naqichua", 18));
        return user.map(u -> u.getOrders()).orElse(Collections.emptyList());
    }

    public static String getName() {
//        Optional<User> user = Optional.of(new User("naqichuan", 18));
        Optional<User> user = Optional.ofNullable(null);

        return user.map(u -> u.getName()).map(name -> name.toUpperCase()).orElse(null);
    }

    private static User getOtherUser() {
        return null;
    }

    static class User {
        private String name;
        private int age;

        private List<String> orders;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public List<String> getOrders() {
            return orders;
        }

        public void setOrders(List<String> orders) {
            this.orders = orders;
        }
    }
}
