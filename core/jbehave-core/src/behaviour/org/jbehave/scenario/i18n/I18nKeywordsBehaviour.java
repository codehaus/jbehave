package org.jbehave.scenario.i18n;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Properties;

import org.jbehave.scenario.definition.KeyWords;
import org.junit.Test;

public class I18nKeywordsBehaviour {

	@Test
	public void keywordsInEnglishAsDefault() throws IOException {
		ensureKeywordsAre(null);
	}

	@Test
	public void keywordsInSpanish() throws IOException {
		ensureKeywordsAre(new Locale("es"));
	}

	@Test
	public void keywordsInFrench() throws IOException {
		ensureKeywordsAre(new Locale("fr"));
	}
	
    @Test
	public void keywordsInItalian() throws IOException {
		ensureKeywordsAre(new Locale("it"));
	}

	@Test
	public void keywordsInPortuguese() throws IOException {
		ensureKeywordsAre(new Locale("pt"));
	}

	private void ensureKeywordsAre(Locale locale) throws IOException {
		Properties expected = bundleFor(locale);
		KeyWords keywords = keyWordsFor(locale);		
		assertUtf8Equals(expected.getProperty("Scenario"), keywords.scenario());
		assertUtf8Equals(expected.getProperty("GivenScenarios"), keywords.givenScenarios());
		assertUtf8Equals(expected.getProperty("ExamplesTable"), keywords.examplesTable());
		assertUtf8Equals(expected.getProperty("ExamplesTableRow"), keywords.examplesTableRow());
		assertUtf8Equals(expected.getProperty("Given"), keywords.given());
		assertUtf8Equals(expected.getProperty("When"), keywords.when());
		assertUtf8Equals(expected.getProperty("Then"), keywords.then());
		assertUtf8Equals(expected.getProperty("And"), keywords.and());
		assertUtf8Equals(expected.getProperty("Pending"), keywords.pending());
		assertUtf8Equals(expected.getProperty("NotPerformed"), keywords.notPerformed());
		assertUtf8Equals(expected.getProperty("Failed"), keywords.failed());
	}

	private I18nKeyWords keyWordsFor(Locale locale) {
		return (locale == null ? new I18nKeyWords()
				: new I18nKeyWords(locale));
	}

	private Properties bundleFor(Locale locale) throws IOException {
		Properties expected = new Properties();
		String bundle = "org/jbehave/scenario/i18n/keywords_"+( locale == null ? "en" : locale.getLanguage()) +".properties";
		expected.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(bundle));
		return expected;
	}

	private void assertUtf8Equals(String expected, String actual) {
		assertEquals(utf8(expected), actual);
	}

	private String utf8(String value) {
		try {
			return new String(value.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
