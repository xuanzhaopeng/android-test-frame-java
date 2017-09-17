package org.xuanzhaopeng.common.testcontext.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.TimeOutDuration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.xuanzhaopeng.common.common.CommonLogger;
import org.xuanzhaopeng.common.driver.CommonDriver;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

public abstract class BasePage<ELEMENT_TYPE extends WebElement, DRIVER extends AppiumDriver<ELEMENT_TYPE>> {
    private static final Logger log = CommonLogger.getLog(BasePage.class.getSimpleName());
    private static final long DEFAULT_ELEMENT_WAIT_TIMEOUT_SECONDS = 5;

    private DRIVER driver;
    private Class<ELEMENT_TYPE> elementTypeClazz;

    @SuppressWarnings("unchecked")
    public BasePage(DRIVER driver, Class<ELEMENT_TYPE> elementTypeClazz) {
        this.driver = driver;
        this.elementTypeClazz = elementTypeClazz;
        PageFactory.initElements(new AppiumFieldDecorator(driver,
                        new TimeOutDuration(DEFAULT_ELEMENT_WAIT_TIMEOUT_SECONDS, TimeUnit.SECONDS)),
                this);
    }

    public boolean waitUntilElementVisible(String elementName, String... args) throws NoSuchFieldException, IllegalAccessException {
        ELEMENT_TYPE element = getElement(elementName, args);
        return waitUntilElementVisible(element);
    }

    public boolean waitUntilElementVisible(ELEMENT_TYPE element) {
        try {
            getWaiter(DEFAULT_ELEMENT_WAIT_TIMEOUT_SECONDS).until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public void typeText(TypeText typeText, String text, String elementName, String... args) throws Exception {
        ELEMENT_TYPE element = getElement(elementName, args);
        if (waitUntilElementVisible(element)) {
            element.click();
            if (typeText.equals(TypeText.CLEAN_TEXT_THEN_TYPE)) {
                element.clear();
            }
            ((CommonDriver) driver).typeText(text);
        }
    }

    public void tapOn(String elementName, String... args) throws Exception {
        ELEMENT_TYPE element = getElement(elementName, args);
        if (waitUntilElementVisible(elementName)) {
            element.click();;
        }
    }

    protected Wait<DRIVER> getWaiter(long timeoutSeconds) {
        return new FluentWait<>(driver)
                .withTimeout(timeoutSeconds, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(InvalidElementStateException.class);
    }

    protected ELEMENT_TYPE getElement(String elementName, String... args) throws NoSuchFieldException, IllegalAccessException {
        elementName = formatElementName(elementName);

        Field field = getClass().getDeclaredField(elementName);
        Object filedObject = field.get(this);
        if (filedObject instanceof String) {
            String xpath = (String) filedObject;
            return getDriver().findElement(By.xpath(String.format(xpath, args)));
        } else if (elementTypeClazz.isInstance(filedObject)) {
            return (ELEMENT_TYPE) filedObject;
        } else {
            throw new RuntimeException(String.format("Cannot identify the element type by name '%s'", elementName));
        }
    }

    protected DRIVER getDriver() {
        return this.driver;
    }

    private String formatElementName(String elementName) {
        String[] words = elementName.split("\\s+");
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            ret.append(Character.toUpperCase(words[i].charAt(0)));
            ret.append(words[i].substring(1));
        }
        return ret.toString();
    }
}
