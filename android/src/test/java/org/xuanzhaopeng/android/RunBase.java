package org.xuanzhaopeng.android;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.xuanzhaopeng.common.server.AppiumServer;

public class RunBase {
    @BeforeClass
    public static void beforeAll() throws Exception {
        if(!AppiumServer.getInstance().isRunning()) {
            AppiumServer.getInstance().restart();
        }
    }

    @AfterClass
    public static void afterAll() {
    }
}
