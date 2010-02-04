package org.jbehave.scenario.reporters;

import java.io.PrintStream;
import java.util.Properties;

import org.jbehave.scenario.definition.KeyWords;

/**
 * <p>
 * Scenario reporter that outputs to a PrintStream, as XML. It extends
 * {@link PrintStreamScenarioReporter}, providing XML-based default output
 * patterns, which can be overridden via the {@link
 * XmlPrintStreamScenarioReporter(PrintStream,Properties)} constructor.
 * </p>
 * 
 * @author Mauro Talevi
 */
public class XmlPrintStreamScenarioReporter extends PrintStreamScenarioReporter {

    public XmlPrintStreamScenarioReporter(PrintStream output) {
        this(output, defaultHtmlPatterns());
    }

    public XmlPrintStreamScenarioReporter(PrintStream output, Properties outputPatterns) {
        super(mergeWithDefault(outputPatterns), Format.XML);
        usePrintStream(output);
    }
    
    public XmlPrintStreamScenarioReporter(PrintStream output, Properties outputPatterns, 
            KeyWords keywords, boolean reportErrors) {
        super(output, mergeWithDefault(outputPatterns), Format.XML, keywords, reportErrors);
    }
    
    private static Properties mergeWithDefault(Properties outputPatterns) {
        Properties patterns = defaultHtmlPatterns();
        // override any default pattern
        patterns.putAll(outputPatterns);
        return patterns;
    }

    private static Properties defaultHtmlPatterns() {
        Properties patterns = new Properties();
        patterns.setProperty("successful", "<step outcome=\"successful\">{0}</step>\n");
        patterns.setProperty("ignorable", "<step outcome=\"ignorable\">{0}</step>\n");
        patterns.setProperty("pending", "<step outcome=\"pending\" keyword=\"{1}\">{0}</step>\n");
        patterns.setProperty("notPerformed", "<step outcome=\"notPerformed\" keyword=\"{1}\">{0}</step>\n");
        patterns.setProperty("failed", "<step outcome=\"failed\" keyword=\"{1}\">{0}</step>\n");
        patterns.setProperty("beforeStory", "<story path=\"{1}\" title=\"{0}\">\n");
        patterns.setProperty("afterStory", "</story>\n");
        patterns.setProperty("beforeScenario", "<scenario keyword=\"{0}\" title=\"{1}\">\n");
        patterns.setProperty("afterScenario", "</scenario>\n");
        patterns.setProperty("afterScenarioWithFailure", "<failure>{0}</failure>\n</scenario>\n");
        patterns.setProperty("givenScenarios", "<givenScenarios keyword=\"{0}\"paths=\"{1}\"</givenScenarios>\n");
        patterns.setProperty("beforeExamples", "<examples keyword=\"{0}\">\n");
        patterns.setProperty("examplesStep", "<step>{0}</step>\n");
        patterns.setProperty("afterExamples", "</examples>\n");
        patterns.setProperty("examplesTableStart", "<parameters>\n");
        patterns.setProperty("examplesTableHeadStart", "<names>");
        patterns.setProperty("examplesTableHeadCell", "<name>{0}</name>");
        patterns.setProperty("examplesTableHeadEnd", "</names>\n");
        patterns.setProperty("examplesTableBodyStart", "");
        patterns.setProperty("examplesTableRowStart", "<values>");
        patterns.setProperty("examplesTableCell", "<value>{0}</value>");
        patterns.setProperty("examplesTableRowEnd", "</values>\n");
        patterns.setProperty("examplesTableBodyEnd", "");
        patterns.setProperty("examplesTableEnd", "</parameters>\n");
        patterns.setProperty("example", "\n<example keyword=\"{0}\">{1}</example>\n");
        patterns.setProperty("parameterValueStart", "<parameter>");
        patterns.setProperty("parameterValueEnd", "</parameter>");
        return patterns;
    }

}
