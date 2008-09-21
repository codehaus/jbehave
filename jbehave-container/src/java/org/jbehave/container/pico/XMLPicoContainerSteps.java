package org.jbehave.container.pico;

import org.jbehave.container.Container;
import org.jbehave.container.ContainerSteps;
import org.jbehave.scenario.steps.StepsConfiguration;

/**
 * XMLPicoContainer-based Steps decorator.
 * 
 * @author Mauro Talevi
 */
public class XMLPicoContainerSteps extends ContainerSteps {

    public XMLPicoContainerSteps(String containerResource) {
        super(containerResource);
    }

    public XMLPicoContainerSteps(String containerResource, ClassLoader classLoader) {
        super(containerResource, classLoader);
    }
    
    public XMLPicoContainerSteps(String containerResource, ClassLoader classLoader, StepsConfiguration configuration) {
        super(containerResource, classLoader, configuration);
    }

    protected Container createContainer(String containerResource, ClassLoader classLoader) {
        return new XMLPicoContainer(containerResource, classLoader);
    }

}
