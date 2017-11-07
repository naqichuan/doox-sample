/*
 * Copyright 2016 nqcx.org All right reserved. This software is the confidential and proprietary information
 * of nqcx.org ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with nqcx.org.
 */

package nqcx.worker.common;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author naqichuan 16/9/27 17:29
 */
public class WorkerHome {

    private final static Logger logger = LoggerFactory.getLogger(WorkerHome.class);

    // 用于正式执行程序时定位程序根目录
    public static String HOME_PATH = System.getenv("worker_home");

    // 设置一个程序运行的根目录
    static {
        logger.info("Worker's worker.home is " + HOME_PATH);

        if (StringUtils.isBlank(HOME_PATH)) {
            // 主要用于开发测试时定位程序根目录
            HOME_PATH = System.getProperty("user.dir");
            logger.info("Worker's user.dir is " + HOME_PATH);

            File file = new File(HOME_PATH + "/target");
            if (file.exists())
                HOME_PATH = file.getPath();
            else
                HOME_PATH = WorkerHome.class.getResource("/").getPath() + "../../";
        }

        logger.info("Worker's home.path is " + HOME_PATH);
    }
}
