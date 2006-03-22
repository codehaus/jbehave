package com.thoughtworks.jbehave.core.example.currency;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 16-Nov-2004
 */
public interface ExchangeRateService {
	ExchangeRate retrieveRate(Currency currency);
}
