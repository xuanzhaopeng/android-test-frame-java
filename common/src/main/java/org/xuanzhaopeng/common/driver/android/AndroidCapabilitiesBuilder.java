package org.xuanzhaopeng.common.driver.android;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.xuanzhaopeng.common.driver.CapabilitiesBuilder;

import java.net.MalformedURLException;

public class AndroidCapabilitiesBuilder extends CapabilitiesBuilder<AndroidCapabilitiesBuilder> {

    @Override
    public DesiredCapabilities build() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = DesiredCapabilities.android();
        desiredCapabilities.setCapability("deviceName", "null");
        desiredCapabilities.setCapability("newCommandTimeout", "1200");
        desiredCapabilities.setCapability("automationName", automationName);
        desiredCapabilities.setCapability("appPackage", appPackage);
        desiredCapabilities.setCapability("appActivity", appActivity);
        desiredCapabilities.setCapability("app", appPath);
        return desiredCapabilities;
    }
}
