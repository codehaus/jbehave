package org.jbehave.examples.trader.scenarios;

import org.jbehave.examples.trader.ItTraderScenario;


public class ItTraderIsAlertedOfStatus extends ItTraderScenario {

    public ItTraderIsAlertedOfStatus() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public ItTraderIsAlertedOfStatus(final ClassLoader classLoader) {
    	super(classLoader);
    }

}
