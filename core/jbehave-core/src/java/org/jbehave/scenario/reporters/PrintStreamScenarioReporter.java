package org.jbehave.scenario.reporters;

import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.jbehave.scenario.definition.Blurb;
import org.jbehave.scenario.definition.ExamplesTable;
import org.jbehave.scenario.definition.KeyWords;
import org.jbehave.scenario.definition.StoryDefinition;
import org.jbehave.scenario.i18n.I18nKeyWords;

/**
 * <p>
 * Scenario reporter that outputs to a PrintStream, defaulting to System.out.
 * </p>
 * <p>
 * The output of the reported event is also configurable via two other means:
 * <ul>
 * <li>custom output patterns, providing only the patterns that differ from
 * default</li>
 * <li>keywords in different languages, providing the i18n locale</li>
 * </ul>
 * </p>
 * <p>
 * Let's look at example of providing custom output patterns, e.g. for the
 * failed event. <br/>
 * we'd need to provide the custom pattern, say we want to have something like
 * "(step being executed) <<< FAILED", keyed on the method name:
 * 
 * <pre>
 * Properties patterns = new Properties();
 * patterns.setProperty(&quot;failed&quot;, &quot;{0} &lt;&lt;&lt; {1}&quot;);
 * </pre>
 * 
 * The pattern is by default processed and formatted by the
 * {@link MessageFormat}. Both the {@link #format()} and
 * {@link #lookupPattern()} methods are overrideable and a different formatter
 * or pattern lookup can be used by subclasses.
 * </p>
 * <p>
 * If the keyword "FAILED" (or any other keyword used by the reporter) needs to
 * be expressed in a different language, all we need to do is to provide an
 * instance of {@link I18nKeyWords} using the appropriate {@link Locale}, e.g.
 * 
 * <pre>
 * KeyWords keywords = new I18nKeyWords(new Locale(&quot;it&quot;);
 * </pre>
 * 
 * </p>
 */
public class PrintStreamScenarioReporter implements ScenarioReporter {

    protected PrintStream output;
    protected final Properties outputPatterns;
    protected final KeyWords keywords;
    protected final boolean reportErrors;
    protected Throwable cause;

    public PrintStreamScenarioReporter() {
        this(System.out);
    }

    public PrintStreamScenarioReporter(PrintStream output) {
        this(output, new Properties(), new I18nKeyWords(), false);
    }

    public PrintStreamScenarioReporter(Properties outputPatterns) {
        this(System.out, outputPatterns, new I18nKeyWords(), false);
    }

    public PrintStreamScenarioReporter(KeyWords keywords) {
        this(System.out, new Properties(), keywords, false);
    }

    public PrintStreamScenarioReporter(PrintStream output, Properties outputPatterns, KeyWords keywords,
            boolean reportErrors) {
        this.output = output;
        this.outputPatterns = outputPatterns;
        this.keywords = keywords;
        this.reportErrors = reportErrors;
    }

    public void successful(String step) {
        output.print(format("successful", "{0}\n", step));
    }

    public void pending(String step) {
        output.print(format("pending", "{0} ({1})\n", step, keywords.pending()));
    }

    public void notPerformed(String step) {
        output.print(format("notPerformed", "{0} ({1})\n", step, keywords.notPerformed()));
    }

    public void failed(String step, Throwable cause) {
        this.cause = cause;
        output.print(format("failed", "{0} ({1})\n", step, keywords.failed()));
    }

    public void afterScenario() {
        output.println();
        if (reportErrors && cause != null) {
            cause.printStackTrace(output);
            output.println();
        }
    }

    public void beforeScenario(String title) {
        cause = null;
        output.print(format("beforeScenario", "{0} {1}\n", keywords.scenario(), title));
    }

    public void afterStory(boolean embeddedStory) {
        output.print(format("afterStory", "\n"));
        if ( !embeddedStory && cause != null) {
            throw new RuntimeException(cause);
        }
    }

    public void afterStory() {
        output.print(format("afterStory", "\n"));
    }

    public void beforeStory(StoryDefinition story, boolean embeddedStory) {
        output.print(format("beforeStory", "{0}\n", story.getBlurb().asString()));
    }

    public void beforeStory(Blurb blurb) {
        output.print(format("beforeStory", "{0}\n", blurb.asString()));
    }

    public void givenScenarios(List<String> givenScenarios) {
        output.print(format("givenScenarios", "{0} {1}\n", keywords.givenScenarios(), givenScenarios));
    }

    public void examplesTable(ExamplesTable table) {
        output.print(format("examplesTable", "{0}\n\n{1}\n\n", keywords.examplesTable(), table));
    }

    public void examplesTableRow(Map<String, String> tableRow) {
        output.print(format("examplesTableRow", "\n{0} {1}\n", keywords.examplesTableRow(), tableRow));
    }

    /**
     * Formats event output by key, conventionally equal to the method name.
     * 
     * @param key the event key
     * @param defaultPattern the default pattern to return if a custom pattern
     *            is not found
     * @param args the args used to format output
     * @return A formatted event output
     */
    protected String format(String key, String defaultPattern, Object... args) {
        return MessageFormat.format(lookupPattern(key, defaultPattern), args);
    }

    /**
     * Looks up the format pattern for the event output by key, conventionally
     * equal to the method name. The pattern is used by the
     * {#format(String,String,Object...)} method and by default is formatted
     * using the {@link MessageFormat#format()} method. If no pattern is found
     * for key or needs to be overridden, the default pattern should be
     * returned.
     * 
     * @param key the format pattern key
     * @param defaultPattern the default pattern if no pattern is
     * @return The format patter for the given key
     */
    protected String lookupPattern(String key, String defaultPattern) {
        if (outputPatterns.containsKey(key)) {
            return outputPatterns.getProperty(key);
        }
        return defaultPattern;
    }

    /**
     * Changes print stream used for output
     * 
     * @param output the new PrintStream to use
     */
    protected void usePrintStream(PrintStream output) {
        this.output = output;
    }

}
