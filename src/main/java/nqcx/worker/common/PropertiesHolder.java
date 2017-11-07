/* 
 * Copyright 2012-2013 nqcx.org All right reserved. This software is the 
 * confidential and proprietary information of nqcx.org ("Confidential 
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with nqcx.org.
 */

package nqcx.worker.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 类PropertiesHolder.java的实现描述：读取properties
 *
 * @author huangbaoguang 2012-10-10 下午8:26:02
 */
public class PropertiesHolder {

    private final static Logger logger = LoggerFactory.getLogger(PropertiesHolder.class);

    private PropertiesConfig propertiesConfig;

    /**
     * @return
     */
    public Properties asProperties() {
        Properties props = new Properties();
        InputStream in = null;
        try {
            if (propertiesConfig.getConfigFileArray() != null) {
                for (String config : propertiesConfig.getConfigFileArray()) {
                    in = new BufferedInputStream(new FileInputStream(config));
                    props.load(in);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
        }
        return props;
    }

    public void setPropertiesConfig(PropertiesConfig propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
    }
}
