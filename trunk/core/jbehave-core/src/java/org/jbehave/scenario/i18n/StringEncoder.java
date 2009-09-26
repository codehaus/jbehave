/**
 * 
 */
package org.jbehave.scenario.i18n;

import java.io.UnsupportedEncodingException;

public class StringEncoder {

	public static final String ISO_8859_1 = "ISO-8859-1";
	public static final String UTF_8 = "UTF-8";
	private String encoding;
	private String decoding;
	
	public StringEncoder() {
		this(UTF_8, UTF_8);
	}
	
	public StringEncoder(String encoding, String decoding) {
		this.encoding = encoding;
		this.decoding = decoding;
	}

	public String encode(String value) {
		try {
			return new String(value.getBytes(encoding), decoding);
		} catch (UnsupportedEncodingException e) {
			throw new InvalidEncodingExcepion(value, e);
		}
	}
	
	@SuppressWarnings("serial")
	public static final class InvalidEncodingExcepion extends RuntimeException {

		public InvalidEncodingExcepion(String value,
				UnsupportedEncodingException cause) {
			super(value, cause);
		}

	}

}