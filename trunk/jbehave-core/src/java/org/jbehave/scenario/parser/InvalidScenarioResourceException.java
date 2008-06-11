package org.jbehave.scenario.parser;

/**
 * Thrown when a scenario resource is not valid
 * 
 * @author Mauro Talevi
 */
@SuppressWarnings("serial")
public class InvalidScenarioResourceException extends RuntimeException {

    public InvalidScenarioResourceException(String message, Throwable cause) {
        super(message, cause);
    }

}
