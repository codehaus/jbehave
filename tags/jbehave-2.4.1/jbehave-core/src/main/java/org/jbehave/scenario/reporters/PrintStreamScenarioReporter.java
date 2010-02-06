package org.jbehave.scenario.reporters;

import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;
import static org.apache.commons.lang.StringEscapeUtils.escapeXml;
import static org.jbehave.scenario.reporters.PrintStreamScenarioReporter.Format.PLAIN;
import static org.jbehave.scenario.steps.CandidateStep.PARAMETER_VALUE_END;
import static org.jbehave.scenario.steps.CandidateStep.PARAMETER_VALUE_START;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.ArrayUtils;
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

    private static final String EMPTY = "";

    public enum Format { PLAIN, HTML, XML }
    
    protected PrintStream output;
    private final Properties outputPatterns;
    private final Format format;    
    private final KeyWords keywords;
    private final boolean reportErrors;
    private Throwable cause;
    
    public PrintStreamScenarioReporter() {
        this(System.out);
    }

    public PrintStreamScenarioReporter(PrintStream output) {
        this(output, new Properties(), new I18nKeyWords(), false);
    }

    public PrintStreamScenarioReporter(Properties outputPatterns) {
        this(System.out, outputPatterns, new I18nKeyWords(), false);
    }

    public PrintStreamScenarioReporter(Properties outputPatterns, Format format) {
        this(System.out, outputPatterns, format, new I18nKeyWords(), false);
    }

    public PrintStreamScenarioReporter(KeyWords keywords) {
        this(System.out, new Properties(), keywords, false);
    }

    public PrintStreamScenarioReporter(PrintStream output, Properties outputPatterns, KeyWords keywords,
            boolean reportErrors) {
        this(output, outputPatterns, PLAIN, keywords, reportErrors);
    }

    public PrintStreamScenarioReporter(PrintStream output, Properties outputPatterns, Format format,
            KeyWords keywords, boolean reportErrors) {
        this.output = output;
        this.outputPatterns = outputPatterns;
        this.format = format;
        this.keywords = keywords;
        this.reportErrors = reportErrors;   
    }

    public void successful(String step) {
        print(format("successful", "{0}\n", step));
    }

    public void ignorable(String step) {
        print(format("ignorable", "{0}\n", step));
    }

    public void pending(String step) {
        print(format("pending", "{0} ({1})\n", step, keywords.pending()));
    }

    public void notPerformed(String step) {
        print(format("notPerformed", "{0} ({1})\n", step, keywords.notPerformed()));
    }

    public void failed(String step, Throwable cause) {
        this.cause = cause;
        print(format("failed", "{0} ({1})\n", step, keywords.failed()));
    }

    public void beforeStory(StoryDefinition story, boolean embeddedStory) {
        print(format("beforeStory", "{0}\n({1})\n", story.getBlurb().asString(), story.getPath()));
    }

    public void beforeStory(Blurb blurb) {
        beforeStory(new StoryDefinition(blurb), false);
    }

    public void afterStory(boolean embeddedStory) {
        print(format("afterStory", "\n"));
        if ( !embeddedStory && cause != null) {
            throw new RuntimeException(cause);
        }
    }

    public void afterStory() {
        afterStory(false);
    }

    public void givenScenarios(List<String> givenScenarios) {
        print(format("givenScenarios", "{0} {1}\n", keywords.givenScenarios(), givenScenarios));
    }

    public void beforeScenario(String title) {
        cause = null;
        print(format("beforeScenario", "{0} {1}\n", keywords.scenario(), title));
    }

    public void afterScenario() {
        if (cause != null && reportErrors) {
            print(format("afterScenarioWithFailure", "\n{0}\n", stackTrace(cause)));
        } else {
            print(format("afterScenario", "\n"));
        }
    }

    private String stackTrace(Throwable cause) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();        
        cause.printStackTrace(new PrintStream(out));
        return out.toString();
    }

    public void beforeExamples(List<String> steps, ExamplesTable table) {
        print(format("beforeExamples", "{0}\n", keywords.examplesTable()));
        for (String step : steps) {
            print(format("examplesStep", "{0}\n", step));
        }
        print(format("examplesTableStart", "\n"));
        final List<Map<String, String>> rows = table.getRows();
        final List<String> headers = table.getHeaders();
        print(format("examplesTableHeadStart", "|"));
        for (String header : headers) {
            print(format("examplesTableHeadCell", "{0}|", header));
        }
        print(format("examplesTableHeadEnd", "\n"));
        print(format("examplesTableBodyStart", EMPTY));
        for (Map<String, String> row : rows) {
            print(format("examplesTableRowStart", "|"));
            for (String header : headers) {
                print(format("examplesTableCell", "{0}|", row.get(header)));
            }
            print(format("examplesTableRowEnd", "\n"));
        }
        print(format("examplesTableBodyEnd", "\n"));
        print(format("examplesTableEnd", "\n"));
    }

    public void example(Map<String, String> tableRow) {
        print(format("example", "\n{0} {1}\n", keywords.examplesTableRow(), tableRow));
    }

    public void afterExamples() {
        print(format("afterExamples", "\n"));
    }

    public void examplesTable(ExamplesTable table) {
        beforeExamples(new ArrayList<String>(), table);        
    }

    public void examplesTableRow(Map<String, String> tableRow) {
        example(tableRow);        
    }

    /**
     * Formats event output by key, usually equal to the method name.
     * 
     * @param key the event key
     * @param defaultPattern the default pattern to return if a custom pattern
     *            is not found
     * @param args the args used to format output
     * @return A formatted event output
     */
    protected String format(String key, String defaultPattern, Object... args) {
        return MessageFormat.format(lookupPattern(key, escape(defaultPattern)), escapeAll(args));
    }

    private String escape(String defaultPattern) {
        return (String) escapeAll(defaultPattern)[0];
    }

    private Object[] escapeAll(Object... args) {
        return escape(format, args);
    }

    /**
     * Escapes args' string values according to format
     * 
     * @param format the Format used by the PrintStream
     * @param args the array of args to escape
     * @return The cloned and escaped array of args
     */
    protected Object[] escape(final Format format, Object... args) {
        // Transformer that escapes HTML and XML strings
        Transformer escapingTransformer = new Transformer( ) {
            public Object transform(Object object) {
                switch ( format ){
                    case HTML: return escapeHtml(asString(object));
                    case XML: return escapeXml(asString(object));
                    default: return object;
                }
            }

            private String asString(Object object) {
                return  ( object != null ? object.toString() : EMPTY );
            }
        };
        List<?> list = Arrays.asList( ArrayUtils.clone( args ) );
        CollectionUtils.transform( list, escapingTransformer );
        return list.toArray();
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

    /**
     * Prints text to output stream, replacing parameter start and end placeholders
     * 
     * @param text the String to print
     */
    protected void print(String text) {
        output.print(text.replace(format(PARAMETER_VALUE_START, PARAMETER_VALUE_START), format("parameterValueStart", EMPTY))
                         .replace(format(PARAMETER_VALUE_END, PARAMETER_VALUE_END), format("parameterValueEnd", EMPTY)));
    }

}
