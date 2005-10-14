package com.thoughtworks.jbehave;
import com.thoughtworks.jbehave.core.behaviour.Behaviours;

public class AllBehaviours implements Behaviours {

	public Class[] getBehaviourClasses() {
		return new Class[] {
				com.thoughtworks.jbehave.ant.AllBehaviours.class,
				com.thoughtworks.jbehave.core.AllBehaviours.class,
				com.thoughtworks.jbehave.jmock.AllBehaviours.class,
				com.thoughtworks.jbehave.junit.AllBehaviours.class,
				com.thoughtworks.jbehave.story.AllBehaviours.class
		};
	}
}
