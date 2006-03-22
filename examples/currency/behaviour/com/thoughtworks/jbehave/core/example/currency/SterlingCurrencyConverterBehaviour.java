/*
 * Created on 15-Nov-2004
 * 
 * (c) Damian Guy
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.core.example.currency;

import jbehave.core.Block;
import jbehave.core.Ensure;
import jbehave.core.minimock.Mock;
import jbehave.core.minimock.UsingMiniMock;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 15-Nov-2004
 */
public class SterlingCurrencyConverterBehaviour extends UsingMiniMock {
	private CurrencyConverter sterlingConverter;
	private Mock exchangeRateServiceMock;

	public void setUp() {
		exchangeRateServiceMock = mock(ExchangeRateService.class);
		sterlingConverter = new SterlingCurrencyConverter((ExchangeRateService)exchangeRateServiceMock);
	}

	public void shouldConvertToUSD() throws Exception {
		// expect
		exchangeRateServiceMock.expects("retrieveRate").
				with(eq(Currency.USD)).
				will(returnValue(new ExchangeRate(1.85, 0.54)));

		// execute
		double convertedAmount = sterlingConverter.convertFromSterling(10.0, Currency.USD);

		// verify
		Ensure.that(18.50, eq(convertedAmount, 0));
	}

	public void shouldConvertToEUR() throws Exception {
		// expect
		exchangeRateServiceMock.stubs("retrieveRate").
			will(returnValue(new ExchangeRate(1.52, 0.66)));
		// execute
		double convertedAmount = sterlingConverter.convertFromSterling(10.0, Currency.EUR);
		double otherAmount = sterlingConverter.convertFromSterling(5, Currency.EUR);

		// verify
		Ensure.that(15.2, eq(convertedAmount, 0));
		Ensure.that(7.6, eq(otherAmount, 0));
	}

	public void shouldNotConvertFromNegativeSterlingAmounts() throws Exception {
		// expects
		exchangeRateServiceMock.stubs("retrieveRate").will(returnValue(new ExchangeRate(1.85, 0.54)));
		Ensure.throwsException(InvalidAmountException.class, new Block() {
            public void execute() throws Exception {
                sterlingConverter.convertFromSterling(-1, Currency.USD);
            }
        });
	}

	public void shouldConvertFromEUR() throws Exception {
        // expects
		exchangeRateServiceMock.expects("retrieveRate").
				with(eq(Currency.EUR)).
				will(returnValue(new ExchangeRate(1.52,0.66)));
		// execute
	    double pounds1 = sterlingConverter.convertToSterling(10.0, Currency.EUR);

		// verify
		Ensure.that(6.6, eq(pounds1, 0.001));
	}

	public void shouldNotConvertFromNegativeAmounts() throws Exception {
		//expects
		exchangeRateServiceMock.stubs("retrieveRate").will(returnValue(new ExchangeRate(1.85, 0.54)));
        Ensure.throwsException(InvalidAmountException.class, new Block() {
            public void execute() throws Exception {
                sterlingConverter.convertToSterling(-3, Currency.EUR);
            }
        });
	}

	public void shouldConvertFromUSD() throws Exception {
		// expects
		exchangeRateServiceMock.stubs("retrieveRate").will(returnValue(new ExchangeRate(1.85, 0.54)));
		// execute
	    double pounds1 = sterlingConverter.convertToSterling(10.0, Currency.USD);
	    double pounds2 = sterlingConverter.convertToSterling(5, Currency.USD);

		// verify
		Ensure.that(5.4, eq(pounds1, 0));
		Ensure.that(2.7, eq(pounds2, 0));
	}

}
