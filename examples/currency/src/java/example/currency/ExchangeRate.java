/*
 * Created on 17-Nov-2004
 *
 * (c) Damian Guy
 *
 * See license.txt for licence details
 */
package example.currency;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 17-Nov-2004
 */
public class ExchangeRate {
	public final double fromRate;
	public final double toRate;

	public ExchangeRate(double fromRate, double toRate) {
		this.fromRate = fromRate;
		this.toRate = toRate;
	}
}
