package com.thoughtworks.jbehave.core.example.currency;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 17-Nov-2004
 */
public interface CurrencyConverter {
	double convertFromSterling(double amount, Currency toCurrency) throws InvalidAmountException;

	double convertToSterling(double amount, Currency fromCurrency) throws InvalidAmountException;

}
