package org.xuanzhaopeng.android;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(format = {"html:target/report", "json:target/report.json", "org.xuanzhaopeng.common.cucumber.CommonFormatter"}, tags = {"@torun"})
public class DevRun extends RunBase {
}
