package org.jbehave.scenario;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ScenarioClassLoaderBehaviour {

    @Test
    public void canInstantiateNewScenario() throws MalformedURLException {
        List<String> elements = Arrays.asList();
        ScenarioClassLoader classLoader = new ScenarioClassLoader(elements);
        String scenarioClassName = MyScenario.class.getName();
        assertScenarioIsInstantiated(classLoader, scenarioClassName);
    }
    
    private void assertScenarioIsInstantiated(ScenarioClassLoader classLoader, String scenarioClassName) {
        RunnableScenario scenario = classLoader.newScenario(scenarioClassName);
        assertNotNull(scenario);
        assertEquals(scenarioClassName, scenario.getClass().getName());
    }

    private static class MyScenario extends JUnitScenario {

        public MyScenario(ClassLoader classLoader){
            
        }
    }

}
