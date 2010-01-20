package org.jbehave.scenario.steps.pico;

import org.jbehave.scenario.steps.CandidateSteps;
import org.jbehave.scenario.steps.Steps;
import org.jbehave.scenario.steps.StepsConfiguration;
import org.picocontainer.ComponentAdapter;
import org.picocontainer.MutablePicoContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PicoStepsFactory {
    private final StepsConfiguration configuration;
    private final MutablePicoContainer parent;

    public PicoStepsFactory(StepsConfiguration configuration, MutablePicoContainer parent) {
        this.configuration = configuration;
        this.parent = parent;
    }

    public CandidateSteps[] createCandidateSteps() {
        List<Steps> steps = new ArrayList<Steps>();
        for (ComponentAdapter<?> adapter : parent.getComponentAdapters()) {
            if (containsStepsAnnotations(adapter.getComponentImplementation())) {
                steps.add(new Steps(configuration, parent.getComponent(adapter.getComponentKey())));
            }
        }
        return steps.toArray(new CandidateSteps[steps.size()]);
    }

    private boolean containsStepsAnnotations(Class<?> componentClass) {
        for (Method method : componentClass.getMethods()) {
            for (Annotation annotation : method.getAnnotations()) {
                if (annotation.annotationType().getName().startsWith("org.jbehave.scenario.annotations")) {
                    return true;
                }
            }
        }
        return false;
    }
}
