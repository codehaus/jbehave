package org.jbehave.mojo;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;

/**
 * Abstract base class containing common functionality for all JBehave Mojos.
 * 
 * @author Mauro Talevi
 */
public abstract class AbstractJBehaveMojo extends AbstractMojo {

    private static final String TEST_SCOPE = "test";

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
     * The scope of the mojo classpath
     * 
     * @parameter default-value="compile"
     */
    private String scope;

    /**
     * Determines if the scope of the mojo classpath is "test"
     * 
     * @return A boolean <code>true</code> if test scoped
     */
    protected boolean isTestScope() {
        return TEST_SCOPE.equals(scope);
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
