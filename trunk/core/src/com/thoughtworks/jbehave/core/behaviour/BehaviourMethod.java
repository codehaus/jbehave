/*
 * Created on 05-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.behaviour;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.visitor.Visitable;
import com.thoughtworks.jbehave.core.visitor.Visitor;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourMethod implements Visitable {

    private final Object instance;
    private final Method method;

    public BehaviourMethod(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
    }

    public Method method() {
        return method;
    }

    public Object instance() {
        return instance;
    }

    public void accept(Visitor visitor) {
        visitor.visitBehaviourMethod(this);
    }
}
