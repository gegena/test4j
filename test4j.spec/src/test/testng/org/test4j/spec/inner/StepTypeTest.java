package org.test4j.spec.inner;

import java.util.Iterator;

import org.test4j.spec.scenario.step.txt.LineType;
import org.test4j.testng.Test4J;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("rawtypes")
public class StepTypeTest extends Test4J {

    @Test(dataProvider = "dataForIsStartWithScenario", groups = "jspec")
    public void testIsStartWithScenario(String line, boolean is) {
        boolean isScenario = LineType.isScenarioLine(line);
        want.bool(isScenario).is(is);
    }

    @DataProvider
    Iterator dataForIsStartWithScenario() {
        return new DataIterator() {
            {
                data(null, false);
                data("Scenario", false);
                data("Scenario adf", true);
                data("Scenario\t adfa", true);
                data("SkipScenario\t adfa", true);
                data("Scenarioxxx", false);

                data("SkipScenarioxxx", false);
                data("Scenerioxxx", false);
                data("SkipScenerioxxx", false);
            }
        };
    }
}
