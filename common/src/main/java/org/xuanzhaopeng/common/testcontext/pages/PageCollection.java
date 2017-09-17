package org.xuanzhaopeng.common.testcontext.pages;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Map;

import io.appium.java_client.AppiumDriver;
import org.apache.log4j.Logger;
import org.xuanzhaopeng.common.common.CommonLogger;

public class PageCollection<DRIVER extends AppiumDriver, PAGE extends BasePage> {
    private final Logger log = CommonLogger.getLog(this.getClass().getSimpleName());
    private final Map<Class<? extends BasePage>, PAGE> pagesMapping = new LinkedHashMap<>();
    private DRIVER driver;
    private Class<DRIVER> driverClazz;

    public PageCollection(DRIVER driver, Class<DRIVER> driverClazz) {
        this.driver = driver;
        this.driverClazz = driverClazz;
    }

    public <T extends PAGE> T getPage(Class<T> pageClass) throws Exception {
        if (!pagesMapping.containsKey(pageClass)) {
            log.debug(String.format(" > +++ %s", pageClass.getSimpleName()));
            pagesMapping.put(pageClass, createPage(pageClass));
        }
        return pageClass.cast(pagesMapping.get(pageClass));
    }

    public void clearAllPages() {
        final int pagesCount = pagesMapping.size();
        if (pagesCount > 0) {
            pagesMapping.clear();
            log.debug(String.format("Cleaned %d existing page objects", pagesCount));
        }
    }

    private <T extends BasePage> T createPage(Class<T> newPageCls)
            throws Exception {
        final Constructor<?> ctor = newPageCls.getConstructor(driverClazz);
        return newPageCls.cast(ctor.newInstance(driver));
    }
}
