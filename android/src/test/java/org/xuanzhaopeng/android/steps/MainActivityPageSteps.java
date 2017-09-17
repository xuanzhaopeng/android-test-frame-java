package org.xuanzhaopeng.android.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.xuanzhaopeng.android.pages.MainActivityPage;
import org.xuanzhaopeng.android.testcontext.AndroidTestContext;
import org.junit.Assert;
import org.xuanzhaopeng.common.testcontext.pages.TypeText;

public class MainActivityPageSteps {
    private MainActivityPage getPage() throws Exception {
        return AndroidTestContext.getInstance().getPageCollection().getPage(MainActivityPage.class);
    }

    @Given("^I see main activity page$")
    public void ISeeMainActivityPage() throws Exception {
        Assert.assertTrue(getPage().waitUntilElementVisible("first argument"));
        Assert.assertTrue(getPage().waitUntilElementVisible("second argument"));
        Assert.assertTrue(getPage().waitUntilElementVisible("result"));
        Assert.assertTrue(getPage().waitUntilElementVisible("add button"));
        Assert.assertTrue(getPage().waitUntilElementVisible("sub button"));
        Assert.assertTrue(getPage().waitUntilElementVisible("mul button"));
        Assert.assertTrue(getPage().waitUntilElementVisible("div button"));
    }

    @When("^I calculate ([^(+|*|/)]+)\\s*(\\+|-|\\*|\\/)\\s*([^(+|*|/)]+)$")
    public void ICalculate(String arg1, String op, String arg2) throws Exception {
        getPage().typeText(TypeText.TYPE_TEXT, arg1, "first argument");
        getPage().typeText(TypeText.TYPE_TEXT, arg2, "second argument");
        String buttonName = getPage().convertOperator(op);
        getPage().tapOn(buttonName);
    }

    @When("^I( clear text and)? type (.*) to (first|second) argument$")
    public void ITypeText(String shouldCleanText, String text, String elementName) throws Exception {
        elementName = String.format("%s argument", elementName);
        if (shouldCleanText == null) {
            getPage().typeText(TypeText.TYPE_TEXT, text, elementName);
        } else {
            getPage().typeText(TypeText.CLEAN_TEXT_THEN_TYPE, text, elementName);
        }
    }

    @When("^I tap on (add|sub|div|mul) button$")
    public void ITypeButton(String elementName) throws Exception {
        elementName = String.format("%s button", elementName);
        getPage().tapOn(elementName);
    }

    @Then("^I see the text of (first argument|second argument|result) is (.*)$")
    public void ISeeTextXIsY(String elementName, String text) throws Exception {
        elementName = String.format("%s with text", elementName);
        Assert.assertTrue(String.format("expect %s equals to %s", elementName, text),
                getPage().waitUntilElementVisible(elementName, text));
    }

}
