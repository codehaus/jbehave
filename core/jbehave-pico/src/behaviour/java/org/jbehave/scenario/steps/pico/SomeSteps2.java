package org.jbehave.scenario.steps.pico;

import org.jbehave.scenario.definition.ExamplesTable;
import org.jbehave.scenario.steps.Steps;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.lang.reflect.Method;
import java.util.List;

public class SomeSteps2 extends Steps {

    public SomeSteps2() {
        System.out.println("");
    }

    public Object args;

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
        BeanInfo beanInfo = Introspector.getBeanInfo(SomeSteps2.class);
        for (MethodDescriptor md : beanInfo.getMethodDescriptors()) {
            if (md.getMethod().getName().equals(methodName)) {
                return md.getMethod();
            }
        }
        return null;
    }

}
