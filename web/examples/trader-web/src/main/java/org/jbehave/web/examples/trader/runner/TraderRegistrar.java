package org.jbehave.web.examples.trader.runner;

import java.io.File;

import org.codehaus.waffle.registrar.Registrar;
import org.jbehave.web.examples.trader.scenarios.StockExchangeSteps;
import org.jbehave.web.examples.trader.scenarios.TraderSteps;
import org.jbehave.web.examples.trader.scenarios.TradingService;
import org.jbehave.web.io.PrintStreamFileMonitor;
import org.jbehave.web.runner.waffle.JBehaveRegistrar;

public class TraderRegistrar extends JBehaveRegistrar {

    public TraderRegistrar(Registrar delegate) {
        super(delegate);
    }

    @Override
    protected void registerSteps() {
        registerInstance(new TraderSteps(new TradingService()));
        registerInstance(new StockExchangeSteps());
    }

    @Override
    protected void registerFileMonitor() {
        register(PrintStreamFileMonitor.class);
    }

    @Override
    protected File uploadDirectory() {
        // Can be overridden to return, e.g. new File("/tmp/upload");
        return super.uploadDirectory();
    }
}
