/*
 * Created on 05-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;

import java.lang.reflect.Method;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourMethod implements Behaviour {

    private final Object instance;
    private final Method methodToVerify;
    private final Verifier verifier;

    public BehaviourMethod(Object instance, Method methodToVerify, Verifier verifier) {
        this.instance = instance;
        this.methodToVerify = methodToVerify;
        this.verifier = verifier;
    }

    public Result verify(Verifier ignored) {
        verifier.verify(this);
        return null;
    }

    public Method methodToVerify() {
        return methodToVerify;
    }

    public Object instance() {
        return instance;
    }

    public void accept(Visitor visitor) {
        visitor.before(this);
        Result result = verifier.verify(this);
        visitor.gotResult(result);
        visitor.after(this);
    }
}
