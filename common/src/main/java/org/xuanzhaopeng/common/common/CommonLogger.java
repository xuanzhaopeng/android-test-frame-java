package org.xuanzhaopeng.common.common;

import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class CommonLogger {
    public static synchronized Logger getLog(String className) {
        URL configFile = CommonLogger.class.getResource("/log4j.properties");
        PropertyConfigurator.configure(configFile);
        return Logger.getLogger(className);
    }
}
