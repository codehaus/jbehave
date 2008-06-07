package org.jbehave.mojo;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.jbehave.scenario.parser.ScenarioClassNameFinder;

/**
 * Abstract base class containing common functionality for all JBehave Mojos.
 * 
 * @author Mauro Talevi
 */
public abstract class AbstractJBehaveMojo extends AbstractMojo {

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
     * The scope of the mojo classpath, either "compile" or "test"
     * 
     * @parameter default-value="compile"
     */
    private String scope;

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
     * Used to find scenario class names
     */
    private ScenarioClassNameFinder finder = new ScenarioClassNameFinder();

    /**
     * Determines if the scope of the mojo classpath is "test"
     * 
     * @return A boolean <code>true</code> if test scoped
     */
    protected boolean isTestScope() {
        return TEST_SCOPE.equals(scope);
    }

    private String rootSourceDirectory() {
        if (isTestScope()) {
            return testSourceDirectory;
        }
        return sourceDirectory;
    }

    protected List<String> findScenarioClassNames() {
        List<String> scenarioClassNames = finder.listScenarioClassNames(rootSourceDirectory(), null,
                scenarioIncludes, scenarioExcludes);
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
    protected ScenarioClassLoader createScenarioClassLoader() throws MalformedURLException {
        List<String> classpathElements = compileClasspathElements;
        if (isTestScope()) {
            classpathElements = testClasspathElements;
        }
        return new ScenarioClassLoader(classpathElements);
    }
}
