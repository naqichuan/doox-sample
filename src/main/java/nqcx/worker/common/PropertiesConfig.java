/* 
 * Copyright 2012-2013 nqcx.org All right reserved. This software is the 
 * confidential and proprietary information of nqcx.org ("Confidential 
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with nqcx.org.
 */

package nqcx.worker.common;

import org.apache.commons.lang.StringUtils;

/**
 * 类Constants.java的实现描述：设置路径常量
 *
 * @author huangbaoguang 2012-10-10 下午8:53:48
 */
public class PropertiesConfig {

    public static String[] CONFIG_FILE_ARRAY = null;

    /**
     * 设置 configs
     *
     * @param configFileNames
     */
    public void setConfigFileNames(String configFileNames) {
        if (configFileNames == null)
            return;

        CONFIG_FILE_ARRAY = configFileNames.split(",");
        for (int i = 0; i < CONFIG_FILE_ARRAY.length; i++) {
            if (StringUtils.isEmpty(CONFIG_FILE_ARRAY[i])) {
                CONFIG_FILE_ARRAY[i] = null;
                continue;
            }
            CONFIG_FILE_ARRAY[i] = WorkerHome.HOME_PATH + "/config/" + CONFIG_FILE_ARRAY[i];
        }
    }

    public String[] getConfigFileArray() {
        return CONFIG_FILE_ARRAY;
    }
}
