/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.ant;

import jbehave.core.behaviour.BehaviourClass;
import jbehave.core.behaviour.BehaviourVerifier;
import jbehave.core.listener.ValidatingListener;
import jbehave.core.listener.BehaviourListener;
import jbehave.core.listener.PlainTextListener;
import jbehave.core.util.Timer;
import net.sf.cotta.utils.ClassPath;
import net.sf.cotta.utils.ClassPathLocator;
import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.LogOutputStream;
import org.apache.tools.ant.types.Path;

import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class JBehaveTask extends AbstractJavaTask {
    private List behaviourClassList = new LinkedList();
    private AntClassLoader loader;

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
        commandLine.setClassname("jbehave.core.Run");
        for (Iterator iterator = behaviourClassList.iterator(); iterator.hasNext();) {
            BehaviourClassDetails behaviourClassDetail = (BehaviourClassDetails) iterator.next();
            commandLine.createArgument().setLine(behaviourClassDetail.getName());
        }
        if (run() != 0) {
            throw new BuildException("behaviour verification FAILED");
        }
        log("Behaviours verification passed");
    }

    public void executeOld() {
        try {
            verifyAll();
        } finally {
            if (loader != null) loader.resetThreadContextLoader();
        }
    }

    private void verifyAll() {
        createClassLoader();
        ValidatingListener listener = new ValidatingListener(
                new PlainTextListener(new OutputStreamWriter(
                        new LogOutputStream(this, Project.MSG_INFO)),
                        new Timer()));

        for (Iterator iter = behaviourClassList.iterator(); iter.hasNext();) {
            final BehaviourClassDetails behaviourClassDetails = (BehaviourClassDetails) iter.next();
            verifyBehaviourClass(behaviourClassDetails, listener);
            if (listener.verificationFailed()) throw new BuildException(behaviourClassDetails.getName() + "failed");
        }
    }

    private void verifyBehaviourClass(BehaviourClassDetails behaviourClassDetails, BehaviourListener listener) {
        try {
            BehaviourClass behaviourClass = new BehaviourClass(classFor(behaviourClassDetails), new BehaviourVerifier(listener));
            behaviourClass.verifyTo(listener);
        } catch (ClassNotFoundException e) {
            throw new BuildException(e);
        }
    }

    private Class classFor(BehaviourClassDetails behaviourClass) throws ClassNotFoundException {
        return Class.forName(behaviourClass.getName());
    }

    private ClassLoader createClassLoader() {
        Path path = commandLine.getClasspath();
        if (path != null) {
            Path classPath = (Path) path.clone();
            loader = getProject().createClassLoader(classPath);
            loader.setParentFirst(false);
            loader.addJavaLibraries();
            loader.setThreadContextLoader();
            return loader;
        } else {
            return getProject().getCoreLoader();
        }
    }
}
