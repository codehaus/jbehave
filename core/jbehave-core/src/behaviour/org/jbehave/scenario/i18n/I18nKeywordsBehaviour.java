package org.jbehave.scenario.i18n;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.jbehave.scenario.definition.KeyWords;
import org.junit.Test;


public class I18nKeywordsBehaviour {

	@Test
	public void keywordsInDefaultLocale(){
		KeyWords keywords = new I18nKeyWords();
		assertEquals("Scenario:", keywords.scenario());
		assertEquals("GivenScenarios:", keywords.givenScenarios());
		assertEquals("Examples:", keywords.table());		
		assertEquals("Given", keywords.given());
		assertEquals("When", keywords.when());
		assertEquals("Then", keywords.then());
		assertEquals("And", keywords.and());
	}

	@Test
	public void keywordsInItalian(){
		KeyWords keywords = new I18nKeyWords(Locale.ITALIAN);
		assertEquals("Scenario:", keywords.scenario());
		assertEquals("DatiScenari:", keywords.givenScenarios());
		assertEquals("Esempi:", keywords.table());		
		assertEquals("Dato", keywords.given());
		assertEquals("Quando", keywords.when());
		assertEquals("Allora", keywords.then());
		assertEquals("E", keywords.and());
	}

	@Test
	public void keywordsInSpanish(){
		KeyWords keywords = new I18nKeyWords(new Locale("es", "ES", ""));
		assertEquals("Escenario:", keywords.scenario());
		assertEquals("DadosEscenarios:", keywords.givenScenarios());
		assertEquals("Ejemplos:", keywords.table());		
		assertEquals("Dado", keywords.given());
		assertEquals("Cuando", keywords.when());
		assertEquals("Entonces", keywords.then());
		assertEquals("Y", keywords.and());
	}
	
}
