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
import jbehave.verify.listener.ListenerSupport;

import org.jmock.Mock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class JMockListener extends ListenerSupport {

	public void afterCriteriaVerificationEnds(CriteriaVerification behaviourResult) {
        Object executedInstance = behaviourResult.getSpecInstance();
        
        // iterate looking for fields of type Mock
        Field[] fields = executedInstance.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (Mock.class.equals(field.getType())) {
                verifyMock(field, executedInstance);
            }
		}
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
