package org.jbehave.examples.trader;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
import org.jbehave.scenario.definition.ExamplesTable;
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
    private List<Trader> traders = new ArrayList<Trader>();
    private List<Trader> searchedTraders;

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

    @Given("the traders: %tradersTable")
    public void theTraders(ExamplesTable tradersTable) {
        traders.clear();
        traders.addAll(toTraders(tradersTable));
    }

    @When("a wildcard search \"%regex\" is executed")
    public void aWildcardSearchIsExecuted(String regex) {
        searchedTraders = new ArrayList<Trader>();
        for (Trader trader : traders) {
            if ( trader.getName().matches(regex) ){
                searchedTraders.add(trader);
            }
        }
    }
    
    @Then("the traders returned are: %tradersTable")
    public void theTradersReturnedAre(ExamplesTable tradersTable) {
        List<Trader> expected = toTraders(tradersTable);
        assertEquals(expected.toString(), searchedTraders.toString());
    }

    private List<Trader> toTraders(ExamplesTable table) {
        List<Trader> traders = new ArrayList<Trader>();
        List<Map<String, String>> rows = table.getRows();
        for (Map<String, String> row : rows) {
            String name = row.get("name");
            String rank = row.get("rank");
            traders.add(new Trader(name, rank));
        }
        Collections.sort(traders);
        return traders;
    }
    
    @Given("a stock of symbol %symbol and a threshold of %threshold")
    @Alias("a stock of <symbol> and a <threshold>") // alias used with examples table
    public void aStock(@Named("symbol") String symbol, @Named("threshold") double threshold) {
        stock = new Stock(symbol, threshold);
    }

    @When("the stock is traded at price %price")
    @Aliases(values={"the stock is sold at price %price", "the stock is exchanged at price %price",
            "the stock is traded with <price>"}) // multiple aliases, one used with examples table
    public void theStockIsTraded(@Named("price") double price) {
        stock.tradeAt(price);
    }

    @Given("the alert status is %status") // shows that matching pattern need only be unique for step type
    public void theAlertStatusIsReset(@Named("status") String status) {
    	if ( AlertStatus.OFF.name().startsWith(status) && stock != null ){
        	stock.resetAlert();    		
    	}
    }

    @Then("the alert status is %status")
    @Alias("the trader is alerted with <status>") // alias used with examples table
    public void theAlertStatusIs(@Named("status") String status) {
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
