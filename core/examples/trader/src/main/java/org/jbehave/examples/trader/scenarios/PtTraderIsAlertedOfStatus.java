package org.jbehave.examples.trader.scenarios;

import org.jbehave.examples.trader.PtTraderScenario;


public class PtTraderIsAlertedOfStatus extends PtTraderScenario {

    public PtTraderIsAlertedOfStatus() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public PtTraderIsAlertedOfStatus(final ClassLoader classLoader) {
    	super(classLoader);
    }

}
