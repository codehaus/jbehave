/*
 * Created on 15-Nov-2004
 *
 * (c) Damian Guy
 *
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.core.example.currency;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 15-Nov-2004
 */
public class Currency {
	public static final Currency USD = new Currency("USD");
	public static final Currency  EUR = new Currency("EUR");

	private String currencyCode;


	private Currency(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String toString() {
		return currencyCode;
	}
}
