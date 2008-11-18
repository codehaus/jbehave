package org.jbehave.examples.trader.scenarios;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import java.util.List;

import org.jbehave.container.Container;
import org.jbehave.examples.trader.container.TraderContainer;
import org.jbehave.examples.trader.model.Stock;
import org.jbehave.examples.trader.model.Trader;
import org.jbehave.examples.trader.persistence.TraderPersister;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.steps.Steps;
import org.jbehave.scenario.steps.StepsConfiguration;

public class TraderContainerSteps extends Steps {

    private double threshold;
    private Stock stock;
    private Trader trader;
    private Container container;

    public TraderContainerSteps(double threshold, ClassLoader classLoader) {
        super(new StepsConfiguration());
        this.threshold = threshold;
        this.container = new TraderContainer(classLoader);
    }

    @Given("a trader of name $name")
    public void aTrader(String name) {
        this.trader = container.getComponent(TraderPersister.class).retrieveTrader(name);
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
    public void theTraderSellsAllStocks() {
        trader.sellAllStocks();
        ensureThat(trader.getStocks().size(), equalTo(0));
    }

}
