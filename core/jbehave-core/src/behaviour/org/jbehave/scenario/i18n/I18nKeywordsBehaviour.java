package org.jbehave.scenario.i18n;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

import org.jbehave.scenario.definition.KeyWords;
import org.junit.Test;

public class I18nKeywordsBehaviour {

	@Test
	public void keywordsInEnglishAsDefault() {
		ensureKeywordsAre(null, asList("Scenario:", "GivenScenarios:",
				"Examples:", "Example:", "Given", "When", "Then", "And",
				"PENDING", "NOT PERFORMED", "FAILED"));
	}

	@Test
	public void keywordsInItalian() {
		ensureKeywordsAre(new Locale("it"), asList("Scenario:",
				"Dati gli scenari:", "Esempi:", "Esempio:", "Dato che",
				"Quando", "Allora", "E", "PENDENTE", "NON ESEGUITO", "FALLITO"));
	}

	@Test
	public void keywordsInSpanish() {
		ensureKeywordsAre(new Locale("es"), asList("Escenario:",
				"Dados los escenarios:", "Ejemplos:", "Ejemplo:", "Dado que",
				"Cuando", "Entonces", "Y", "PENDIENTE", "NO REALIZADO",
				"FRACASADO"));
	}

	@Test
	public void keywordsInPortuguese() {
		ensureKeywordsAre(new Locale("pt"), asList("Cen‡rio:",
				"Dados os cen‡rios:", "Exemplos:", "Exemplo:", "Dado que",
				"Quando", "Ent‹o", "E", "PENDENTE", "NÌO EXECUTADO", "FALHADO"));
	}

	private void ensureKeywordsAre(Locale locale, List<String> expected) {
		KeyWords keywords = (locale == null ? new I18nKeyWords()
				: new I18nKeyWords(locale));
		assertUtf8Equals(expected.get(0), keywords.scenario());
		assertUtf8Equals(expected.get(1), keywords.givenScenarios());
		assertUtf8Equals(expected.get(2), keywords.examplesTable());
		assertUtf8Equals(expected.get(3), keywords.examplesTableRow());
		assertUtf8Equals(expected.get(4), keywords.given());
		assertUtf8Equals(expected.get(5), keywords.when());
		assertUtf8Equals(expected.get(6), keywords.then());
		assertUtf8Equals(expected.get(7), keywords.and());
		assertUtf8Equals(expected.get(8), keywords.pending());
		assertUtf8Equals(expected.get(9), keywords.notPerformed());
		assertUtf8Equals(expected.get(10), keywords.failed());
	}

	private void assertUtf8Equals(String expected, String actual) {
		assertEquals(utf8(expected), actual);
	}

	private String utf8(String value) {
		try {
			return new String(value.getBytes("UTF-8"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
