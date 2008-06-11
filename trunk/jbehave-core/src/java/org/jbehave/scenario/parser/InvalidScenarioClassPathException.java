package org.jbehave.scenario.parser;

/**
 * Thrown when a scenario class path is not valid
 * 
 * @author Mauro Talevi
 */
@SuppressWarnings("serial")
public class InvalidScenarioClassPathException extends RuntimeException {

    public InvalidScenarioClassPathException(String message) {
        super(message);
    }

}
