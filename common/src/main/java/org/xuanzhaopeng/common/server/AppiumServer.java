package org.xuanzhaopeng.common.server;

import org.openqa.selenium.WebDriverException;
import org.xuanzhaopeng.common.common.CommonLogger;
import org.xuanzhaopeng.common.common.TimeDelta;
import org.apache.log4j.Logger;
import org.openqa.selenium.net.UrlChecker;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AppiumServer {
    private static final Logger log = CommonLogger.getLog(AppiumServer.class.getSimpleName());
    private static AppiumServer instance = null;
    private static final int PORT = 4723;
    private static final int SELENDROID_PORT = 4444;
    private static final String APPIUM_SCRIPT_PATH = "/usr/local/bin/appium";
    private static final String NODE_EXECUTABLE_PATH = "/usr/local/bin/node";
    private static final String LOG_PATH = "/usr/local/var/log/appium/appium.log";
    private static final TimeDelta RESTART_TIMEOUT = TimeDelta.fromSeconds(90);
    private static final String SERVER_URL = String.format("http://127.0.0.1:%d/wd/hub", PORT);

    public synchronized static AppiumServer getInstance() {
        if (instance == null) {
            instance = new AppiumServer();
        }
        return instance;
    }

    public synchronized void restart() throws Exception {
        log.info(String.format("Trying to (re)start Appium server %s", SERVER_URL));
        Runtime.getRuntime().exec("killall node");
        final File scriptFile = File.createTempFile("script", ".sh");
        try {
            final List<String> scriptContent = new ArrayList<>();
            scriptContent.add("#!/bin/bash");
            Collections.addAll(scriptContent, String.join(" ", DEFAULT_CMDLINE));
            try (Writer output = new BufferedWriter(new FileWriter(scriptFile))) {
                output.write(String.join("\n", scriptContent));
            }
            log.info(String.format("Waiting for Appium to be (re)started on %s", SERVER_URL));
            new ProcessBuilder("/bin/bash", scriptFile.getCanonicalPath()).
                    redirectErrorStream(true).start().waitFor(RESTART_TIMEOUT.asMilliSeconds(), TimeUnit.MILLISECONDS);
            if (!waitUntilIsRunning(RESTART_TIMEOUT)) {
                throw new WebDriverException("Appium server has failed to start");
            }

            log.info(String.format("Appium server has been successfully (re)started on %s", SERVER_URL));
        } finally {
            scriptFile.delete();
        }
    }

    public boolean isRunning() throws Exception {
        return waitUntilIsRunning(TimeDelta.fromSeconds(1.5));
    }

    private AppiumServer() {
        ensureAppiumExecutableExistence();
        ensureParentDirExistence(LOG_PATH);
    }

    private boolean waitUntilIsRunning(TimeDelta timeout) throws Exception {
        final URL status = new URL(SERVER_URL + "/sessions");
        try {
            new UrlChecker().waitUntilAvailable(timeout.asMilliSeconds(), TimeUnit.MILLISECONDS, status);
            return true;
        } catch (UrlChecker.TimeoutException e) {
            return false;
        }
    }

    private static final String[] DEFAULT_CMDLINE = new String[]{
            NODE_EXECUTABLE_PATH,
            APPIUM_SCRIPT_PATH,
            "--port", Integer.toString(PORT),
            "--session-override",
            "--selendroid-port", Integer.toString(SELENDROID_PORT),
            "--log", LOG_PATH,
            "--log-timestamp",
            "> /dev/null 2>&1",
            "&"
    };

    private static void ensureParentDirExistence(String filePath) {
        final File log = new File(filePath);
        if (!log.getParentFile().exists()) {
            if (!log.getParentFile().mkdirs()) {
                throw new IllegalStateException(String.format(
                        "The script has failed to create '%s' folder for Appium logs. " +
                                "Please make sure your account has correct access permissions on the parent folder(s)",
                        log.getParentFile().getAbsolutePath()));
            }
        }
    }

    private static void ensureAppiumExecutableExistence() {
        if (!new File(APPIUM_SCRIPT_PATH).exists()) {
            throw new IllegalStateException(
                    String.format("The script is unable to find main Appium executable at the path '%s'. " +
                                    "Please make sure it is properly installed (`npm install -g appium`)",
                            APPIUM_SCRIPT_PATH));
        }
    }
}
