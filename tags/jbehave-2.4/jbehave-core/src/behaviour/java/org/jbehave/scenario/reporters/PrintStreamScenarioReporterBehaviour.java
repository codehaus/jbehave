package org.jbehave.scenario.reporters;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;
import static org.jbehave.scenario.reporters.ScenarioReporterBuilder.Format.HTML;
import static org.jbehave.scenario.reporters.ScenarioReporterBuilder.Format.TXT;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.definition.Blurb;
import org.jbehave.scenario.definition.ExamplesTable;
import org.jbehave.scenario.definition.ScenarioDefinition;
import org.jbehave.scenario.definition.StoryDefinition;
import org.jbehave.scenario.i18n.I18nKeyWords;
import org.jbehave.scenario.parser.ScenarioNameResolver;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;
import org.jbehave.scenario.reporters.FilePrintStreamFactory.FileConfiguration;
import org.jbehave.scenario.reporters.FreemarkerReportRenderer.RenderingFailedException;
import org.junit.Test;

public class PrintStreamScenarioReporterBehaviour {

    @Test
    public void shouldReportEventsToPrintStream() {
        // Given
        OutputStream out = new ByteArrayOutputStream();
        ScenarioReporter reporter = new PrintStreamScenarioReporter(new PrintStream(out));

        // When
        narrateAnInterestingStory(reporter);

        // Then
        String expected = "An interesting story\n" 
                + "(/path/to/story)\n" 
                + "Scenario: I ask for a loan\n"
                + "GivenScenarios: [/given/scenario1,/given/scenario2]\n" 
                + "Given I have a balance of $50\n"
                + "When I request $20\n" 
                + "When I ask Liz for a loan of $100\n"
                + "Then I should have a balance of $30 (PENDING)\n" 
                + "Then I should have $20 (NOT PERFORMED)\n"
                + "Then I don't return loan (FAILED)\n" 
                + "Examples:\n" 
                + "Given money <money>\n"
                + "Then I give it to <to>\n"
                + "\n"
                + "|money|to|\n" + "|$30|Mauro|\n"
                + "|$50|Paul|\n" + "\n\n" // Examples table
                + "\nExample: {to=Mauro, money=$30}\n" 
                + "\nExample: {to=Paul, money=$50}\n" 
                + "\n" // end of examples
                + "\n\n"; // end of scenario and story
        ensureThatOutputIs(out, expected);
    }

    @Test
    public void shouldReportEventsToHtmlPrintStream() {
        // Given
        final OutputStream out = new ByteArrayOutputStream();
        PrintStreamFactory factory = new PrintStreamFactory() {

            public PrintStream getPrintStream() {
                return new PrintStream(out);
            }
        };
        ScenarioReporter reporter = new HtmlPrintStreamScenarioReporter(factory.getPrintStream());

        // When
        narrateAnInterestingStory(reporter);

        // Then
        String expected = "<div class=\"story\">\n<h1>An interesting story</h1>\n"
                + "<div class=\"path\">/path/to/story</div>\n"
                + "<div class=\"scenario\">\n<h2>Scenario: I ask for a loan</h2>\n"
                + "<div class=\"givenScenarios\">GivenScenarios: [/given/scenario1,/given/scenario2]</div>\n"
                + "<div class=\"step successful\">Given I have a balance of $50</div>\n"
                + "<div class=\"step successful\">When I request $20</div>\n"
                + "<div class=\"step successful\">When I ask Liz for a loan of $100</div>\n"
                + "<div class=\"step pending\">Then I should have a balance of $30 <span class=\"keyword pending\">(PENDING)</span></div>\n"
                + "<div class=\"step notPerformed\">Then I should have $20 <span class=\"keyword notPerformed\">(NOT PERFORMED)</span></div>\n"
                + "<div class=\"step failed\">Then I don't return loan <span class=\"keyword failed\">(FAILED)</span></div>\n"
                + "<div class=\"examples\">\n" + "<h3>Examples:</h3>\n" 
                + "<div class=\"step\">Given money &lt;money&gt;</div>\n"
                + "<div class=\"step\">Then I give it to &lt;to&gt;</div>\n"
                + "<table>\n" + "<thead>\n"
                + "<tr>\n<th>money</th><th>to</th></tr>\n" + "</thead>\n" + "<tbody>\n"
                + "<tr>\n<td>$30</td><td>Mauro</td></tr>\n" + "<tr>\n<td>$50</td><td>Paul</td></tr>\n" + "</tbody>\n"
                + "</table>\n" + "\n<h3 class=\"example\">Example: {to=Mauro, money=$30}</h3>\n"
                + "\n<h3 class=\"example\">Example: {to=Paul, money=$50}</h3>\n" + "</div>\n" + // end
                // of
                // examples
                "</div>\n</div>\n"; // end of scenario and story
        ensureThatOutputIs(out, expected);
    }

    @Test
    public void shouldReportEventsToHtmlPrintStreamUsingCustomOutputPatterns() {
        // Given
        final OutputStream out = new ByteArrayOutputStream();
        PrintStreamFactory factory = new PrintStreamFactory() {

            public PrintStream getPrintStream() {
                return new PrintStream(out);
            }
        };
        Properties patterns = new Properties();
        patterns.setProperty("afterStory", "</div><!-- after story -->\n");
        patterns.setProperty("afterScenario", "</div><!-- after scenario -->\n");
        patterns.setProperty("afterExamples", "</div><!-- after examples -->\n");
        ScenarioReporter reporter = new HtmlPrintStreamScenarioReporter(factory.getPrintStream(), patterns);

        // When
        narrateAnInterestingStory(reporter);

        // Then
        String expected = "<div class=\"story\">\n<h1>An interesting story</h1>\n"
                + "<div class=\"path\">/path/to/story</div>\n"
                + "<div class=\"scenario\">\n<h2>Scenario: I ask for a loan</h2>\n"
                + "<div class=\"givenScenarios\">GivenScenarios: [/given/scenario1,/given/scenario2]</div>\n"
                + "<div class=\"step successful\">Given I have a balance of $50</div>\n"
                + "<div class=\"step successful\">When I request $20</div>\n"
                + "<div class=\"step successful\">When I ask Liz for a loan of $100</div>\n"
                + "<div class=\"step pending\">Then I should have a balance of $30 <span class=\"keyword pending\">(PENDING)</span></div>\n"
                + "<div class=\"step notPerformed\">Then I should have $20 <span class=\"keyword notPerformed\">(NOT PERFORMED)</span></div>\n"
                + "<div class=\"step failed\">Then I don't return loan <span class=\"keyword failed\">(FAILED)</span></div>\n"
                + "<div class=\"examples\">\n" + "<h3>Examples:</h3>\n" 
                + "<div class=\"step\">Given money &lt;money&gt;</div>\n"
                + "<div class=\"step\">Then I give it to &lt;to&gt;</div>\n"
                + "<table>\n" + "<thead>\n"
                + "<tr>\n<th>money</th><th>to</th></tr>\n" + "</thead>\n" + "<tbody>\n"
                + "<tr>\n<td>$30</td><td>Mauro</td></tr>\n" + "<tr>\n<td>$50</td><td>Paul</td></tr>\n" + "</tbody>\n"
                + "</table>\n" + "\n<h3 class=\"example\">Example: {to=Mauro, money=$30}</h3>\n"
                + "\n<h3 class=\"example\">Example: {to=Paul, money=$50}</h3>\n" + "</div><!-- after examples -->\n"
                + "</div><!-- after scenario -->\n" + "</div><!-- after story -->\n";
        ensureThatOutputIs(out, expected);
    }

    @Test
    public void shouldReportEventsToXmlPrintStream() {
        // Given
        final OutputStream out = new ByteArrayOutputStream();
        PrintStreamFactory factory = new PrintStreamFactory() {

            public PrintStream getPrintStream() {
                return new PrintStream(out);
            }
        };
        ScenarioReporter reporter = new XmlPrintStreamScenarioReporter(factory.getPrintStream());

        // When
        narrateAnInterestingStory(reporter);

        // Then
        String expected = "<story path=\"/path/to/story\" title=\"An interesting story\">\n"
                + "<scenario keyword=\"Scenario:\" title=\"I ask for a loan\">\n"
                + "<givenScenarios keyword=\"GivenScenarios:\"paths=\"[/given/scenario1,/given/scenario2]\"</givenScenarios>\n"
                + "<step outcome=\"successful\">Given I have a balance of $50</step>\n"
                + "<step outcome=\"successful\">When I request $20</step>\n"
                + "<step outcome=\"successful\">When I ask Liz for a loan of $100</step>\n"
                + "<step outcome=\"pending\" keyword=\"PENDING\">Then I should have a balance of $30</step>\n"
                + "<step outcome=\"notPerformed\" keyword=\"NOT PERFORMED\">Then I should have $20</step>\n"
                + "<step outcome=\"failed\" keyword=\"FAILED\">Then I don&apos;t return loan</step>\n"
                + "<examples keyword=\"Examples:\">\n" 
                + "<step>Given money &lt;money&gt;</step>\n"
                + "<step>Then I give it to &lt;to&gt;</step>\n"
                + "<parameters>\n"
                + "<names><name>money</name><name>to</name></names>\n"
                + "<values><value>$30</value><value>Mauro</value></values>\n"
                + "<values><value>$50</value><value>Paul</value></values>\n" + "</parameters>\n"
                + "\n<example keyword=\"Example:\">{to=Mauro, money=$30}</example>\n"
                + "\n<example keyword=\"Example:\">{to=Paul, money=$50}</example>\n" + "</examples>\n"
                + "</scenario>\n" + "</story>\n";
        ensureThatOutputIs(out, expected);
    }

    private void narrateAnInterestingStory(ScenarioReporter reporter) {
        StoryDefinition story = new StoryDefinition(new Blurb("An interesting story"),
                "/path/to/story", new ArrayList<ScenarioDefinition>());
        boolean embeddedStory = true;
        reporter.beforeStory(story, embeddedStory);
        String title = "I ask for a loan";
        reporter.beforeScenario(title);
        reporter.givenScenarios(asList("/given/scenario1,/given/scenario2"));
        reporter.successful("Given I have a balance of $50");
        reporter.successful("When I request $20");
        reporter.successful("When I ask Liz for a loan of $100");
        reporter.pending("Then I should have a balance of $30");
        reporter.notPerformed("Then I should have $20");
        reporter.failed("Then I don't return loan", new Exception("Naughty me!"));
        ExamplesTable table = new ExamplesTable("|money|to|\n|$30|Mauro|\n|$50|Paul|\n");
        reporter.beforeExamples(asList("Given money <money>", "Then I give it to <to>"), table);
        reporter.example(table.getRow(0));
        reporter.example(table.getRow(1));
        reporter.afterExamples();
        reporter.afterScenario();
        reporter.afterStory(embeddedStory);
    }

    private void ensureThatOutputIs(OutputStream out, String expected) {
        // JUnit assertion allows easier comparison of strings in IDE
        assertEquals(expected, dos2unix(out.toString()));
        // ensureThat(out.toString(), equalTo(expected));
    }

    private String dos2unix(String string) {
        return string.replace("\r\n", "\n");
    }

    @Test
    public void shouldReportThrowablesWhenToldToDoSo() {
        // Given
        IllegalAccessException exception = new IllegalAccessException("Leave my money alone!");
        OutputStream stackTrace = new ByteArrayOutputStream();
        exception.printStackTrace(new PrintStream(stackTrace));
        OutputStream out = new ByteArrayOutputStream();
        ScenarioReporter reporter = new PrintStreamScenarioReporter(new PrintStream(out), new Properties(),
                new I18nKeyWords(), true);

        // When
        reporter.beforeScenario("A title");
        reporter.successful("Given I have a balance of $50");
        reporter.successful("When I request $20");
        reporter.failed("When I ask Liz for a loan of $100", exception);
        reporter.pending("Then I should have a balance of $30");
        reporter.notPerformed("Then I should have $20");
        reporter.afterScenario();

        // Then
        String expected = "Scenario: A title\n" + "Given I have a balance of $50\n" + "When I request $20\n"
                + "When I ask Liz for a loan of $100 (FAILED)\n" + "Then I should have a balance of $30 (PENDING)\n"
                + "Then I should have $20 (NOT PERFORMED)\n" + "\n" + dos2unix(stackTrace.toString()) + "\n";
        ensureThatOutputIs(out, expected);

        // Given
        out = new ByteArrayOutputStream();
        reporter = new PrintStreamScenarioReporter(new PrintStream(out));

        // When
        reporter.beforeScenario("A title");
        reporter.successful("Given I have a balance of $50");
        reporter.successful("When I request $20");
        reporter.failed("When I ask Liz for a loan of $100", exception);
        reporter.pending("Then I should have a balance of $30");
        reporter.notPerformed("Then I should have $20");
        reporter.afterScenario();

        // Then
        ensureThat(!out.toString().contains(stackTrace.toString()));
    }

    @Test
    public void shouldReportEventsToPrintStreamWithCustomPatterns() {
        // Given
        IllegalAccessException exception = new IllegalAccessException("Leave my money alone!");
        OutputStream out = new ByteArrayOutputStream();
        Properties patterns = new Properties();
        patterns.setProperty("pending", "{0} - {1} - need to implement me\n");
        patterns.setProperty("failed", "{0} <<< {1}\n");
        patterns.setProperty("notPerformed", "{0} : {1} (because of previous pending)\n");
        ScenarioReporter reporter = new PrintStreamScenarioReporter(new PrintStream(out), patterns, new I18nKeyWords(),
                true);

        // When
        reporter.successful("Given I have a balance of $50");
        reporter.successful("When I request $20");
        reporter.failed("When I ask Liz for a loan of $100", exception);
        reporter.pending("Then I should have a balance of $30");
        reporter.notPerformed("Then I should have $20");

        // Then
        String expected = "Given I have a balance of $50\n" + "When I request $20\n"
                + "When I ask Liz for a loan of $100 <<< FAILED\n"
                + "Then I should have a balance of $30 - PENDING - need to implement me\n"
                + "Then I should have $20 : NOT PERFORMED (because of previous pending)\n";

        ensureThatOutputIs(out, expected);

    }

    @Test
    public void shouldReportEventsToPrintStreamInItalian() {
        // Given
        IllegalAccessException exception = new IllegalAccessException("Lasciate in pace i miei soldi!");
        OutputStream out = new ByteArrayOutputStream();
        I18nKeyWords keywords = new I18nKeyWords(Locale.ITALIAN);
        ScenarioReporter reporter = new PrintStreamScenarioReporter(new PrintStream(out), new Properties(), keywords,
                true);

        // When
        reporter.successful("Dato che ho un saldo di $50");
        reporter.successful("Quando richiedo $20");
        reporter.failed("Quando chiedo a Liz un prestito di $100", exception);
        reporter.pending("Allora dovrei avere un saldo di $30");
        reporter.notPerformed("Allora dovrei avere $20");

        // Then
        String expected = "Dato che ho un saldo di $50\n" + "Quando richiedo $20\n"
                + "Quando chiedo a Liz un prestito di $100 (FALLITO)\n"
                + "Allora dovrei avere un saldo di $30 (PENDENTE)\n" + "Allora dovrei avere $20 (NON ESEGUITO)\n";

        ensureThatOutputIs(out, expected);

    }

    @Test
    public void shouldCreateAndWriteToFilePrintStreamForScenarioClass() throws IOException {

        // Given
        Class<MyScenario> scenarioClass = MyScenario.class;
        ScenarioNameResolver converter = new UnderscoredCamelCaseResolver(".scenario");
        FilePrintStreamFactory factory = new FilePrintStreamFactory(scenarioClass, converter);
        File file = factory.getOutputFile();
        file.delete();
        ensureThat(!file.exists());

        // When
        PrintStream printStream = factory.getPrintStream();
        printStream.print("Hello World");

        // Then
        ensureThat(file.exists());
        ensureThat(IOUtils.toString(new FileReader(file)), equalTo("Hello World"));
    }

    @Test
    public void shouldReportEventsToFilePrintStreamsAndRenderAggregatedIndex() throws IOException {
        Class<MyScenario> scenarioClass = MyScenario.class;
        ScenarioNameResolver nameResolver = new UnderscoredCamelCaseResolver();
        FilePrintStreamFactory printStreamFactory = new FilePrintStreamFactory(scenarioClass, nameResolver);
        ScenarioReporter reporter = new ScenarioReporterBuilder(printStreamFactory).with(HTML).with(TXT)
                .build();

        // When
        narrateAnInterestingStory(reporter);
        File outputDirectory = printStreamFactory.getOutputFile().getParentFile();
        ReportRenderer renderer = new FreemarkerReportRenderer();
        renderer.render(outputDirectory, asList("html", "txt"));

        // Then
        ensureFileExists(new File(outputDirectory, "rendered/index.html"));
    }

    @Test
    public void shouldBuildPrintStreamReportersAndOverrideDefaultForAGivenFormat() throws IOException {
        Class<MyScenario> scenarioClass = MyScenario.class;
        ScenarioNameResolver nameResolver = new UnderscoredCamelCaseResolver();
        FilePrintStreamFactory factory = new FilePrintStreamFactory(scenarioClass, nameResolver);
        ScenarioReporter reporter = new ScenarioReporterBuilder(factory){
               public ScenarioReporter reporterFor(Format format){
                       switch (format) {
                           case TXT:
                               factory.useConfiguration(new FileConfiguration("text"));
                               return new PrintStreamScenarioReporter(factory.getPrintStream(), new Properties(),  new I18nKeyWords(), true);
                            default:
                               return super.reporterFor(format);
                       }
                   }
        }.with(TXT).build();

        // When
        narrateAnInterestingStory(reporter);        

        // Then
        File outputFile = factory.getOutputFile();
        ensureFileExists(outputFile);
    }
    
    private void ensureFileExists(File renderedOutput) throws IOException, FileNotFoundException {
        ensureThat(renderedOutput.exists());
        ensureThat(IOUtils.toString(new FileReader(renderedOutput)).length() > 0);
    }    
    
    @Test(expected = RenderingFailedException.class)
    public void shouldFailRenderingOutputWithInexistentTemplates() throws IOException {
        // Given
        Properties templates = new Properties();
        templates.setProperty("index", "target/inexistent");
        ReportRenderer renderer = new FreemarkerReportRenderer(templates);
        // When
        File outputDirectory = new File("target");
        renderer.render(outputDirectory, asList("html"));
        // Then ... fail as expected
    }

    private static class MyScenario extends Scenario {

    }
}
