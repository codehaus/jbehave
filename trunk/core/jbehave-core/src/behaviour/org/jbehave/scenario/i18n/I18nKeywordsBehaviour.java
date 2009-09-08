package org.jbehave.scenario.i18n;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.jbehave.scenario.definition.KeyWords;
import org.junit.Test;


public class I18nKeywordsBehaviour {

	@Test
	public void keywordsInEnglishAsDefault(){
		KeyWords keywords = new I18nKeyWords();
		assertEquals("Scenario:", keywords.scenario());
		assertEquals("GivenScenarios:", keywords.givenScenarios());
		assertEquals("Examples:", keywords.examplesTable());		
		assertEquals("Given", keywords.given());
		assertEquals("When", keywords.when());
		assertEquals("Then", keywords.then());
		assertEquals("And", keywords.and());
	}

	@Test
	public void keywordsInItalian(){
		KeyWords keywords = new I18nKeyWords(new Locale("it"));
		assertEquals("Scenario:", keywords.scenario());
		assertEquals("Dati gli scenari:", keywords.givenScenarios());
		assertEquals("Esempi:", keywords.examplesTable());		
		assertEquals("Dato che", keywords.given());
		assertEquals("Quando", keywords.when());
		assertEquals("Allora", keywords.then());
		assertEquals("E", keywords.and());
	}

	@Test
	public void keywordsInSpanish(){
		KeyWords keywords = new I18nKeyWords(new Locale("es"));
		assertEquals("Escenario:", keywords.scenario());
		assertEquals("Dados los escenarios:", keywords.givenScenarios());
		assertEquals("Ejemplos:", keywords.examplesTable());		
		assertEquals("Dado que", keywords.given());
		assertEquals("Cuando", keywords.when());
		assertEquals("Entonces", keywords.then());
		assertEquals("Y", keywords.and());
	}	

	@Test
	public void keywordsInPortuguese(){
		KeyWords keywords = new I18nKeyWords(new Locale("pt"));
		assertEquals("Cen‡rio:", keywords.scenario());
		assertEquals("Dados os cen‡rios:", keywords.givenScenarios());
		assertEquals("Exemplos:", keywords.examplesTable());		
		assertEquals("Dado que", keywords.given());
		assertEquals("Quando", keywords.when());
		assertEquals("Ent‹o", keywords.then());
		assertEquals("E", keywords.and());
	}
	
}
