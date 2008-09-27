package org.jbehave.container;

/**
 * Thrown when a container cannot be created
 * 
 * @author Mauro Talevi
 */
@SuppressWarnings("serial")
public class InvalidContainerException extends RuntimeException {

    public InvalidContainerException(String message) {
        super(message);
    }

}
