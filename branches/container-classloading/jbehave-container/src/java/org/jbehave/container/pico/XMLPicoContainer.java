package org.jbehave.container.pico;

import java.io.Reader;

import org.picocontainer.script.ScriptedContainerBuilder;
import org.picocontainer.script.xml.XMLContainerBuilder;

/**
 * PicoContainer which uses XMLContainerBuilder to build the delegate container
 * 
 * @author Mauro Talevi
 */
public class XMLPicoContainer extends AbstractPicoContainer {

    public XMLPicoContainer(String resource) {
        super(resource);
    }

    public XMLPicoContainer(String resource, ClassLoader classLoader) {
        super(resource, classLoader);
    }

    protected ScriptedContainerBuilder createContainerBuilder(Reader script, ClassLoader classLoader) {
        return new XMLContainerBuilder(script, classLoader);
    }

}
