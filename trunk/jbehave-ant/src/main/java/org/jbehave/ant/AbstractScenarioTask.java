package org.jbehave.ant;

import static java.util.Arrays.asList;
import static org.apache.tools.ant.Project.*;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.CommandlineJava;
import org.apache.tools.ant.types.Path;
import org.jbehave.scenario.RunnableScenario;
import org.jbehave.scenario.ScenarioClassLoader;
import org.jbehave.scenario.parser.ScenarioClassNameFinder;

/**
 * Abstract task that holds all the configuration parameters to specify and load
 * scenarios.
 * 
 * @author Mauro Talevi
 */
public abstract class AbstractScenarioTask extends Task {

    private static final String TEST_SCOPE = "test";

    private String sourceDirectory = "src/main/java";

    private String testSourceDirectory = "src/test/java";

    /**
     * The scope of the source, either "compile" or "test"
     */
    private String scope = "compile";

    /**
     * Scenario class names, if specified take precedence over the names
     * specificed via the "scenarioIncludes" and "scenarioExcludes" parameters
     */
    private List<String> scenarioClassNames = new ArrayList<String>();

    /**
     * Scenario include filters, relative to the root source directory
     * determined by the scope
     */
    private List<String> scenarioIncludes = new ArrayList<String>();

    /**
     * Scenario exclude filters, relative to the root source directory
     * determined by the scope
     */
    private List<String> scenarioExcludes = new ArrayList<String>();

    /**
     * Used to find scenario class names
     */
    private ScenarioClassNameFinder finder = new ScenarioClassNameFinder();

    /**
     * Determines if the scope of the source is "test"
     * 
     * @return A boolean <code>true</code> if test scoped
     */
    private boolean isTestScope() {
        return TEST_SCOPE.equals(scope);
    }

    private String rootSourceDirectory() {
        if (isTestScope()) {
            return testSourceDirectory;
        }
        return sourceDirectory;
    }

    private List<String> findScenarioClassNames() {
        List<String> scenarioClassNames = finder.listScenarioClassNames(rootSourceDirectory(), null, scenarioIncludes,
                scenarioExcludes);
        log("Found scenario class names: " + scenarioClassNames);
        return scenarioClassNames;
    }

    /**
     * Creates the Scenario ClassLoader with the classpath element of the
     * selected scope
     * 
     * @return A ScenarioClassLoader
     * @throws MalformedURLException
     */
    private ScenarioClassLoader createScenarioClassLoader() throws MalformedURLException {
        return new ScenarioClassLoader(classpathElements());
    }

    private List<String> classpathElements() {
        CommandlineJava commandLine = new CommandlineJava();
        Path path = commandLine.createClasspath(getProject());
        List<String> classpathElements = asList(path.list());
        log("Created classpath with elements " + classpathElements, MSG_DEBUG);
        return classpathElements;
    }

    /**
     * Returns the list of scenario instances, whose class names are either
     * specified via the parameter "scenarioClassNames" (which takes precedence)
     * or found using the parameters "scenarioIncludes" and "scenarioExcludes".
     * 
     * @return A List of Scenarios
     * @throws BuildException
     */
    protected List<RunnableScenario> scenarios() throws BuildException {
        List<String> names = scenarioClassNames;
        if (names == null || names.isEmpty()) {
            names = findScenarioClassNames();
        }
        if (names.isEmpty()) {
            log("No scenarios to run.", MSG_INFO);
        }
        ScenarioClassLoader classLoader = null;
        try {
            classLoader = createScenarioClassLoader();
        } catch (Exception e) {
            throw new BuildException("Failed to create scenario class loader", e);
        }
        List<RunnableScenario> scenarios = new ArrayList<RunnableScenario>();
        for (String name : names) {
            try {
                scenarios.add(classLoader.newScenario(name));
            } catch (Exception e) {
                throw new BuildException("Failed to instantiate scenario '" + name + "'", e);
            }
        }
        return scenarios;
    }

    // Setters used by Task to inject dependencies
    public void setSourceDirectory(String sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    public void setTestSourceDirectory(String testSourceDirectory) {
        this.testSourceDirectory = testSourceDirectory;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setScenarioClassNames(String scenarioClassNamesCSV) {
        this.scenarioClassNames = asList(scenarioClassNamesCSV.split(","));
    }

    public void setScenarioIncludes(String scenarioIncludesCSV) {
        this.scenarioIncludes = asList(scenarioIncludesCSV.split(","));
    }

    public void setScenarioExcludes(String scenarioExcludesCSV) {
        this.scenarioExcludes = asList(scenarioExcludesCSV.split(","));
    }

}
