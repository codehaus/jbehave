package org.jbehave.scenario.i18n;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import org.jbehave.scenario.definition.KeyWords;
import org.jbehave.scenario.i18n.I18nKeyWords.KeywordNotFoundExcepion;
import org.jbehave.scenario.i18n.I18nKeyWords.ResourceBundleNotFoundExcepion;
import org.junit.Test;

public class I18nKeywordsBehaviour {

	private StringEncoder encoder = new StringEncoder("UTF-8", "UTF-8");

	@Test
	public void shouldAllowKeywordsInEnglishAsDefault() throws IOException {
		ensureKeywordsAreLocalisedFor(null, null);
	}

	@Test
	public void shouldAllowKeywordsInADifferentLocale() throws IOException {
		ensureKeywordsAreLocalisedFor(new Locale("it"), null);
	}

	@Test(expected = ResourceBundleNotFoundExcepion.class)
	public void shouldFailIfResourceBundleIsNotFound() throws IOException {
		ensureKeywordsAreLocalisedFor(new Locale("en"), "unknown");
	}

	@Test(expected = KeywordNotFoundExcepion.class)
	public void shouldFailIfKeywordIsNotFound() throws IOException {
		ensureKeywordsAreLocalisedFor(new Locale("es"), null);
	}

	private void ensureKeywordsAreLocalisedFor(Locale locale, String bundleName)
			throws IOException {
		KeyWords keywords = keyWordsFor(locale, bundleName);
		Properties properties = bundleFor(locale);
		ensureKeywordIs(properties, "Scenario", keywords.scenario());
		ensureKeywordIs(properties, "GivenScenarios", keywords.givenScenarios());
		ensureKeywordIs(properties, "ExamplesTable", keywords.examplesTable());
		ensureKeywordIs(properties, "ExamplesTableRow", keywords
				.examplesTableRow());
		ensureKeywordIs(properties, "Given", keywords.given());
		ensureKeywordIs(properties, "When", keywords.when());
		ensureKeywordIs(properties, "Then", keywords.then());
		ensureKeywordIs(properties, "And", keywords.and());
		ensureKeywordIs(properties, "Pending", keywords.pending());
		ensureKeywordIs(properties, "NotPerformed", keywords.notPerformed());
		ensureKeywordIs(properties, "Failed", keywords.failed());
	}

	private I18nKeyWords keyWordsFor(Locale locale, String bundleName) {
		if ( bundleName == null ){
			return (locale == null ? new I18nKeyWords() : new I18nKeyWords(locale));
		} else {
			return new I18nKeyWords(locale, new StringEncoder(), bundleName, Thread.currentThread().getContextClassLoader());
		}
	}

	private Properties bundleFor(Locale locale) throws IOException {		
		Properties expected = new Properties();
		String bundle = "org/jbehave/scenario/i18n/keywords_"
				+ (locale == null ? "en" : locale.getLanguage())
				+ ".properties";
		InputStream stream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(bundle);
		if (stream != null) {
			expected.load(stream);
		}
		return expected;
	}
	
	private void ensureKeywordIs(Properties properties, String key, String value) {
		assertEquals(encoder.encode(properties.getProperty(key, value)), value);
	}

}
