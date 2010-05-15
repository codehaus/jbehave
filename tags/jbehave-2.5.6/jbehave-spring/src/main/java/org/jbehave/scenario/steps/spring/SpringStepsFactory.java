package org.jbehave.scenario.steps.spring;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jbehave.scenario.steps.CandidateSteps;
import org.jbehave.scenario.steps.Steps;
import org.jbehave.scenario.steps.StepsConfiguration;
import org.springframework.beans.factory.ListableBeanFactory;

/**
 * A factory class for {@link CandidateSteps} that uses a
 * {@link ListableBeanFactory} for the composition and instantiation of all
 * components that contains scenario annotated methods.
 * 
 * @author Paul Hammant
 * @author Mauro Talevi
 */
public class SpringStepsFactory {

    private final StepsConfiguration configuration;
    private final ListableBeanFactory parent;

    public SpringStepsFactory(StepsConfiguration configuration, ListableBeanFactory parent) {
        this.configuration = configuration;
        this.parent = parent;
    }

    public CandidateSteps[] createCandidateSteps() {
        List<Steps> steps = new ArrayList<Steps>();
        for (String name : parent.getBeanDefinitionNames()) {
            Object bean = parent.getBean(name);
            if (containsScenarioAnnotations(bean.getClass())) {
                steps.add(new Steps(configuration, bean));
            }
        }
        return steps.toArray(new CandidateSteps[steps.size()]);
    }

    private boolean containsScenarioAnnotations(Class<?> componentClass) {
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
