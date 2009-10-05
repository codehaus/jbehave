package org.jbehave.examples.trader.scenarios;

import org.jbehave.examples.trader.TraderScenario;

public class TraderSellsAllStocks extends TraderScenario {

    public TraderSellsAllStocks() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public TraderSellsAllStocks(final ClassLoader classLoader) {
    	super(classLoader);
    }

}
