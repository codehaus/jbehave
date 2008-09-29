package org.jbehave.examples.trader.scenarios;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import java.util.List;

import org.jbehave.examples.trader.converters.TraderConverter;
import org.jbehave.examples.trader.model.Stock;
import org.jbehave.examples.trader.model.Trader;
import org.jbehave.examples.trader.persistence.TraderPersister;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.parser.PrefixCapturingPatternBuilder;
import org.jbehave.scenario.steps.ParameterConverters;
import org.jbehave.scenario.steps.SilentStepMonitor;
import org.jbehave.scenario.steps.StepMonitor;
import org.jbehave.scenario.steps.Steps;

public class StockSteps extends Steps {

    private static final StepMonitor MONITOR = new SilentStepMonitor();
    private double threshold;
    private Stock stock;
    private Trader trader;

    public StockSteps(double threshold) {
        super(new PrefixCapturingPatternBuilder(), MONITOR, new ParameterConverters(new SilentStepMonitor(), new TraderConverter(mockTradePersister())), "Given", "When", "Then", "And");
        this.threshold = threshold;
    }

    private static TraderPersister mockTradePersister() {
        return new TraderPersister(new Trader("Mauro", asList(new Stock(asList(1.0d), 10.d))));
    }

    @Given("a trader of name $trader")
    public void aTrader(Trader trader) {
        this.trader = trader;
    }

    @Given("a stock of prices $prices")
    public void aStockOfPrice(List<Double> prices) {
        stock = new Stock(prices, threshold);
    }

    @When("the stock is traded at $price")
    public void theStockIsTradedAt(double price) {
        stock.tradeAt(price);
    }

    @Then("the alert status should be $status")
    public void theAlertStatusShouldBe(String status) {
        ensureThat(stock.getStatus().name(), equalTo(status));
    }

    @Then("the trader sells all stocks")
    public void theTraderSellsAllStocks(){
        trader.sellAllStocks();
        ensureThat(trader.getStocks().size(), equalTo(0));
    }

}
