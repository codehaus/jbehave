package org.jbehave.scenario.i18n;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.jbehave.scenario.definition.KeyWords;

public class I18nKeyWords extends KeyWords {

	private static final ResourceBundle EMPTY_BUNDLE = new EmptyResourceBundle();
	private static final String DEFAULT_BUNDLE_NAME = "org/jbehave/scenario/i18n/keywords";

	public I18nKeyWords() {
        this(DEFAULT_BUNDLE_NAME, Locale.ENGLISH, new StringEncoder());
    }

    public I18nKeyWords(Locale locale) {
    	this(DEFAULT_BUNDLE_NAME, locale,  new StringEncoder());
    }

    public I18nKeyWords(Locale locale,  StringEncoder encoder) {
    	this(DEFAULT_BUNDLE_NAME, locale, encoder);
    }

    public I18nKeyWords(String bundleName, Locale locale, StringEncoder encoder) {
    	super(keywords(bundleName, locale, encoder), encoder);
    }

	private static Map<String, String> keywords(String bundleName,
			Locale locale, StringEncoder encoder) {
		ResourceBundle bundle = lookupBunde(bundleName, locale);
		Map<String, String> keywords = new HashMap<String, String>();
		for ( String key : KEYWORDS ) {
			keywords.put(key, keyword(bundle, key, encoder));			
		}
		return keywords;
	}

	private static String keyword(ResourceBundle bundle, String name, StringEncoder encoder) {
		return encoder.encode(bundle.getString(name));
	}

    private static ResourceBundle lookupBunde(String bundleName, Locale locale) {
        try {
            return ResourceBundle.getBundle(bundleName.trim(), locale);
        } catch (MissingResourceException e) {
            return EMPTY_BUNDLE;
        }
    }

}
