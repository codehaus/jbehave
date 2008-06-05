package org.jbehave.mojo;

import static java.util.Arrays.asList;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.jbehave.scenario.Scenario;

/**
 * Extends URLClassLoader to instantiate Scenarios.
 * 
 * @author Mauro Talevi
 */
public class ScenarioClassLoader extends URLClassLoader {

    public ScenarioClassLoader(List<String> classpathElements) throws MalformedURLException {
        super(toClasspathURLs(classpathElements), Scenario.class.getClassLoader());
    }

    public ScenarioClassLoader(List<String> classpathElements, ClassLoader parent) throws MalformedURLException {
        super(toClasspathURLs(classpathElements), parent);
    }

    /**
     * Loads and instantiates a Scenario class
     * 
     * @param scenarioClassName the name of the Scenario class
     * @return A Scenario instance
     */
    public Scenario newScenario(String scenarioClassName) {
        String scenarioInstantiationFailed = "The Scenario " + scenarioClassName
                + " could not be instantiated with classpath elements " + asList(getURLs());
        try {
            Scenario scenario = (Scenario) loadClass(scenarioClassName).getConstructor(ClassLoader.class).newInstance(
                    this);
            Thread.currentThread().setContextClassLoader(this);
            return scenario;
        } catch (ClassCastException e) {
            throw new RuntimeException(scenarioClassName + " is not a " + Scenario.class.getName(), e);
        } catch (Exception e) {
            throw new RuntimeException(scenarioInstantiationFailed, e);
        } catch (NoClassDefFoundError e) {
            throw new RuntimeException(scenarioInstantiationFailed, e);
        }
    }

    protected static URL[] toClasspathURLs(List<String> classpathElements) throws MalformedURLException {
        List<URL> urls = new ArrayList<URL>();
        if (classpathElements != null) {
            for (String classpathElement : classpathElements) {
                urls.add(new File(classpathElement).toURL());
            }
        }
        return urls.toArray(new URL[urls.size()]);
    }

}
