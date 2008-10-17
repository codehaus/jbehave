package org.jbehave.scenario.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AfterScenario {

	/**
	 * When set it signals that the annotated method should be invoked only upon scenario outcome, successful or not
	 * 
	 * @return A boolean, true if method should be invoked only upon successful scenario
	 */
	boolean successful() default true;

}
