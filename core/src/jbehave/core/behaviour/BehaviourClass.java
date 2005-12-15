/*
 * Created on 05-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.behaviour;

import java.lang.reflect.Method;

import jbehave.core.exception.JBehaveFrameworkError;
import jbehave.core.listener.BehaviourListener;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourClass implements Behaviour {

    private final Class classToVerify;
    private final BehaviourVerifier verifier;

    public BehaviourClass(Class classToVerify) {
        throw new UnsupportedOperationException();
//        this(classToVerify, new BehaviourVerifier());
    }

    public BehaviourClass(Class classToVerify, BehaviourVerifier verifier) {
        this.classToVerify = classToVerify;
        this.verifier = verifier;
    }

    public void verifyTo(BehaviourListener listener) {
        traverseMethodsWith(new MethodVerifier(verifier));
    }
    
    public int countBehaviours() {
        MethodCounter counter = new MethodCounter();
        traverseMethodsWith(counter);
        return counter.total();
    }

    private void traverseMethodsWith(MethodHandler methodHandler) {
        if (Behaviours.class.isAssignableFrom(classToVerify)) {
            Behaviours behaviours = (Behaviours) createInstance();
            Class[] nestedClasses = behaviours.getBehaviours();
            for (int i = 0; i < nestedClasses.length; i++) {
                methodHandler.handleClass(new BehaviourClass(nestedClasses[i], verifier));
            }
        }
        Method[] methods = classToVerify.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.getName().startsWith("should")) {
                methodHandler.handleMethod(new BehaviourMethod(createInstance(), method));
            }
        }
    }

    private Object createInstance() {
        try {
            return classToVerify.newInstance();
        }
        catch (Exception e) {
            throw new JBehaveFrameworkError("Unable to create instance of " + classToVerify.getName(), e);
        }
    }
    
    public String toString() {
        return "BehaviourClass: " + classToVerify.getName();
    }

    /** @deprecated */
    public Class classToVerify() {
        return classToVerify;
    }
}
