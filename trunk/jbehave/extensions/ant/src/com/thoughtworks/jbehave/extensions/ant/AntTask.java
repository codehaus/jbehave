/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.extensions.ant;

import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.LogOutputStream;
import org.apache.tools.ant.types.CommandlineJava;
import org.apache.tools.ant.types.Path;

import com.thoughtworks.jbehave.core.BehaviourClass;
import com.thoughtworks.jbehave.core.listeners.TextReporter;
import com.thoughtworks.jbehave.core.verifiers.InvokeMethodWithSetUpAndTearDown;
import com.thoughtworks.jbehave.extensions.ant.listeners.AntVisitor;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 * How do I use this task?
 *
 * First, define the taskdef as follows.
 *
 * &lt;taskdef name="jbehave"
 *                classname="jbehave.extensions.ant.JBehaveAntTask"
 *                classpath="jbehave-ant.jar"/&gt;
 *
 * Now create the behave target. You can have mulitple &lt;spec&gt; elements. Each one
 * can be a single spec or a SpecContainer.
 *
 *	&lt;target name="behave" depends="spec.compile"&gt;
 *		&lt;jbehave&gt;
 *			&lt;spec specname="jbehave.AllSpecs"/&gt;
 *			&lt;classpath&gt;
 *				&lt;pathelement location="${classes.dir}"/&gt;
 *				&lt;pathelement location="${spec.classes.dir}"/&gt;
 *			&lt;/classpath&gt;
 *		&lt;/jbehave&gt;
 *	&lt;/target&gt;
 */
public class AntTask extends org.apache.tools.ant.Task {
	private List behaviourClassList;
	private CommandlineJava commandLine = new CommandlineJava();
	private AntClassLoader loader;

	public AntTask() {
		behaviourClassList = new LinkedList();
	}

	public BehaviourClassDetails createBehaviourClass() {
		BehaviourClassDetails details = new BehaviourClassDetails();
		behaviourClassList.add(details);
		return details;
	}

	public Path createClasspath() {
		return commandLine.createClasspath(getProject()).createPath();
	}

	public void execute() {
		try {
			verifyAll();
		} finally {
			if (loader != null) loader.resetThreadContextLoader();
		}
	}

	private void verifyAll() {
		createClassLoader();
        TextReporter textReporter = new TextReporter(new OutputStreamWriter(new LogOutputStream(this, Project.MSG_INFO)));
        AntVisitor antVisitor = new AntVisitor(textReporter);
		for (Iterator iter = behaviourClassList.iterator(); iter.hasNext(); ) {
			verifyBehaviourClass((BehaviourClassDetails) iter.next(), antVisitor);
		}
	}

	private void verifyBehaviourClass(BehaviourClassDetails behaviourClassDetails, AntVisitor antVisitor) {
		try {
            BehaviourClass visitableClass = new BehaviourClass(classFor(behaviourClassDetails), new InvokeMethodWithSetUpAndTearDown());
            visitableClass.accept(antVisitor);
			if (antVisitor.verificationFailed()) throw new BuildException(behaviourClassDetails.getBehaviourClassName() + "failed");
		} catch (ClassNotFoundException e) {
        	throw new BuildException(e);
		}
	}

	private Class classFor(BehaviourClassDetails behaviourClass) throws ClassNotFoundException {
		return Class.forName(behaviourClass.getBehaviourClassName());
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
		}  else {
			return getProject().getCoreLoader();
		}
	}
}
