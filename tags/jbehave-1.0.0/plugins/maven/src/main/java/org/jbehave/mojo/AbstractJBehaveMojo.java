package org.jbehave.mojo;

import java.util.List;

import org.apache.maven.plugin.AbstractMojo;

/**
 * Abstract base class containing common functionality for all JBehave Mojos.
 * 
 * @author Mauro Talevi
 */
public abstract class AbstractJBehaveMojo extends AbstractMojo {
    
    /**
     * Compile classpath.
     *
     * @parameter expression="${project.compileClasspathElements}"
     * @required
     * @readonly
     */
    List classpathElements;
    
   
}
