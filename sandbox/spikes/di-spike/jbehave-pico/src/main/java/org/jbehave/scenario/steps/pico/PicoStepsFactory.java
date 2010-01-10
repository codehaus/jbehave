package org.jbehave.scenario.steps.pico;

import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.steps.CandidateSteps;
import org.jbehave.scenario.steps.Steps;
import org.jbehave.scenario.steps.StepsConfiguration;
import org.picocontainer.ComponentAdapter;
import org.picocontainer.MutablePicoContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PicoStepsFactory {
    private final StepsConfiguration configuration;
    private final MutablePicoContainer parent;

    public PicoStepsFactory(StepsConfiguration configuration, MutablePicoContainer parent) {
        this.configuration = configuration;
        this.parent = parent;
    }

    public CandidateSteps[] createCandidateSteps() {

        List<CandidateSteps> steps = new ArrayList<CandidateSteps>();

        Collection<ComponentAdapter<?>> adapters = parent.getComponentAdapters();
        for (ComponentAdapter<?> componentAdapter : adapters) {
            Class<?> impl = componentAdapter.getComponentImplementation();
            Method[] methods = impl.getMethods();
            if (isStep(methods)) {
                steps.add(new Steps(configuration, parent.getComponent(componentAdapter.getComponentKey())));
            }
        }
        return steps.toArray(new CandidateSteps[steps.size()]);
    }

    private boolean isStep(Method[] methods) {
        for (Method method : methods) {
            Annotation[] anns = method.getAnnotations();
            for (Annotation ann : anns) {
                if (ann.annotationType() == Given.class || ann.annotationType() == When.class || ann.annotationType() == Then.class) {
                    return true;
                }
            }
        }
        return false;
    }
}
