/*
 * Created on 15-Nov-2004
 *
 * (c) Damian Guy
 *
 * See license.txt for licence details
 */
package org.xpday.currency;

import com.thoughtworks.jbehave.core.behaviour.BehaviourClassContainer;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 15-Nov-2004
 */
public class AllBehaviours implements BehaviourClassContainer {
	public Class[] getBehaviourClasses() {
		return new Class[] {
			SterlingCurrencyConverterBehaviour.class,
			CurrencyConverterControllerBehaviour.class
		};
	}
}
