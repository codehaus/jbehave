package org.jbehave.mojo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import org.jbehave.container.pico.XMLPicoContainer;
import org.jbehave.scenario.JUnitScenario;
import org.jbehave.scenario.RunnableScenario;
import org.junit.Test;

public class ScenarioClassLoaderBehaviour {

    @Test
    public void canInstantiateNewScenario() throws MalformedURLException {
        List<String> elements = Arrays.asList();
        ScenarioClassLoader classLoader = new ScenarioClassLoader(elements);
        String scenarioClassName = MyScenario.class.getName();
        assertScenarioIsInstantiated(classLoader, scenarioClassName);
    }

    //FIXME@Test
    public void canInstantiateNewContainerScenario() throws MalformedURLException {
        List<String> elements = Arrays.asList();
        ClassLoader parent = Thread.currentThread().getContextClassLoader();
        ScenarioClassLoader classLoader = new ScenarioClassLoader(elements, parent);
        String scenarioClassName = MyContainerScenario.class.getName();
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

    private static class MyContainerScenario extends JUnitScenario {

        public MyContainerScenario(ClassLoader classLoader){
            new XMLPicoContainer("org/jbehave/mojo/container.xml");
        }
    }

}
