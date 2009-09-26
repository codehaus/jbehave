package org.jbehave.examples.trader;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import java.util.Locale;

import org.jbehave.examples.trader.model.Stock;
import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Named;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.i18n.I18nKeyWords;
import org.jbehave.scenario.steps.Steps;
import org.jbehave.scenario.steps.StepsConfiguration;

public class PtTraderSteps extends Steps {

	private Stock stock;

	public PtTraderSteps(ClassLoader classLoader) {
		// Use Portuguese for keywords
		super(new StepsConfiguration(new I18nKeyWords(new Locale("pt"))));
	}

	@Given("há uma ação com símbolo $symbol e um limite de $threshold")
	public void aStock(@Named("symbol") String symbol,
			@Named("threshold") double threshold) {
		stock = new Stock(symbol, threshold);
	}

	@When("a ação é oferecida ao preço de $price")
	public void stockIsTraded(@Named("price") double price) {
		stock.tradeAt(price);
	}

	@Then("o estado de alerta é $status")
	public void alertStatusIs(@Named("status") String status) {
		ensureThat(stock.getStatus().name(), equalTo(status));
	}

}
