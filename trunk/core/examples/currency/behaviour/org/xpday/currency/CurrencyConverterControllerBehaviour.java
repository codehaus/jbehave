/*
 * Created on 17-Nov-2004
 *
 * (c) Damian Guy
 *
 * See license.txt for licence details
 */
package org.xpday.currency;

import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 17-Nov-2004
 */
public class CurrencyConverterControllerBehaviour extends UsingMiniMock {
	private Mock viewMock;
	private CurrencyConverterController controller;
	private Mock currencyConverterMock;

	public void setUp() {
		viewMock = mock(CurrencyConverterView.class);
		currencyConverterMock = mock(CurrencyConverter.class);
		controller = new CurrencyConverterController((CurrencyConverterView)viewMock, (CurrencyConverter) currencyConverterMock);

	}

	public void shouldRegisterAsHandlerAndShowViewOnLaunch() throws Exception {
		// setup

		//expects
		viewMock.expects("registerEventHandler").with(eq(controller));
		viewMock.expects("show").withNoArguments();

		// execute
		controller.launch();

		// verify
		verifyMocks();
	}


	public void shouldConvertFromSterlingToOtherCurrency() throws Exception {
		// setup

		// expects
		currencyConverterMock.expects("convertFromSterling").willReturn(3.4);
		viewMock.expects("conversionResult").with(eq(3.4));

		// execute
		controller.handleConvertFromSterling(Currency.USD, "1");

		// verify
		verifyMocks();
	}

	public void shouldConvertFromOtherCurrencyToSterling() throws Exception {
		// setup

		// expects
		currencyConverterMock.expects("convertToSterling").willReturn(2);
		viewMock.expects("conversionResult").with(eq(2));

		// execute
		controller.handleConvertToSterling(Currency.EUR, "3");
		// verify
		verifyMocks();
	}

	public void shouldRaiseErrorsToUserOnNegativeSterlingAmounts() throws Exception {
		// setup
		InvalidAmountException invalidAmountException = new InvalidAmountException("non-negative");

		// expects
		currencyConverterMock.expects("convertFromSterling").willThrow(invalidAmountException);
		viewMock.expects("displayError").with(eq(invalidAmountException));

		// execute

		controller.handleConvertFromSterling(Currency.EUR, "-1");

		// verify
		verifyMocks();
	}

	public void shouldRaiseErrorsOnNegativeOtherCurrencyAmounts() throws Exception {
		// setup
		InvalidAmountException invalidAmountException = new InvalidAmountException("non-negative");

		// expects
		currencyConverterMock.expects("convertToSterling").willThrow(invalidAmountException);
		viewMock.expects("displayError").with(eq(invalidAmountException));

		// execute

		controller.handleConvertToSterling(Currency.USD, "-1");

		// verify
		verifyMocks();
	}

	public void shouldRaiseErrorOnNonNumericValuesWhenConvertingToSterling() throws Exception {
		// setup

		// expects
		viewMock.expects("displayError");

		// execute
		controller.handleConvertToSterling(Currency.USD, "b");

		// verify
		verifyMocks();
	}
	public void shouldRaiseErrorOnNonNumericValuesWhenConvertingFromSterling() throws Exception {
		// setup

		// expects
		viewMock.expects("displayError");

		// execute
		controller.handleConvertFromSterling(Currency.EUR, "e");

		// verify
		verifyMocks();
	}

	public void shouldDisposeOfViewOnClose() throws Exception {
		// setup

		// expects
		viewMock.expects("dispose");

		// execute
		controller.handleClose();

		// verify
		verifyMocks();
	}
}
