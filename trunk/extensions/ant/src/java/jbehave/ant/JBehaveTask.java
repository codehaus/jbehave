/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.ant;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jbehave.core.Run;
import net.sf.cotta.utils.ClassPath;
import net.sf.cotta.utils.ClassPathLocator;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Path;

public class JBehaveTask extends AbstractJavaTask {
    private List behaviourClassList = new LinkedList();

    public JBehaveTask() {
    }

    public JBehaveTask(CommandRunner runner) {
        super(runner);
    }
 
    public BehaviourClassDetails createVerify() {
        BehaviourClassDetails details = new BehaviourClassDetails();
        behaviourClassList.add(details);
        return details;
    }

    public void setBehavioursClassName(String behavioursClassName) {
        BehaviourClassDetails details = new BehaviourClassDetails();
        details.setName(behavioursClassName);
        behaviourClassList.add(details);
    }
    
    public void execute() {
        validateElements();
        appendAntTaskJar();
        invoke();
    }

    private void appendAntTaskJar() {
        ClassPath classPath = new ClassPathLocator(getClass()).locate();
        createClasspath().append(new Path(getProject(), classPath.path()));
    }

    private void validateElements() {
    }

    private void invoke() {
        commandLine.setClassname(Run.class.getName());
        for (Iterator iterator = behaviourClassList.iterator(); iterator.hasNext();) {
            BehaviourClassDetails behaviourClassDetail = (BehaviourClassDetails) iterator.next();
            commandLine.createArgument().setLine(behaviourClassDetail.getName());
        }
        if (run() != 0) {
            throw new BuildException("behaviour verification FAILED");
        }
        log("Behaviours verification passed");
    }

}
