/*
 * Created on 27-Jun-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.extensions.jmock.listener;

import java.lang.reflect.Field;

import jbehave.framework.BehaviourResult;
import jbehave.runner.listener.ListenerSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmock.Mock;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class JMockListener extends ListenerSupport {
    private static final Log log = LogFactory.getLog(JMockListener.class);

	public void behaviourEnded(BehaviourResult behaviourResult) {
        log.trace("behaviourEnded");
        Object executedInstance = behaviourResult.getExecutedInstance();
        
        // iterate looking for fields of type Mock
        Field[] fields = executedInstance.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            log.trace("Field = " + field + ", " + " type = " + field.getType(), null);
            if (Mock.class.equals(field.getType())) {
                try {
                    log.trace("Testing field " + field);
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
	}
}
