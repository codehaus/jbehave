/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.extensions.ant;


/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class BehaviourClass {
	private String behaviourClassName;

	public void setBehaviourClassName(String behaviourClassName) {
		this.behaviourClassName = behaviourClassName;

	}

	public String getBehaviourClassName() {
		return behaviourClassName;
	}
}
