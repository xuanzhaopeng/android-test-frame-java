package org.xuanzhaopeng.common.common;

public enum ConfigVariableType {
    SERVER_URL("serverUrl"),
    DRIVER_APP_PATH("driverAppPath"),
    DRIVER_APP_PACKAGE("driverAppPackage"),
    DRIVER_APP_ACTIVITY("driverAppActivity"),
    DRIVER_AUTOMATION_NAME("driverAutomationName");

    private final String variableType;

    ConfigVariableType(String variableType) {
        this.variableType = variableType;
    }

    @Override
    public String toString() {
        return variableType;
    }
}
