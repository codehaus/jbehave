/*
 * Created on 15-Nov-2004
 *
 * (c) Damian Guy
 *
 * See license.txt for licence details
 */
package example.currency;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 15-Nov-2004
 */
public class InvalidAmountException extends Exception {
	public InvalidAmountException(String message) {
		super(message);
	}
}
