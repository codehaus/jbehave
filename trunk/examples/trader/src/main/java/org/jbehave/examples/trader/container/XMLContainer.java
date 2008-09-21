package org.jbehave.examples.trader.container;

import java.io.InputStreamReader;
import java.io.Reader;

import org.jbehave.container.Container;
import org.picocontainer.PicoContainer;
import org.picocontainer.script.ScriptedContainerBuilder;
import org.picocontainer.script.xml.XMLContainerBuilder;

/**
 * Simplified XML Pico-based Container
 * 
 * @author Mauro Talevi
 */
public class XMLContainer implements Container {

    private final ClassLoader classLoader;
    private final PicoContainer container;

    public XMLContainer(String resource) {
        this(resource, Thread.currentThread().getContextClassLoader());
    }

    public XMLContainer(String resource, ClassLoader classLoader) {
        this.classLoader = classLoader;
        this.container = buildContainer(resource);
    }

    public <T> T getComponent(Class<T> type) {
        return container.getComponent(type);
    }

    @SuppressWarnings("unchecked")
    public <T> T getComponent(Class<T> type, Object key) {
        return (T) container.getComponent(key);
    }

    private PicoContainer buildContainer(String resource) {
        Reader script = getReader(resource, classLoader);
        ScriptedContainerBuilder builder = createContainerBuilder(script, classLoader);
        return builder.buildContainer(null, null, false);
    }

    private Reader getReader(String resource, ClassLoader classLoader) {
        return new InputStreamReader(classLoader.getResourceAsStream(resource));
    }
    
    protected ScriptedContainerBuilder createContainerBuilder(Reader script, ClassLoader classLoader) {
        return new XMLContainerBuilder(script, classLoader);
    }

}
