package org.jbehave.container;

/**
 * Container represents a simple facade to access components from lightweight
 * containers. Different implementations will provide adapters to different
 * containers.
 * 
 * @author Mauro Talevi
 */
public interface Container {

    /**
     * Returns a component of a given type
     * 
     * @param type the component Class type
     * @return The component instance of type <T>
     * @throws ComponentNotFoundException when component not found
     */
    <T> T getComponent(Class<T> type);

    /**
     * Returns a component for a given type and key. It first looks up the
     * components by type and then from these the one with the provided key.
     * 
     * @param type the component Class type
     * @param key the component Object key
     * @return The component instance of type <T>
     * @throws ComponentNotFoundException when component not found
     */
    <T> T getComponent(Class<T> type, Object key);

}
