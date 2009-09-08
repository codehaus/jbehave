package org.jbehave.scenario.i18n;

import static java.util.ResourceBundle.getBundle;

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
        this(DEFAULT_BUNDLE_NAME, Locale.ENGLISH);
    }

    public I18nKeyWords(Locale locale) {
    	this(DEFAULT_BUNDLE_NAME, locale);
    }

    public I18nKeyWords(String bundleName, Locale locale) {
    	super(keywords(bundleName, locale));
    }

	private static Map<String, String> keywords(String bundleName,
			Locale locale) {
		ResourceBundle bundle = lookupBunde(bundleName, locale);
		Map<String, String> keywords = new HashMap<String, String>();
		for ( String key : KEYWORDS ) {
			keywords.put(key, keyword(bundle, key));			
		}
		return keywords;
	}

	private static String keyword(ResourceBundle bundle, String name) {
		return bundle.getString(name);
	}

    private static ResourceBundle lookupBunde(String bundleName, Locale locale) {
        try {
            return getBundle(bundleName.trim(), locale);
        } catch (MissingResourceException e) {
            return EMPTY_BUNDLE;
        }
    }

}
