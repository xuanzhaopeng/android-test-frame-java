package org.xuanzhaopeng.android.steps;

import cucumber.api.java.en.Given;

public class CommonSteps {

    @Given("^I wait for\\s*(\\d+) seconds?$")
    public void IWaitForXSeconds(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
    }
}
