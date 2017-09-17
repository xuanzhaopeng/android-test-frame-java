package org.xuanzhaopeng.android.pages;

import io.appium.java_client.android.AndroidElement;
import org.xuanzhaopeng.common.driver.android.CommonAndroidDriver;
import org.xuanzhaopeng.common.testcontext.pages.BasePage;


public abstract class AndroidPage extends BasePage<AndroidElement, CommonAndroidDriver> {
    public AndroidPage(CommonAndroidDriver driver) {
        super(driver, AndroidElement.class);
    }
}
