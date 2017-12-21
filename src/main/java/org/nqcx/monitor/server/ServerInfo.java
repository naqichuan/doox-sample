/*
 * Copyright 2017 nqcx.org All right reserved. This software is the confidential and proprietary information
 * of nqcx.org ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with nqcx.org.
 */

package org.nqcx.monitor.server;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.nqcx.HostAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.Set;

/**
 */
public class ServerInfo {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerInfo.class);


    private final static String SERVER_ROOT_PATH = "/nqcx/monitor/server";
    private static String SERVER_PATH = "1";

    private final static String ZK_SERVERS = "localhost:2181,localhost:2182,localhost:2183";
    private final static int SESSION_TIMEOUT = 10 * 1000;
    private final static int CONNECTION_TIMEOUT = Integer.MAX_VALUE;


    private static ZkClient zkClient;

    static {
        zkClient = new ZkClient(ZK_SERVERS, SESSION_TIMEOUT, CONNECTION_TIMEOUT, new ZkSerializer() {

            @Override
            public byte[] serialize(Object data) throws ZkMarshallingError {
                try {
                    return String.valueOf(data).getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("", e);
                }

                return null;
            }

            @Override
            public Object deserialize(byte[] bytes) throws ZkMarshallingError {
                try {
                    return new String(bytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("", e);
                }
                return null;
            }
        });

        registerServerRoot();
        registerServer();
    }

    /**
     *
     */
    private static void registerServerRoot() {
        if (!zkClient.exists(SERVER_ROOT_PATH)) {
            zkClient.createPersistent(SERVER_ROOT_PATH, true);
        }

        zkClient.subscribeChildChanges(SERVER_ROOT_PATH, (parentPath, childs) -> {
            System.out.println(childs.size());
        });
    }

    /**
     */
    private static void registerServer() {
//        List<String> childs = zkClient.getChildren(SERVER_ROOT_PATH);
//        int serverId = 1;
//        if (childs != null || childs.size() > 0)
//            serverId = childs.size() + 1;

//        while (zkClient.exists(SERVER_PATH = (SERVER_ROOT_PATH + "/" + String.valueOf(serverId)))) {
//            serverId++;
//        }
        final StringBuffer nodeName = new StringBuffer();
        Set<String> set = HostAddress.ipv4();
        set.forEach(v -> {
            if (nodeName.length() != 0)
                nodeName.append(",");
            nodeName.append(v);
        });

        SERVER_PATH = SERVER_ROOT_PATH + "/" + nodeName.toString();

        if (!zkClient.exists(SERVER_PATH))
            zkClient.createPersistent(SERVER_PATH);

        zkClient.subscribeDataChanges(SERVER_PATH, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) {
                System.out.println("dataPath handleDataChange" + dataPath + " | " + data);
            }

            @Override
            public void handleDataDeleted(String dataPath) {
                System.out.println("dataPath handleDataDeleted" + dataPath);
            }
        });
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        System.out.println("> ");
        while (true) {
            String line = s.nextLine();
            if (line.equals("exit"))
                break;
            else if ("get".equals(line))
                System.out.println((String) zkClient.readData(SERVER_PATH));
            else if (line.startsWith("set"))
                zkClient.writeData(SERVER_PATH, line);

            System.out.println("> " + line);
        }

        System.exit(0);
    }
}
