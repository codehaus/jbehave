package org.jbehave;
import org.jbehave.core.behaviour.Behaviours;

public class AllBehaviours implements Behaviours {

	public Class[] getBehaviours() {
		return new Class[] {
				org.jbehave.core.AllBehaviours.class,
				org.jbehave.ant.AllBehaviours.class,
                org.jbehave.classmock.AllBehaviours.class,
				org.jbehave.jmock.AllBehaviours.class,
				org.jbehave.junit.AllBehaviours.class,
                org.jbehave.threaded.swing.AllBehaviours.class
		};
	}
}
