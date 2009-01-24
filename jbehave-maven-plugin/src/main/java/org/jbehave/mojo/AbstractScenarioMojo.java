package org.jbehave.mojo;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.jbehave.scenario.RunnableScenario;
import org.jbehave.scenario.ScenarioClassLoader;
import org.jbehave.scenario.parser.ScenarioClassNameFinder;

/**
 * Abstract mojo that holds all the configuration parameters to specify and load
 * scenarios.
 * 
 * @author Mauro Talevi
 */
public abstract class AbstractScenarioMojo extends AbstractMojo {

    private static final String TEST_SCOPE = "test";

    /**
     * @parameter expression="${project.build.sourceDirectory}"
     * @required
     * @readonly
     */
    private String sourceDirectory;

    /**
     * @parameter expression="${project.build.testSourceDirectory}"
     * @required
     * @readonly
     */
    private String testSourceDirectory;

    /**
     * The scope of the mojo classpath, either "compile" or "test"
     * 
     * @parameter default-value="compile"
     */
    private String scope;

    /**
     * Scenario class names, if specified take precedence over the names
     * specificed via the "scenarioIncludes" and "scenarioExcludes" parameters
     * 
     * @parameter
     */
    private List<String> scenarioClassNames;

    /**
     * Scenario include filters, relative to the root source directory
     * determined by the scope
     * 
     * @parameter
     */
    private List<String> scenarioIncludes;

    /**
     * Scenario exclude filters, relative to the root source directory
     * determined by the scope
     * 
     * @parameter
     */
    private List<String> scenarioExcludes;

    /**
     * Compile classpath.
     * 
     * @parameter expression="${project.compileClasspathElements}"
     * @required
     * @readonly
     */
    private List<String> compileClasspathElements;

    /**
     * Test classpath.
     * 
     * @parameter expression="${project.testClasspathElements}"
     * @required
     * @readonly
     */
    private List<String> testClasspathElements;

    /**
     * Used to find scenario class names
     */
    private ScenarioClassNameFinder finder = new ScenarioClassNameFinder();

    /**
     * Determines if the scope of the mojo classpath is "test"
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
        getLog().debug("Found scenario class names: " + scenarioClassNames);
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
        List<String> classpathElements = compileClasspathElements;
        if (isTestScope()) {
            classpathElements = testClasspathElements;
        }
        return classpathElements;
    }

    /**
     * Returns the list of scenario instances, whose class names are either
     * specified via the parameter "scenarioClassNames" (which takes precedence)
     * or found using the parameters "scenarioIncludes" and "scenarioExcludes".
     * 
     * @return A List of Scenarios
     * @throws MojoExecutionException
     */
    protected List<RunnableScenario> scenarios() throws MojoExecutionException {
        List<String> names = scenarioClassNames;
        if (names == null || names.isEmpty()) {
            names = findScenarioClassNames();
        }
        if (names.isEmpty()) {
            getLog().info("No scenarios to run.");
        }
        ScenarioClassLoader classLoader = null;
        try {
            classLoader = createScenarioClassLoader();
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to create scenario class loader", e);
        }
        List<RunnableScenario> scenarios = new ArrayList<RunnableScenario>();
        for (String name : names) {
            try {
                scenarios.add(classLoader.newScenario(name));
            } catch (Exception e) {
                throw new MojoExecutionException("Failed to instantiate scenario '" + name + "'", e);
            }
        }
        return scenarios;
    }
}
