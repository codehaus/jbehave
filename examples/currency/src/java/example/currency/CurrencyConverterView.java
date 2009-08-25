package example.currency;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 17-Nov-2004
 */
public interface CurrencyConverterView {
	void registerEventHandler(CurrencyConverterViewEventHandler eventHandler);

	void show();

	void conversionResult(double result);

	void displayError(InvalidAmountException e);

	void dispose();
}
