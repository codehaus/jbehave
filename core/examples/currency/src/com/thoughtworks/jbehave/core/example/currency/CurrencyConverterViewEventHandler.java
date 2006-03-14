package jbehave.core.example.currency;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 17-Nov-2004
 */
public interface CurrencyConverterViewEventHandler {
	void handleConvertFromSterling(Currency currency, String amount);

	void handleConvertToSterling(Currency currency, String amount);

	void handleClose();
}
