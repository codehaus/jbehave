/*
 * Created on 27-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.extensions.jmock.listener;

import java.lang.reflect.Field;

import jbehave.framework.CriteriaVerification;
import jbehave.framework.exception.VerificationException;
import jbehave.listeners.NullListener;

import org.jmock.Mock;
import junit.framework.AssertionFailedError;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class JMockListener extends NullListener {

	private Class specBeingVerified = null;

	public void specVerificationStarting(Class spec) {
		specBeingVerified = spec;
	}

	public CriteriaVerification criteriaVerificationEnding(CriteriaVerification behaviourResult, Object specInstance) {

        // iterate looking for fields of type Mock
        Field[] fields = specInstance.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (Mock.class.equals(field.getType())) {
				try {
                	verifyMock(field, specInstance);
				} catch (AssertionFailedError error) {
					return new CriteriaVerification(behaviourResult.getName(),
							behaviourResult.getSpecName(),
							new VerificationException(error.getMessage()));
				}
            }
		}
		return behaviourResult; 
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
