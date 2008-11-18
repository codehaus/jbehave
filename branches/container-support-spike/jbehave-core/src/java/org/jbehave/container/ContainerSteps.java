package org.jbehave.container;

import org.jbehave.container.pico.XMLPicoContainerSteps;
import org.jbehave.scenario.steps.Steps;
import org.jbehave.scenario.steps.StepsConfiguration;

/**
 * <p>
 * Abstract steps decorator which adds container support. Users need to extend
 * this class and provide a concrete implementation of Container.
 * </p>
 * <p>
 * Concrete implementations provided are {@link XMLPicoContainerSteps}.
 * </p>
 * 
 * @author Mauro Talevi
 */
public abstract class ContainerSteps extends Steps {

    private Container container;

    public ContainerSteps(String containerResource) {
        this(containerResource, Thread.currentThread().getContextClassLoader());
    }

    public ContainerSteps(String containerResource, ClassLoader classLoader) {    
        this(containerResource, classLoader, new StepsConfiguration());
    }

    public ContainerSteps(String containerResource, ClassLoader classLoader, StepsConfiguration configuration) {
        super(configuration);
        container = createContainer(containerResource, classLoader);
    }

    public <T> T component(Class<T> type) {
        return container.getComponent(type);
    }

    public <T> T getComponent(Class<T> type, Object key) {
        return container.getComponent(type, key);
    }

    protected abstract Container createContainer(String containerResource, ClassLoader classLoader);

}
