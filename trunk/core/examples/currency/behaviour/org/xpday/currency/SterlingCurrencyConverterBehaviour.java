/*
 * Created on 15-Nov-2004
 * 
 * (c) Damian Guy
 * 
 * See license.txt for licence details
 */
package org.xpday.currency;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.core.minimock.Mock;

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
		Verify.equal(18.50, convertedAmount, 0);
		verifyMocks();
	}

	public void shouldConvertToEUR() throws Exception {
		// expect
		exchangeRateServiceMock.stubs("retrieveRate").
			will(returnValue(new ExchangeRate(1.52, 0.66)));
		// execute
		double convertedAmount = sterlingConverter.convertFromSterling(10.0, Currency.EUR);
		double otherAmount = sterlingConverter.convertFromSterling(5, Currency.EUR);

		// verify
		Verify.equal(15.2, convertedAmount, 0);
		Verify.equal(7.6, otherAmount, 0);
		verifyMocks();
	}

	public void shouldNotConvertFromNegativeSterlingAmounts() throws Exception {
		// expects
		exchangeRateServiceMock.stubs("retrieveRate").will(returnValue(new ExchangeRate(1.85, 0.54)));
		try {
			sterlingConverter.convertFromSterling(-1, Currency.USD);
			Verify.impossible("cannot convert negative values");
		} catch (InvalidAmountException iae) {
			verifyMocks();
			// success!
		}
	}

	public void shouldConvertFromEUR() throws Exception {
        // expects
		exchangeRateServiceMock.expects("retrieveRate").
				with(eq(Currency.EUR)).
				will(returnValue(new ExchangeRate(1.52,0.66)));
		// execute
	    double pounds1 = sterlingConverter.convertToSterling(10.0, Currency.EUR);

		// verify
		Verify.equal(6.6, pounds1, 0.001);
		verifyMocks();
	}

	public void shouldNotConvertFromNegativeAmounts() throws Exception {
		//expects
		exchangeRateServiceMock.stubs("retrieveRate").will(returnValue(new ExchangeRate(1.85, 0.54)));
		try {
			sterlingConverter.convertToSterling(-3, Currency.EUR);
			Verify.impossible("cannot convert negative values");
		} catch (InvalidAmountException iae) {
			verifyMocks();
			// success!
		}
	}

	public void shouldConvertFromUSD() throws Exception {
		// expects
		exchangeRateServiceMock.stubs("retrieveRate").will(returnValue(new ExchangeRate(1.85, 0.54)));
		// execute
	    double pounds1 = sterlingConverter.convertToSterling(10.0, Currency.USD);
	    double pounds2 = sterlingConverter.convertToSterling(5, Currency.USD);

		// verify
		Verify.equal(5.4, pounds1, 0);
		Verify.equal(2.7, pounds2, 0);
		verifyMocks();
	}

}
