/*
 * Copyright 2017 nqcx.org All right reserved. This software is the confidential and proprietary information
 * of nqcx.org ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with nqcx.org.
 */

package nqcx.tools.jdk9;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author naqichuan 2017/11/13 16:46
 */
public class SupplierTest {

    public static void main(String[] args) {
        Supplier<String> supplier = String::new;
        supplier.get();

        List<String> values = new ArrayList<>(10);


        values.stream();
    }
}
