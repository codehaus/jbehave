package org.jbehave.examples.trader.scenarios;

import org.jbehave.examples.trader.TraderScenario;


public class TraderIsAlertedOfStatus extends TraderScenario {

    public TraderIsAlertedOfStatus() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public TraderIsAlertedOfStatus(final ClassLoader classLoader) {
    	super(classLoader);
    }

}
