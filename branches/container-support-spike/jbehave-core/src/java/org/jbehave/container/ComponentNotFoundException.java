package org.jbehave.container;


/**
 * Thrown when no component is found for a given key or type in the container
 * 
 * @author Mauro Talevi
 */
@SuppressWarnings("serial")
public class ComponentNotFoundException extends RuntimeException {

    public ComponentNotFoundException(String message) {
        super(message);
    }

    public ComponentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
