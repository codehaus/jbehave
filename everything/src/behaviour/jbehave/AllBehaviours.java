package jbehave;
import jbehave.core.behaviour.Behaviours;

public class AllBehaviours implements Behaviours {

	public Class[] getBehaviours() {
		return new Class[] {
				jbehave.core.AllBehaviours.class,
				jbehave.ant.AllBehaviours.class,
				jbehave.jmock.AllBehaviours.class,
				jbehave.junit.AllBehaviours.class
		};
	}
}
