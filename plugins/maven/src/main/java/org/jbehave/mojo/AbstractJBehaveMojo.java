package org.jbehave.mojo;

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
    protected List compileClasspathElements;
    
    /**
     * Test classpath.
     *
     * @parameter expression="${project.testClasspathElements}"
     * @required
     * @readonly
     */
    protected List testClasspathElements;
   
    /**
     * The scope of the mojo classpath
     *
     * @parameter default-value="compile" 
     */
    protected String scope;
    
    /**
     * Returns the compile or test classpath elements based on the scope
     * @return A List of classpath elements
     */
    protected List getClasspathElements(){
        if ( TEST_SCOPE.equals(scope) ){
            return testClasspathElements;
        }
        return compileClasspathElements;
    }
}
