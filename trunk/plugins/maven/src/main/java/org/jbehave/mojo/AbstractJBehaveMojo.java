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
    private List compileClasspathElements;
    
    /**
     * Test classpath.
     *
     * @parameter expression="${project.testClasspathElements}"
     * @required
     * @readonly
     */
    private List testClasspathElements;
   
    /**
     * The scope of the mojo classpath
     *
     * @parameter default-value="compile" 
     */
    private String scope;

    /**
     * Creates the Behaviours ClassLoader with the classpath element of the selected scope
     * @return A BehavioursClassLoader
     * @throws MalformedURLException
     */
    protected BehavioursClassLoader createBehavioursClassLoader() throws MalformedURLException {
        List classpathElements = compileClasspathElements;
        if ( TEST_SCOPE.equals(scope) ){
            classpathElements = testClasspathElements;
        } 
        return new BehavioursClassLoader(classpathElements);
    }

}
