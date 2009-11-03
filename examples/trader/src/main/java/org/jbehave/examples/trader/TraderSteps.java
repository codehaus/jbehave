package org.jbehave.examples.trader;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import org.jbehave.examples.trader.converters.TraderConverter;
import org.jbehave.examples.trader.model.Stock;
import org.jbehave.examples.trader.model.Trader;
import org.jbehave.examples.trader.model.Stock.AlertStatus;
import org.jbehave.examples.trader.persistence.TraderPersister;
import org.jbehave.scenario.annotations.Alias;
import org.jbehave.scenario.annotations.Aliases;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Named;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.parser.PrefixCapturingPatternBuilder;
import org.jbehave.scenario.steps.ParameterConverters;
import org.jbehave.scenario.steps.SilentStepMonitor;
import org.jbehave.scenario.steps.StepMonitor;
import org.jbehave.scenario.steps.Steps;
import org.jbehave.scenario.steps.StepsConfiguration;

public class TraderSteps extends Steps {

	private static final StepsConfiguration configuration = new StepsConfiguration();
    private Stock stock;
    private Trader trader;

    public TraderSteps() {
        super(configuration);
        StepMonitor monitor = new SilentStepMonitor();
		configuration.useParameterConverters(new ParameterConverters(
        		monitor, new TraderConverter(mockTradePersister())));  // define converter for custom type Trader
        configuration.usePatternBuilder(new PrefixCapturingPatternBuilder("%")); // use '%' instead of '$' to identify parameters
        configuration.useMonitor(monitor);
    }

    private TraderPersister mockTradePersister() {
        return new TraderPersister(new Trader("Mauro", asList(new Stock("STK1", 10.d))));
    }

    @Given("a trader of name %trader")
    public void aTrader(Trader trader) {
        this.trader = trader;
    }

    @Given("a stock of <symbol> and a <threshold>")
    public void aStockWithTableParams(@Named("symbol") String symbol, @Named("threshold") double threshold) {
        stock = new Stock(symbol, threshold);
    }

    @Given("a stock of symbol %symbol and a threshold of %threshold")
    public void aStockWithNamedParams(@Named("symbol") String symbol, @Named("threshold") double threshold) {
        stock = new Stock(symbol, threshold);
    }

    @When("the stock is traded with <price>")
    public void theStockIsTradedAtWithTableParam(@Named("price") double price) {
        stock.tradeAt(price);
    }

    @When("the stock is traded at price %price")
    @Aliases(values={"the stock is sold at price %price", "the stock is exchanged at price %price"}) // multiple aliases
    public void theStockIsTradedAtWithNamedParam(@Named("price") double price) {
        stock.tradeAt(price);
    }

    @Then("the trader is alerted with <status>")
    public void theAlertStatusIsWithTableParam(@Named("status") String status) {
        ensureThat(stock.getStatus().name(), equalTo(status));
    }

    @Given("the alert status is %status") // shows that matching pattern need only be unique for step type
    public void theAlertStatusIsReset(@Named("status") String status) {
    	if ( AlertStatus.OFF.name().startsWith(status) && stock != null ){
        	stock.resetAlert();    		
    	}
    }

    @Then("the alert status is %status")
    @Alias("the alert status will be %status") // single alias
    public void theAlertStatusIsWithNamedParam(@Named("status") String status) {
        ensureThat(stock.getStatus().name(), equalTo(status));
    }

    @When("the trader sells all stocks")
    public void theTraderSellsAllStocks() {
        trader.sellAllStocks();
    }

    @Then ("the trader is left with no stocks")
    public void theTraderIsLeftWithNoStocks() {
        ensureThat(trader.getStocks().size(), equalTo(0));
    }

}
