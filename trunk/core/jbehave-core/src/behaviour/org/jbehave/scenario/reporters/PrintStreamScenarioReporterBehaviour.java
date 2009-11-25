package org.jbehave.scenario.reporters;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Properties;

import org.jbehave.scenario.i18n.I18nKeyWords;
import org.junit.Test;


public class PrintStreamScenarioReporterBehaviour {
    
    private static final String NL = System.getProperty("line.separator");

    @Test
    public void shouldReportEventsToPrintStream() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ScenarioReporter reporter = new PrintStreamScenarioReporter(new PrintStream(out));
        reporter.successful("Given I have a balance of $50");
        reporter.successful("When I request $20");
        reporter.successful("When I ask Liz for a loan of $100");
        reporter.pending("Then I should have a balance of $30");
        reporter.notPerformed("Then I should have $20");
        
        ensureThat(out.toString(), equalTo(
                "Given I have a balance of $50" + NL +
                "When I request $20" + NL +
                "When I ask Liz for a loan of $100" + NL +
                "Then I should have a balance of $30 (PENDING)" + NL +
                "Then I should have $20 (NOT PERFORMED)" + NL));
    }
    
    @Test
    public void shouldReportThrowablesWhenToldToDoSo() {
        IllegalAccessException exception = new IllegalAccessException("Leave my money alone!");
        ByteArrayOutputStream stackTrace = new ByteArrayOutputStream();
        exception.printStackTrace(new PrintStream(stackTrace));
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ScenarioReporter reporter = new PrintStreamScenarioReporter(new PrintStream(out), new Properties(), new I18nKeyWords(), true);
        reporter.beforeScenario("A title");
        reporter.successful("Given I have a balance of $50");
        reporter.successful("When I request $20");
        reporter.failed("When I ask Liz for a loan of $100", exception);
        reporter.pending("Then I should have a balance of $30");
        reporter.notPerformed("Then I should have $20");
        reporter.afterScenario();
        
        ensureThat(out.toString(), equalTo(
                "Scenario: A title" + NL +
                "Given I have a balance of $50" + NL +
                "When I request $20" + NL +
                "When I ask Liz for a loan of $100 (FAILED)" + NL +
                "Then I should have a balance of $30 (PENDING)" + NL +
                "Then I should have $20 (NOT PERFORMED)" + NL +
                NL + stackTrace + NL));
        
        out = new ByteArrayOutputStream();
        reporter = new PrintStreamScenarioReporter(new PrintStream(out));
        reporter.beforeScenario("A title");
        reporter.successful("Given I have a balance of $50");
        reporter.successful("When I request $20");
        reporter.failed("When I ask Liz for a loan of $100", exception);
        reporter.pending("Then I should have a balance of $30");
        reporter.notPerformed("Then I should have $20");
        reporter.afterScenario();
        
        ensureThat(!out.toString().contains(stackTrace.toString()));
    }


    @Test
    public void shouldReportEventsToPrintStreamWithCustomPatterns() {
        IllegalAccessException exception = new IllegalAccessException("Leave my money alone!");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Properties patterns = new Properties();
        patterns.setProperty("pending", "{0} - {1} - need to implement me\n");
        patterns.setProperty("failed", "{0} <<< {1}\n");
        patterns.setProperty("notPerformed", "{0} : {1} (because of previous pending)\n");

		ScenarioReporter reporter = new PrintStreamScenarioReporter(new PrintStream(out),  patterns, new I18nKeyWords(), true);
        reporter.successful("Given I have a balance of $50");
        reporter.successful("When I request $20");
        reporter.failed("When I ask Liz for a loan of $100", exception);
        reporter.pending("Then I should have a balance of $30");
        reporter.notPerformed("Then I should have $20");
        
        ensureThat(out.toString(), equalTo(
                "Given I have a balance of $50" + NL +
                "When I request $20" + NL +
                "When I ask Liz for a loan of $100 <<< FAILED" + NL +
                "Then I should have a balance of $30 - PENDING - need to implement me" + NL +
                "Then I should have $20 : NOT PERFORMED (because of previous pending)" + NL));
        
    }

    @Test
    public void shouldReportEventsToPrintStreamInItalian() {
        IllegalAccessException exception = new IllegalAccessException("Lasciate in pace i miei soldi!");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        I18nKeyWords keywords = new I18nKeyWords(Locale.ITALIAN);
		ScenarioReporter reporter = new PrintStreamScenarioReporter(new PrintStream(out),  new Properties(), keywords, true);
        reporter.successful("Dato che ho un saldo di $50");
        reporter.successful("Quando richiedo $20");
        reporter.failed("Quando chiedo a Liz un prestito di $100", exception);
        reporter.pending("Allora dovrei avere un saldo di $30");
        reporter.notPerformed("Allora dovrei avere $20");
        
        ensureThat(out.toString(), equalTo(
                "Dato che ho un saldo di $50" + NL +
                "Quando richiedo $20" + NL +
                "Quando chiedo a Liz un prestito di $100 (FALLITO)" + NL +
                "Allora dovrei avere un saldo di $30 (PENDENTE)" + NL +
                "Allora dovrei avere $20 (NON ESEGUITO)" + NL));
        
    }

}
