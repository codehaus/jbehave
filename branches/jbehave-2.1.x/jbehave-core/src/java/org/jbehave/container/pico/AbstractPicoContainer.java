package org.jbehave.container.pico;

import static java.text.MessageFormat.format;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.jbehave.container.ComponentNotFoundException;
import org.jbehave.container.Container;
import org.jbehave.container.InvalidContainerException;
import org.picocontainer.ComponentAdapter;
import org.picocontainer.PicoContainer;
import org.picocontainer.script.ScriptedContainerBuilder;

/**
 * <p>
 * Abstract implementation of Container which uses a PicoContainer as delegate
 * container.
 * </p>
 * 
 * @author Mauro Talevi
 */
public abstract class AbstractPicoContainer implements Container {

    private final ClassLoader classLoader;
    private final PicoContainer container;

    protected AbstractPicoContainer(String resource) {
        this(resource, Thread.currentThread().getContextClassLoader());
    }

    protected AbstractPicoContainer(String resource, ClassLoader classLoader) {
        this.classLoader = classLoader;
        this.container = buildContainer(resource);
    }

    public <T> T getComponent(Class<T> type) {
        return getComponent(type, null);
    }

    public <T> T getComponent(Class<T> type, Object key) {
        List<ComponentAdapter<T>> adapters = container.getComponentAdapters(type);
        if (adapters.isEmpty()) {
            String message = format("No component registered in container of type {0}", type);
            throw new ComponentNotFoundException(message);
        }
        if (key != null) {            
            // a key has been provided: return the component for that key
            for (ComponentAdapter<T> adapter : adapters) {
                if (key.equals(adapter.getComponentKey())) {
                    T component = adapter.getComponentInstance(container, type);
                    if ( component != null ){
                        return component;
                    }
                }
            }
            String message = format("No component registered in container of type {0} and for key {1}", type, key);
            throw new ComponentNotFoundException(message);
        } else {
            // no key has been found:
            // return first of registered components
            return adapters.iterator().next().getComponentInstance(container, type);
        }
    }

    private PicoContainer buildContainer(String resource) {
        Reader script = getReader(resource, classLoader);
        ScriptedContainerBuilder builder = createContainerBuilder(script, classLoader);
        return builder.buildContainer(null, null, false);
    }

    private Reader getReader(String resource, ClassLoader classLoader) {
        InputStream is = classLoader.getResourceAsStream(resource);
        if (is == null) {
            String message = format("Resource {0} not found in ClassLoader {1}", resource, classLoader.getClass(),
                    classLoader);
            throw new InvalidContainerException(message);
        }
        return new InputStreamReader(is);
    }

    /**
     * Allow concrete implementations to specify a ScriptedContainerBuilder
     * 
     * @param script the Reader containing the container script
     * @param classLoader the ClassLoader
     * @return A ScriptedContainerBuilder
     */
    protected abstract ScriptedContainerBuilder createContainerBuilder(Reader script, ClassLoader classLoader);

}
