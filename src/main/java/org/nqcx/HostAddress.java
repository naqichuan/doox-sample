/*
 * Copyright 2017 nqcx.org All right reserved. This software is the confidential and proprietary information
 * of nqcx.org ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with nqcx.org.
 */

package org.nqcx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by naqichuan 2017/12/15 23:49
 */
public class HostAddress {

    private final static Logger LOGGER = LoggerFactory.getLogger(HostAddress.class);


    /**
     * 取得全部IP地址
     *
     * @param ipv4 ipv4
     * @param ipv6 ipv6
     * @return set
     */
    private static Set<String> all(boolean ipv4, boolean ipv6) {
        Set<String> all = new HashSet<>();
        try {
            for (Enumeration<NetworkInterface> netWorks = NetworkInterface.getNetworkInterfaces(); netWorks.hasMoreElements(); ) {
                NetworkInterface network = netWorks.nextElement();

                for (Enumeration<InetAddress> addresses = network.getInetAddresses(); addresses.hasMoreElements(); ) {
                    InetAddress address = addresses.nextElement();

                    if (address == null)
                        continue;

                    if (ipv4 && address instanceof Inet4Address && !address.getHostAddress().equals("127.0.0.1"))
                        all.add(address.getHostAddress());
                    else if (ipv6 && address instanceof Inet6Address && !address.getHostAddress().startsWith("0:0:0:0:0:0:0:1"))
                        all.add(address.getHostAddress());
                }

            }
        } catch (SocketException e) {
            LOGGER.error("", e);
        }

        return all;
    }

    /**
     * all
     *
     * @return set
     */
    public static Set<String> all() {
        return all(true, true);
    }

    /**
     * ipv4
     *
     * @return set
     */
    public static Set<String> ipv4() {
        return all(true, false);
    }

    /**
     * ipv6
     *
     * @return set
     */
    public static Set<String> ipv6() {
        return all(false, true);
    }


    public static void main(String[] args) {
        System.out.println("all: " + all());
        System.out.println("IPv4: " + ipv4());
        System.out.println("IPv6: " + ipv6());
    }

}
