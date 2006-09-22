/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.ant;


/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class BehaviourClassDetails {
	private String behaviourClassName;

	public void setName(String behaviourClassName) {
		this.behaviourClassName = behaviourClassName;

	}

	public String getName() {
		return behaviourClassName;
	}
}
