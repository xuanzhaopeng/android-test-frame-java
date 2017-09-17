package org.xuanzhaopeng.common.driver;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.xuanzhaopeng.common.driver.android.AndroidCapabilitiesBuilder;

import java.net.MalformedURLException;

public abstract class CapabilitiesBuilder<SELF> {
    protected String automationName;
    protected String appPath;
    protected String appPackage;
    protected String appActivity;

    public abstract DesiredCapabilities build() throws MalformedURLException;

    public static AndroidCapabilitiesBuilder forAndroid() {
        return new AndroidCapabilitiesBuilder();
    };

    public SELF withAutomationName(String automationName) {
        this.automationName = automationName;
        return (SELF) this;
    }

    public SELF withAppPath(String appPath) {
        this.appPath = appPath;
        return (SELF) this;
    }

    public SELF withAppPackage(String appPackage) {
        this.appPackage = appPackage;
        return (SELF) this;
    }

    public SELF withAppActivity(String appActivity) {
        this.appActivity = appActivity;
        return (SELF) this;
    }
}
