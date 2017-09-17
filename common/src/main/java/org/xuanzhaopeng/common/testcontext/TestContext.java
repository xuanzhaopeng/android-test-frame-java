package org.xuanzhaopeng.common.testcontext;

import cucumber.api.Scenario;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.xuanzhaopeng.common.testcontext.pages.BasePage;
import org.xuanzhaopeng.common.testcontext.pages.PageCollection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class TestContext<DRIVER extends AppiumDriver, PAGE extends BasePage> {
    private final Class<DRIVER> driverClazz;
    private DRIVER driver;
    private PageCollection<DRIVER, PAGE> pageCollection;

    @SuppressWarnings("unchecked")
    public TestContext() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        driverClazz = (Class<DRIVER>) genericSuperclass.getActualTypeArguments()[0];
    }

    public void start(Scenario scenario, String remoteUrl, DesiredCapabilities desiredCapabilities) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, MalformedURLException {
        if (driver == null) {
            final Constructor<?> ctor = driverClazz.getConstructor(URL.class, Capabilities.class);
            driver = driverClazz.cast(ctor.newInstance(new URL(remoteUrl), desiredCapabilities));
            pageCollection = new PageCollection<>(driver, driverClazz);
        }
    }

    public void stop(Scenario scenario) {
        pageCollection.clearAllPages();
        driver.quit();
        driver = null;
    }

    public PageCollection<DRIVER, PAGE> getPageCollection() {
        return pageCollection;
    }

    public DRIVER getDriver() {
        return driver;
    }
}
