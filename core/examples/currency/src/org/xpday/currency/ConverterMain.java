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
public class ConverterMain {
	public static void main(String[] args) {
		new ConverterMain().run();
	}

	private void run() {
		SwingCurrencyConverterView view  = new SwingCurrencyConverterView();
		ExchangeRateService service = new StubExchangeRateService();
		CurrencyConverter converter = new SterlingCurrencyConverter(service);
		CurrencyConverterController controller = new CurrencyConverterController(view, converter);
		controller.launch();
	}
}
