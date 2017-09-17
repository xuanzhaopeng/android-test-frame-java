package org.xuanzhaopeng.android.pages;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.xuanzhaopeng.common.driver.android.CommonAndroidDriver;

public class MainActivityPage extends AndroidPage {
    public MainActivityPage(CommonAndroidDriver driver) {
        super(driver);
    }

    @AndroidFindBy(xpath = "//*[@content-desc='arg1']")
    public AndroidElement FirstArgument;

    @AndroidFindBy(xpath = "//*[@content-desc='arg2']")
    public AndroidElement SecondArgument;

    @AndroidFindBy(xpath = "//*[@content-desc='result' and @text='Result']")
    public AndroidElement Result;

    @AndroidFindBy(xpath = "//*[@content-desc='addition']")
    public AndroidElement AddButton;

    @AndroidFindBy(xpath = "//*[@content-desc='subtraction']")
    public AndroidElement SubButton;

    @AndroidFindBy(xpath = "//*[@content-desc='division']")
    public AndroidElement DivButton;

    @AndroidFindBy(xpath = "//*[@content-desc='multiplication']")
    public AndroidElement MulButton;

    public String FirstArgumentWithText = "//*[@content-desc='arg1' and @text='%s']";

    public String SecondArgumentWithText = "//*[@content-desc='arg2' and @text='%s']";

    public String ResultWithText = "//*[@content-desc='result' and @text='%s']";

    public String convertOperator(String operator) {
        switch (operator) {
            case "+":
                return "AddButton";
            case "-":
                return "SubButton";
            case "/":
                return "DivButton";
            case "*":
                return "MulButton";
            default:
                throw new RuntimeException(String.format("Cannot identify operation flag '%s'", operator));
        }
    }
}
