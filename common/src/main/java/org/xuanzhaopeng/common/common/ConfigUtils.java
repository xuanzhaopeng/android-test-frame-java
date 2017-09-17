package org.xuanzhaopeng.common.common;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.Semaphore;

public class ConfigUtils {
    private final static Map<String, Optional<String>> cachedConfig = new HashMap<>();
    private final static Semaphore sem = new Semaphore(1);
    private static final Random rand = new Random();
    private static final String PROJECT_CONFIG = "Configuration.properties";

    public static String getValueFromConfig(Class<?> c, ConfigVariableType variableType) throws Exception {
        final Optional<String> value = getValueFromConfigFile(c, variableType.toString(), PROJECT_CONFIG);
        if (value.isPresent()) {
            return value.get();
        } else {
            throw new RuntimeException(String.format(
                    "There is no '%s' property in the '%s' configuration file", variableType.toString(),
                    PROJECT_CONFIG));
        }
    }

    private static Optional<String> getValueFromConfigFile(Class<?> c, String key, String resourcePath)
            throws Exception {
        final String configKey = String.format("%s:%s", resourcePath, key);
        if (cachedConfig.containsKey(configKey)) {
            return cachedConfig.get(configKey);
        }

        final int maxTry = 3;
        int tryNum = 0;
        Exception savedException = null;
        do {
            InputStream configFileStream = null;
            sem.acquire();
            try {
                final ClassLoader classLoader = c.getClassLoader();
                configFileStream = classLoader.getResourceAsStream(resourcePath);
                Properties p = new Properties();
                p.load(configFileStream);
                if (p.containsKey(key)) {
                    cachedConfig.put(configKey, Optional.of((String) p.get(key)));
                } else {
                    cachedConfig.put(configKey, Optional.empty());
                }
                return cachedConfig.get(configKey);
            } catch (Exception e) {
                savedException = e;
            } finally {
                if (configFileStream != null) {
                    configFileStream.close();
                }
                sem.release();
            }
            Thread.sleep(100 + rand.nextInt(2000));
            tryNum++;
        } while (tryNum < maxTry);
        throw savedException;
    }
}
