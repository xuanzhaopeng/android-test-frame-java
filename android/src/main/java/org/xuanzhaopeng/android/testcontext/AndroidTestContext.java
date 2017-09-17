package org.xuanzhaopeng.android.testcontext;

import org.xuanzhaopeng.android.pages.AndroidPage;
import org.xuanzhaopeng.common.driver.android.CommonAndroidDriver;
import org.xuanzhaopeng.common.testcontext.TestContext;

public class AndroidTestContext extends TestContext<CommonAndroidDriver, AndroidPage> {
    private static AndroidTestContext testContext;

    public static AndroidTestContext getInstance() {
        if (testContext == null) {
            testContext = new AndroidTestContext();
        }
        return testContext;
    }

    private AndroidTestContext() {
        super();
    }
}
