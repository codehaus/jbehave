package jbehave.ant;

import jbehave.core.Run;
import jbehave.core.behaviour.Behaviours;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Path;

/**
* Ant Task to verify Behaviours
* 
* @author Mauro Talevi
*/
public class BehaviourRunnerTask extends AbstractJavaTask {
    
    private String behavioursClassName;
    private Run runner = new Run(System.out);

    public void execute() throws BuildException {
        try {
            Behaviours behaviours = loadBehaviours(behavioursClassName);
            Class[] classes = behaviours.getBehaviours();
            for (int i = 0; i < classes.length; i++) {
                runner.verifyBehaviour(classes[i]);
            }            
        } catch (Exception e) {
            throw new BuildException("Failed to verify behaviours "+behavioursClassName, e);
        }
    }

    public void setBehavioursClassName(String behavioursClassName) {
        this.behavioursClassName = behavioursClassName;
    }

    private Behaviours loadBehaviours(String name) throws InstantiationException, IllegalAccessException, ClassNotFoundException {        
        return (Behaviours) createClassLoader().loadClass(name).newInstance();
    }
    
    private ClassLoader createClassLoader() {
        Path path = commandLine.getClasspath();
        if (path != null) {
            Path classPath = (Path) path.clone();
            AntClassLoader loader = getProject().createClassLoader(classPath);
            loader.setParentFirst(false);
            loader.addJavaLibraries();
            loader.setThreadContextLoader();
            return loader;
        } else {
            return getProject().getCoreLoader();
        }
    }

}
