package org.jbehave.mojo;

import java.net.MalformedURLException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.jbehave.core.BehaviourRunner;
import org.jbehave.core.behaviour.Behaviours;

/**
 * Mojo to run Behaviours
 * 
 * @author Mauro Talevi
 * @goal run-behaviours
 */
public class BehaviourRunnerMojo  extends AbstractJBehaveMojo {
    
    /**
     * @parameter
     * @required true
     */
    private String behavioursClassName;
    
    private BehaviourRunner runner = new BehaviourRunner();
    
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            getLog().info("Running behaviours "+ behavioursClassName);
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
        BehavioursClassLoader cl = createBehavioursClassLoader();
        return cl.newBehaviours(name);
    }

}
