/*
 * Copyright 2017 nqcx.org All right reserved. This software is the confidential and proprietary information
 * of nqcx.org ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with nqcx.org.
 */

package nqcx.tools.download;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

/**
 * @author naqichuan 2017/11/15 17:12
 */
public class DownloadTest {

    public static void main(String[] args) throws IOException, InterruptedException {

        String bbb = "/Users/nqcx/Downloads/bbb";
        File bbbDir = new File(bbb);
        if (!bbbDir.exists())
            return;
        File[] files = bbbDir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f != null && f.exists() && f.isFile())
                    f.delete();
            }
        }


//        HttpGet httpGet = new HttpGet("https://resources.chineseall.cn/v3/book/download/zceyg/txt?token=N4M7N0LEp4qzj842Ki0x72sU89776088");
//        HttpGet httpGet = new HttpGet("https://resources.chineseall.cn/v3/book/download/VjXOg/txt?token=N4M7N0LEp4qzj842Ki0x72sU89776088");
        HttpGet httpGet = new HttpGet("https://resources.chineseall.cn/v3/book/download/dj0dg/txt?token=N4M7N0LEp4qzj842Ki0x72sU89776088");
//        HttpGet httpGet = new HttpGet("http://sxqh.chineseall.cn/interface/abookshelf/book/10060551977/download");
//        httpGet.addHeader("security", "ChineseAll&*(");

        for (int i = 0; i < 100; i++) {
            System.out.println("==============================");

            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(httpGet);

            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                System.out.println("连接成功！");
                Header[] headers = response.getAllHeaders();
                if (headers != null) {
                    for (Header header : headers) {
                        if (header == null)
                            continue;
                        System.out.println(header.toString());
                    }
                }
            }

            InputStream in = response.getEntity().getContent();
            File downFile = new File(bbb + "/" + i + ".txt");
            OutputStream out = new FileOutputStream(downFile);
            byte[] buffer = new byte[4096];
            int readLength = 0;
            while ((readLength = in.read(buffer)) > 0) {
                byte[] bytes = new byte[readLength];
                System.arraycopy(buffer, 0, bytes, 0, readLength);
                out.write(bytes);
            }

            out.flush();
            in.close();

            httpGet.releaseConnection();
            httpClient.close();

            System.out.println("下载完成！");

            Thread.sleep(1000);
        }
    }
}
