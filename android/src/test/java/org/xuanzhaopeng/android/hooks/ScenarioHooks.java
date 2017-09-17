package org.xuanzhaopeng.android.hooks;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.xuanzhaopeng.android.testcontext.AndroidTestContext;
import org.xuanzhaopeng.common.common.ConfigUtils;
import org.xuanzhaopeng.common.common.ConfigVariableType;
import org.xuanzhaopeng.common.driver.CapabilitiesBuilder;

public class ScenarioHooks {
    @Before
    public void setUp(Scenario scenario) throws Exception {
        String serverUrl = ConfigUtils.getValueFromConfig(ScenarioHooks.class, ConfigVariableType.SERVER_URL);
        DesiredCapabilities capabilities = CapabilitiesBuilder.forAndroid()
                .withAutomationName(ConfigUtils.getValueFromConfig(ScenarioHooks.class, ConfigVariableType.DRIVER_AUTOMATION_NAME))
                .withAppPackage(ConfigUtils.getValueFromConfig(ScenarioHooks.class, ConfigVariableType.DRIVER_APP_PACKAGE))
                .withAppActivity(ConfigUtils.getValueFromConfig(ScenarioHooks.class, ConfigVariableType.DRIVER_APP_ACTIVITY))
                .withAppPath(ConfigUtils.getValueFromConfig(ScenarioHooks.class, ConfigVariableType.DRIVER_APP_PATH))
                .build();

        AndroidTestContext.getInstance().start(scenario, serverUrl, capabilities);
    }

    @After
    public void tearDown(Scenario scenario) throws Exception {
        AndroidTestContext.getInstance().stop(scenario);
    }
}
