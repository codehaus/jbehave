/*
 * Created on 11-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.framework;

import java.io.OutputStreamWriter;
import java.lang.reflect.Method;

import jbehave.framework.exception.BehaviourFrameworkError;
import jbehave.framework.listeners.TextListener;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class SpecVerifier {
    private final Class spec;

    public SpecVerifier(Class spec) {
        this.spec = spec;
    }

    public void verifySpec(Listener listener) {
        try {
            listener.specVerificationStarting(spec);
            if (SpecContainer.class.isAssignableFrom(spec)) {
                verifyContainedSpecs((SpecContainer) spec.newInstance(), listener);
            }
            Method methods[] = spec.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                if (method.getName().startsWith("should") && method.getParameterTypes().length == 0) {
                    CriteriaVerifier criteriaVerifier = new CriteriaVerifier(method);
                    criteriaVerifier.verifyCriteria(listener);
                }
            }
            listener.specVerificationEnding(spec);
        } catch (Exception e) {
            throw new BehaviourFrameworkError("Problem verifying spec", e);
        }
    }
    
    private void verifyContainedSpecs(SpecContainer container, Listener listener) throws Exception {
        Class[] containedSpecs = container.getSpecs();
        for (int i = 0; i < containedSpecs.length; i++) {
            new SpecVerifier(containedSpecs[i]).verifySpec(listener);
        }
    }
    
    public static void main(String[] args) throws Exception {
        Listener listener = new TextListener(new OutputStreamWriter(System.out));
        new SpecVerifier(Class.forName(args[0])).verifySpec(listener);
    }
}
