package org.jbehave.scenario.i18n;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class UTF8ResourceBundle {

	public static ResourceBundle getBundle(String baseName) {
		return utf8ResourceBundle(ResourceBundle.getBundle(baseName));
	}

	public static ResourceBundle getBundle(String baseName, Locale locale) {
		return utf8ResourceBundle(ResourceBundle.getBundle(baseName,
				locale));
	}

	private static ResourceBundle utf8ResourceBundle(
			ResourceBundle bundle) {
		if (!(bundle instanceof PropertyResourceBundle))
			return bundle;

		return new UTF8ResourceBundleDecorator((PropertyResourceBundle) bundle);
	}

	private static class UTF8ResourceBundleDecorator extends ResourceBundle {

		private static final String ISO_8859_1 = "ISO-8859-1";
		private static final String UTF_8 = "UTF-8";
		
		private final PropertyResourceBundle delegate;

		private UTF8ResourceBundleDecorator(PropertyResourceBundle bundle) {
			this.delegate = bundle;
		}

		public Enumeration<String> getKeys() {
			return delegate.getKeys();
		}

		protected Object handleGetObject(String key) {
			String value = (String) delegate.handleGetObject(key);
			try {
				return new String(value.getBytes(ISO_8859_1), UTF_8);
			} catch (UnsupportedEncodingException e) {
				throw new InvalidEncodingExcepion(value, e);
			}
		}
	}

	@SuppressWarnings("serial")
	private static final class InvalidEncodingExcepion extends RuntimeException {

		public InvalidEncodingExcepion(String value,
				UnsupportedEncodingException cause) {
			super(value, cause);
		}

	}
}
