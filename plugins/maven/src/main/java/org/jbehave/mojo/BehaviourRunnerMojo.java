package org.jbehave.mojo;

import java.net.MalformedURLException;
import java.util.List;

import org.jbehave.core.Run;
import org.jbehave.core.behaviour.Behaviours;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Mojo to run Behaviours
 * 
 * @author Mauro Talevi
 * @goal run-behaviours
 */
public class BehaviourRunnerMojo  extends AbstractMojo {
    
    /**
     * Compile classpath.
     *
     * @parameter expression="${project.compileClasspathElements}"
     * @required
     * @readonly
     */
    List classpathElements;
    
    /**
     * @parameter
     * @required true
     */
    String behavioursClassName;
    
    private Run runner = new Run(System.out);
    
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            getLog().debug("Running behaviours "+ behavioursClassName);
            Behaviours behaviours = loadBehaviours(behavioursClassName);
            Class[] classes = behaviours.getBehaviours();
            for (int i = 0; i < classes.length; i++) {
                runner.verifyBehaviour(classes[i]);
            }            
        } catch (Exception e) {
            throw new MojoExecutionException("Failed to verify behaviours", e);
        }
    }

    private Behaviours loadBehaviours(String name) throws MalformedURLException, InstantiationException, IllegalAccessException {        
        BehavioursClassLoader cl = new BehavioursClassLoader(classpathElements);
        return cl.newBehaviours(name);
    }

}
