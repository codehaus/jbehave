/*
 * Created on 27-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.extensions.jmock.listener;

import java.lang.reflect.Field;

import jbehave.extensions.jmock.JMockable;
import jbehave.framework.ResponsibilityVerification;
import jbehave.framework.ResponsibilityVerifier;
import jbehave.framework.exception.VerificationException;
import jbehave.listeners.ListenerSupport;
import junit.framework.AssertionFailedError;

import org.jmock.Mock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 */
public class JMockListener extends ListenerSupport {

	public void behaviourClassVerificationStarting(Class spec) {
	}

	public void responsibilityVerificationStarting(ResponsibilityVerifier verifier, Object behaviourClassInstance) {
        JMockable.Mocks.clear(); // whatever
	}

	public ResponsibilityVerification responsibilityVerificationEnding(ResponsibilityVerification behaviourResult, Object specInstance) {
		try {
			JMockable.Mocks.verify();
		} catch (VerificationException e) {
			return createCriteriaVerification(behaviourResult, e);
		}

		return verifyFields(specInstance, behaviourResult);
	}

	private ResponsibilityVerification verifyFields(Object specInstance, ResponsibilityVerification behaviourResult) {
		Field[] fields = specInstance.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			if (Mock.class.equals(field.getType())) {
				try {
					verifyMock(field, specInstance);
				} catch (AssertionFailedError error) {
					return createCriteriaVerification(behaviourResult,new VerificationException(error.getMessage()));
				}
			}
		}
		return behaviourResult;
	}

	private ResponsibilityVerification createCriteriaVerification(ResponsibilityVerification behaviourResult, VerificationException ve) {
		return new ResponsibilityVerification(behaviourResult.getName(), behaviourResult.getBehaviourClassName(), ve);
	}

	private void verifyMock(Field field, Object executedInstance) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
        	Mock mock = (Mock) field.get(executedInstance);
			mock.verify();
        } catch (IllegalArgumentException ignored) {
        } catch (IllegalAccessException ignored) {
        }
    }
}
