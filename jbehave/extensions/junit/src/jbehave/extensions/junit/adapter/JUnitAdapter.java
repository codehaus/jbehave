/*
 * Created on 15-Jan-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.extensions.junit.adapter;

import jbehave.framework.CriteriaVerifier;
import jbehave.framework.CriteriaVerification;
import jbehave.framework.SpecVerifier;
import jbehave.framework.exception.VerificationException;
import jbehave.listeners.NullListener;
import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestCase;

import java.lang.reflect.Method;

/**
 * Allows behaviours to be run in any JUnit runner
 *
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 * @author <a href="mailto:joe@jbehave.org">Joe Walnes</a>
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 */
public class JUnitAdapter extends TestCase {

	private final class JUnitListener extends NullListener {
		private final TestResult result;
		private final Test adapter;
        private Class currentSpec;

		private JUnitListener(TestResult result, Test adapter) {
			this.result = result;
			this.adapter = adapter;
		}

		public void specVerificationStarting(Class spec) {
			currentSpec = spec;
		}

		public void criteriaVerificationStarting(CriteriaVerifier verifier, Object spec) {
			setCurrentCriteria(currentSpec.getName());

			result.startTest(adapter);
		}

		public CriteriaVerification criteriaVerificationEnding(CriteriaVerification verification, Object specInstance) {
			if (verification.failed()) {
				VerificationException e = (VerificationException) verification.getTargetException();
				result.addError(adapter, e);
			} else if (verification.threwException()) {
				result.addError(adapter, verification.getTargetException());
			}
			result.endTest(adapter);
			return verification;
		}

	}

	private final Class behaviours;
	private String currentBehaviourName;

	public JUnitAdapter(Class behaviours) {
		this(behaviours, 1);
	}

	public JUnitAdapter(Class behaviours, int depth) {
		this.behaviours = behaviours;
		currentBehaviourName = behaviours.getName();
	}

	public String getName() {
		return currentBehaviourName;
	}

	public int countTestCases() {
		Method [] methods = behaviours.getMethods();
		int count = 0;
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method.getName().startsWith("should")) count ++;
		}
		return count;
	}

	private void setCurrentCriteria(String behaviourClassName) {
		currentBehaviourName = behaviourClassName;
	}

	public void run(final TestResult result) {
		SpecVerifier currentRunner = new SpecVerifier(behaviours);

		currentRunner.verifySpec(new JUnitListener(result, this));
	}

	public String toString() {
		return currentBehaviourName;
	}
}
