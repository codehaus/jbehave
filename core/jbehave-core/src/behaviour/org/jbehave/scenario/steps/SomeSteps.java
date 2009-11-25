package org.jbehave.scenario.steps;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.lang.reflect.Method;
import java.util.List;

import org.jbehave.scenario.definition.ExamplesTable;

public class SomeSteps extends Steps {
    Object args;

    public void aMethod() {

    }

    public void aMethodWith(String args) {
        this.args = args;
    }

    public void aMethodWith(double args) {
        this.args = args;
    }

    public void aMethodWith(long args) {
        this.args = args;
    }

    public void aMethodWith(int args) {
        this.args = args;
    }

    public void aMethodWith(float args) {
        this.args = args;
    }

    public void aMethodWithListOfStrings(List<String> args) {
        this.args = args;
    }

    public void aMethodWithListOfLongs(List<Long> args) {
        this.args = args;
    }

    public void aMethodWithListOfIntegers(List<Integer> args) {
        this.args = args;
    }

    public void aMethodWithListOfDoubles(List<Double> args) {
        this.args = args;
    }

    public void aMethodWithListOfFloats(List<Float> args) {
        this.args = args;
    }
    
    public void aMethodWithListOfNumbers(List<Number> args) {
        this.args = args;
    }

    public void aMethodWithExamplesTable(ExamplesTable args) {
        this.args = args;
    }

    public static Method methodFor(String methodName) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(SomeSteps.class);
        for (MethodDescriptor md : beanInfo.getMethodDescriptors()) {
            if (md.getMethod().getName().equals(methodName)) {
                return md.getMethod();
            }
        }
        return null;
    }

}
