package org.xuanzhaopeng.common.driver.android;

import java.io.IOException;
import java.net.URL;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.Capabilities;
import org.xuanzhaopeng.common.driver.CommonDriver;

public class CommonAndroidDriver extends AndroidDriver<AndroidElement> implements CommonDriver {
    public CommonAndroidDriver(URL remoteAddress, Capabilities capabilities) {
        super(remoteAddress, capabilities);
    }

    public void typeText(String text) throws IOException, InterruptedException {
        Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", String.format("adb shell input text %s", text)}).waitFor();
    }
}