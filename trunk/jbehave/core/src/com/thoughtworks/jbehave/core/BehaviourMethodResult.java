/*
 * Created on 24-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourMethodResult extends Result {

    private final BehaviourMethod behaviourMethod;
    
    public BehaviourMethodResult(BehaviourMethod behaviourMethod, Throwable cause) {
        super(behaviourMethod.method().getName(), cause);
        this.behaviourMethod = behaviourMethod;
    }
    
    public BehaviourMethodResult(BehaviourMethod behaviourMethod) {
        this(behaviourMethod, null);
    }

    public BehaviourMethod behaviourMethod() {
        return behaviourMethod;
    }
}
