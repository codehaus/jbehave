package org.jbehave.scenario.reporters;

import java.io.PrintStream;
import java.util.Properties;

import org.jbehave.scenario.definition.KeyWords;

/**
 * <p>
 * Scenario reporter that outputs to a PrintStream, as HTML. It extends
 * {@link PrintStreamScenarioReporter}, providing HTML-based default output
 * patterns, which can be overridden via the {@link
 * HtmlPrintStreamScenarioReporter(PrintStream,Properties)} constructor.
 * </p>
 * 
 * @author Mirko FriedenHagen
 * @author Mauro Talevi
 */
public class HtmlPrintStreamScenarioReporter extends PrintStreamScenarioReporter {

    public HtmlPrintStreamScenarioReporter(PrintStream output) {
        this(output, defaultHtmlPatterns());
    }

    public HtmlPrintStreamScenarioReporter(PrintStream output, Properties outputPatterns) {
        super(mergeWithDefault(outputPatterns), Format.HTML);
        usePrintStream(output);
    }
    
    public HtmlPrintStreamScenarioReporter(PrintStream output, Properties outputPatterns, 
            KeyWords keywords, boolean reportErrors) {
        super(output, mergeWithDefault(outputPatterns), Format.HTML, keywords, reportErrors);
    }
    
    private static Properties mergeWithDefault(Properties outputPatterns) {
        Properties patterns = defaultHtmlPatterns();
        // override any default pattern
        patterns.putAll(outputPatterns);
        return patterns;
    }

    private static Properties defaultHtmlPatterns() {
        Properties patterns = new Properties();
        patterns.setProperty("successful", "<div class=\"step successful\">{0}</div>\n");
        patterns.setProperty("pending", "<div class=\"step pending\">{0}<span class=\"keyword pending\">({1})</span></div>\n");
        patterns.setProperty("notPerformed", "<div class=\"step notPerformed\">{0}<span class=\"keyword notPerformed\">({1})</span></div>\n");
        patterns.setProperty("failed", "<div class=\"step failed\">{0}<span class=\"keyword failed\">({1})</span></div>\n");
        patterns.setProperty("beforeStory", "<link href=\"style/jbehave-reports.css\" rel=\"stylesheet\" type=\"text/css\">\n<div class=\"story\">\n<h1>{0}</h1>\n<h2>{1}</h2>\n");
        patterns.setProperty("afterStory", "</div>\n");
        patterns.setProperty("beforeScenario", "<div class=\"scenario\">\n<h2>{0} {1}</h2>\n");
        patterns.setProperty("afterScenario", "</div>\n");
        patterns.setProperty("afterScenarioWithFailure", "<div class=\"scenario.failure\">{0}</div>\n</div>\n");
        patterns.setProperty("givenScenarios", "<div class=\"givenScenarios\">{0} {1}</div>\n");
        patterns.setProperty("examplesTable", "<h3 class=\"examplesTable\">{0}</h3>\n");
        patterns.setProperty("examplesTableStart", "<table class=\"examplesTable\">\n");
        patterns.setProperty("examplesTableHeadStart", "<thead>\n<tr>\n");
        patterns.setProperty("examplesTableHeadCell", "<th>{0}</th>");
        patterns.setProperty("examplesTableHeadEnd", "</tr>\n</thead>\n");
        patterns.setProperty("examplesTableBodyStart", "<tbody>\n");
        patterns.setProperty("examplesTableRowStart", "<tr>\n");
        patterns.setProperty("examplesTableCell", "<td>{0}</td>");
        patterns.setProperty("examplesTableRowEnd", "</tr>\n");
        patterns.setProperty("examplesTableBodyEnd", "</tbody>\n");
        patterns.setProperty("examplesTableEnd", "</table>\n");
        patterns.setProperty("examplesTableRow", "\n<h3 class=\"examplesTableRow\">{0} {1}</h3>\n");
        patterns.setProperty("parameterValueStart", "<span class=\"step parameter\">");
        patterns.setProperty("parameterValueEnd", "</span>");
        return patterns;
    }

}
