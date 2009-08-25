/*
 * Created on 24-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.visitor;

import jbehave.core.behaviour.BehaviourClass;
import jbehave.core.behaviour.BehaviourMethod;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface Visitor {
	void visitBehaviourMethod(BehaviourMethod behaviourMethod);
	void visitBehaviourClass(BehaviourClass behaviourClass);
}
