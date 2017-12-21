/*
 * Copyright 2017 nqcx.org All right reserved. This software is the confidential and proprietary information
 * of nqcx.org ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with nqcx.org.
 */

package org.nqcx.worker.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by naqichuan 2017/12/20 14:55
 */
public class OpenUrl {

    public static void main(String[] args) throws IOException {

        URL url = new URL("http://bksx.chineseall.cn/v3/book/content/5oKig/pdf/1?t=1513733215797");

        URLConnection connection = url.openConnection();
        InputStream in = connection.getInputStream();
        int len = connection.getContentLength();
        Map<String, List<String>> map = connection.getHeaderFields();
        System.out.println(map);

        byte[] bytes = new byte[len];
        in.read(bytes);
        System.out.println(bytes);
        in.close();
    }
}
