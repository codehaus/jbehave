package org.jbehave.examples.trader.i18n.scenarios;

import org.jbehave.examples.trader.i18n.PtTraderScenario;


public class PtTraderIsAlertedOfStatus extends PtTraderScenario {

    public PtTraderIsAlertedOfStatus() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public PtTraderIsAlertedOfStatus(final ClassLoader classLoader) {
    	super(classLoader);
    }

}
