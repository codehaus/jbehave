/*
 * Created on 17-Nov-2004
 *
 * (c) Damian Guy
 *
 * See license.txt for licence details
 */
package org.xpday.currency;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 17-Nov-2004
 */
public class CurrencyConverterController implements CurrencyConverterViewEventHandler {
	private CurrencyConverterView currencyConverterView;
	private CurrencyConverter currencyConverter;

	public CurrencyConverterController(CurrencyConverterView currencyConverterView, CurrencyConverter currencyConverter) {
		this.currencyConverterView = currencyConverterView;
		this.currencyConverter = currencyConverter;
	}

	public void launch() {
    	currencyConverterView.registerEventHandler(this);
		currencyConverterView.show();
	}

	public void handleConvertFromSterling(Currency currency, String amount) {
		try {
			double result = currencyConverter.convertFromSterling(parseNumber(amount), currency);
			currencyConverterView.conversionResult(result);
		} catch (InvalidAmountException e) {
			currencyConverterView.displayError(e);
		}
	}

	public void handleConvertToSterling(Currency currency, String amount) {
		try {
			double result = currencyConverter.convertToSterling(parseNumber(amount), currency);
			currencyConverterView.conversionResult(result);
		} catch (InvalidAmountException e) {
            currencyConverterView.displayError(e);
		}

	}

	private double parseNumber(String amount) throws InvalidAmountException {
		try {
			return new Double(amount).doubleValue();
		} catch (NumberFormatException e) {
			throw new InvalidAmountException("Enter a number!");
		}
	}

	public void handleClose() {
		currencyConverterView.dispose();
	}
}
