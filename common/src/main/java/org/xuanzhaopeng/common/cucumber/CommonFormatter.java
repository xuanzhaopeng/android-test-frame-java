package org.xuanzhaopeng.common.cucumber;

import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;
import org.apache.log4j.Logger;
import org.xuanzhaopeng.common.common.CommonLogger;

import java.util.*;

public class CommonFormatter implements Formatter, Reporter {
    private static Map<Step, String> steps = new LinkedHashMap<>();
    private static Optional<Iterator<Step>> stepsIterator = Optional.empty();
    private static final Logger log = CommonLogger.getLog(CommonFormatter.class.getSimpleName());

    @Override
    public void background(Background arg0) {
    }

    @Override
    public void close() {
    }

    @Override
    public void done() {
    }

    @Override
    public void eof() {
    }

    @Override
    public void examples(Examples arg0) {
    }

    @Override
    public void feature(Feature arg0) {
    }

    @Override
    public void scenario(Scenario scenario) {
        if (steps.size() > 0) {
            steps.clear();
        }
        log.debug(String.format("\n\n----------------------------------------------\n" +
                "Scenario: %s \n" +
                "----------------------------------------------", scenario.getName()));
    }

    @Override
    public void scenarioOutline(ScenarioOutline scenarioOutline) {
    }

    @Override
    public void step(Step step) {
        steps.put(step, null);
    }

    @Override
    public void syntaxError(String arg0, String arg1, List<String> arg2, String arg3, Integer arg4) {

    }

    @Override
    public void uri(String arg0) {
    }

    @Override
    public void after(Match match, Result result) {
    }

    @Override
    public void before(Match match, Result result) {
    }

    @Override
    public void embedding(String match, byte[] embeddingBytes) {
    }

    @Override
    public void match(Match match) {
    }


    private void takeStepScreenshot(final Result stepResult, final String stepName) {
        //TODO: Take screenshot and save for each step
    }


    @Override
    public void result(Result result) {
        final Step currentStep = getCurrentStep();
        takeStepScreenshot(result, currentStep.getName());
        log.debug(String.format("\n----------------------------------------------\n" +
                        "STEP: %s (status: %s)\n" +
                        "----------------------------------------------",
                currentStep.getName(), result.getStatus()));
    }

    @Override
    public void write(String arg0) {
    }


    @Override
    public void startOfScenarioLifeCycle(Scenario scenario) {
    }


    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {
        steps.clear();
        stepsIterator = Optional.empty();
        //TODO: Send result to TestRail
    }

    private Step getCurrentStep() {
        if (!stepsIterator.isPresent()) {
            stepsIterator = Optional.of(steps.keySet().iterator());
        }
        return stepsIterator.get().next();
    }
}
