package jbehave;
import jbehave.core.behaviour.Behaviours;

public class AllBehaviours implements Behaviours {

	public Class[] getBehaviourClasses() {
		return new Class[] {
				jbehave.ant.AllBehaviours.class,
				jbehave.core.AllBehaviours.class,
				jbehave.jmock.AllBehaviours.class,
				jbehave.junit.AllBehaviours.class,
				jbehave.story.AllBehaviours.class
		};
	}
}
