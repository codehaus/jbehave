/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.extensions.ant;

import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.LogOutputStream;
import org.apache.tools.ant.types.CommandlineJava;
import org.apache.tools.ant.types.Path;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.io.OutputStreamWriter;

import jbehave.extensions.ant.listeners.AntListener;
import jbehave.listeners.CompositeListener;
import jbehave.listeners.TextListener;
import jbehave.framework.SpecVerifier;
import jbehave.framework.Listener;

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
public class JBehaveAntTask extends org.apache.tools.ant.Task {
	private List specList;
	private CommandlineJava commandLine = new CommandlineJava();
	private AntClassLoader loader;

	public JBehaveAntTask() {
		specList = new LinkedList();
	}

	public Spec createSpec() {
		Spec spec = new Spec();
		specList.add(spec);
		return spec;
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
		AntListener listener = new AntListener();
		for (Iterator iter = specList.iterator(); iter.hasNext(); ) {
			verifySpec((Spec) iter.next(), listener);
		}
	}

	private void verifySpec(Spec spec, AntListener listener) {
		try {
			SpecVerifier verifier = new SpecVerifier(getSpec(spec.getSpecName()));
			verifier.verifySpec(createCompositeListener(listener));
			if (listener.failBuild()) throw new BuildException(spec.getSpecName() + "failed");
		} catch (ClassNotFoundException e) {
        	throw new BuildException(e);
		}
	}

	private Listener createCompositeListener(AntListener antListener) {
		CompositeListener compositeListener = new CompositeListener();
		compositeListener.add(antListener);
		compositeListener.add(new TextListener(new OutputStreamWriter(new LogOutputStream(this, Project.MSG_INFO))));
		return compositeListener;
	}

	private Class getSpec(String specName) throws ClassNotFoundException {
		return Class.forName(specName);
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
