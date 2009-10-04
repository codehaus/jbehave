package org.jbehave.scenario.i18n;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.jbehave.scenario.definition.KeyWords;

public class I18nKeyWords extends KeyWords {

	private static final String DEFAULT_BUNDLE_NAME = "org/jbehave/scenario/i18n/keywords";

	public I18nKeyWords() {
        this(Locale.ENGLISH, new StringEncoder(), DEFAULT_BUNDLE_NAME,  Thread.currentThread().getContextClassLoader());
    }

    public I18nKeyWords(Locale locale) {
    	this(locale, new StringEncoder(),  DEFAULT_BUNDLE_NAME, Thread.currentThread().getContextClassLoader());
    }

    public I18nKeyWords(Locale locale,  StringEncoder encoder) {
    	this(locale, encoder, DEFAULT_BUNDLE_NAME, Thread.currentThread().getContextClassLoader());
    }

    public I18nKeyWords(Locale locale, StringEncoder encoder, String bundleName, ClassLoader classLoader) {
    	super(keywords(bundleName, locale, encoder, classLoader), encoder);
    }

	private static Map<String, String> keywords(String bundleName,
			Locale locale, StringEncoder encoder, ClassLoader classLoader) {
		ResourceBundle bundle = lookupBunde(bundleName, locale, classLoader);
		Map<String, String> keywords = new HashMap<String, String>();
		for ( String key : KEYWORDS ) {
			keywords.put(key, keyword(bundle, key, encoder));			
		}
		return keywords;
	}

	private static String keyword(ResourceBundle bundle, String name, StringEncoder encoder) {
		return encoder.encode(bundle.getString(name));
	}

    private static ResourceBundle lookupBunde(String bundleName, Locale locale, ClassLoader classLoader) {
        try {
            return ResourceBundle.getBundle(bundleName.trim(), locale, classLoader);
        } catch (MissingResourceException e) {
            throw new ResourceBundleNotFoundExcepion(bundleName, locale, classLoader, e);
        }
    }
    
	@SuppressWarnings("serial")
	public static final class ResourceBundleNotFoundExcepion extends RuntimeException {

		public ResourceBundleNotFoundExcepion(String bundleName,
				Locale locale, ClassLoader classLoader, MissingResourceException cause) {
			super("Resource bundle "+bundleName+" not found for locale "+locale+" in classLoader "+classLoader, cause);
		}

	}

}
