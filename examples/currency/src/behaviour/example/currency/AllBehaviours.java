/*
 * Created on 15-Nov-2004
 *
 * (c) Damian Guy
 *
 * See license.txt for licence details
 */
package example.currency;


import jbehave.core.behaviour.Behaviours;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 15-Nov-2004
 */
public class AllBehaviours implements Behaviours {
	public Class[] getBehaviours() {
		return new Class[] {
			SterlingCurrencyConverterBehaviour.class,
			CurrencyConverterControllerBehaviour.class
		};
	}
    
    public String toString() {
        return "Behaviours: " + AllBehaviours.class.getName();
    }
}
