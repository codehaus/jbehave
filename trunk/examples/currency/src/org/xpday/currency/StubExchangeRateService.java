/*
 * Created on 17-Nov-2004
 *
 * (c) Damian Guy
 *
 * See license.txt for licence details
 */
package org.xpday.currency;

import java.util.Map;
import java.util.HashMap;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 17-Nov-2004
 */
public class StubExchangeRateService implements ExchangeRateService {

	Map exchangeRates = new HashMap();

	public StubExchangeRateService() {
		exchangeRates.put(Currency.USD, new ExchangeRate(1.85, 0.54));
		exchangeRates.put(Currency.EUR, new ExchangeRate(1.52, 0.66));

	}
	
	public ExchangeRate retrieveRate(Currency currency) {
		return (ExchangeRate) exchangeRates.get(currency);
	}
}
