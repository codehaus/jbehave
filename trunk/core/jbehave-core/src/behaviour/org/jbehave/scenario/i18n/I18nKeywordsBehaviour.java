package org.jbehave.scenario.i18n;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import org.jbehave.scenario.definition.KeyWords;
import org.junit.Test;

public class I18nKeywordsBehaviour {

	private StringEncoder encoder = new StringEncoder("UTF-8", "UTF-8");

	@Test
	public void keywordsInEnglishAsDefault() throws IOException {
		ensureKeywordsAreLocalisedFor(null);
	}

	@Test
	public void keywordsInItalian() throws IOException {
		ensureKeywordsAreLocalisedFor(new Locale("it"));
	}

	private void ensureKeywordsAreLocalisedFor(Locale locale)
			throws IOException {
		Properties properties = bundleFor(locale);
		KeyWords keywords = keyWordsFor(locale);
		ensureKeywordIs(properties, "Scenario", keywords.scenario());
		ensureKeywordIs(properties, "GivenScenarios", keywords.givenScenarios());
		ensureKeywordIs(properties, "ExamplesTable", keywords.examplesTable());
		ensureKeywordIs(properties, "ExamplesTableRow", keywords.examplesTableRow());
		ensureKeywordIs(properties, "Given", keywords.given());
		ensureKeywordIs(properties, "When", keywords.when());
		ensureKeywordIs(properties, "Then", keywords.then());
		ensureKeywordIs(properties, "And", keywords.and());
		ensureKeywordIs(properties, "Pending", keywords.pending());
		ensureKeywordIs(properties, "NotPerformed", keywords.notPerformed());
		ensureKeywordIs(properties, "Failed", keywords.failed());
	}

	private I18nKeyWords keyWordsFor(Locale locale) {
		return (locale == null ? new I18nKeyWords() : new I18nKeyWords(locale));
	}

	private Properties bundleFor(Locale locale) throws IOException {
		Properties expected = new Properties();
		String bundle = "org/jbehave/scenario/i18n/keywords_"
				+ (locale == null ? "en" : locale.getLanguage())
				+ ".properties";
		expected.load(Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(bundle));
		return expected;
	}

	private void ensureKeywordIs(Properties properties, String key, String value) {
		assertEquals(encoder.encode(properties.getProperty(key)), value);
	}

}
