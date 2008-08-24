package org.jbehave.examples.trader.scenarios;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import org.jbehave.examples.trader.model.Stock;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.steps.Steps;

public class StockSteps extends Steps {
    
    private double threshold;
    private Stock stock;
    
    public StockSteps(double threshold){
        this.threshold = threshold;
    }
    
    @Given("a stock of price $price")
    public void aStockOfPrice(double price) {
        stock = new Stock(price, threshold);
    }
    
    @When("the stock is traded at $price")
    public void theStockIsTradedAt(double price) {
        stock.tradeAt(price);
    }
    
    @Then("the alert status should be $status")
    public void theAlertStatusShouldBe(String status) {
        ensureThat(stock.getStatus().name(), equalTo(status));
    }

}
