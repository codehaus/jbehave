package org.jbehave.container.pico;

import org.jbehave.container.Container;
import org.jbehave.container.ContainerSteps;

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

    protected Container createContainer(String containerResource, ClassLoader classLoader) {
        return new XMLPicoContainer(containerResource, classLoader);
    }

}
