package org.jbehave.scenario.reporters;

import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.jbehave.scenario.definition.Blurb;
import org.jbehave.scenario.definition.ExamplesTable;
import org.jbehave.scenario.definition.KeyWords;
import org.jbehave.scenario.definition.StoryDefinition;

/**
 * <p>
 * Scenario reporter that outputs to a PrintStream, as HTML. It extends
 * {@link PrintStreamScenarioReporter}, providing HTML-based default output
 * patterns, which can be overridden via the {@link
 * HtmlPrintStreamScenarioReporter(Properties)} constructor.
 * </p>
 * 
 * @author Mirko FriedenHagen
 * @author Mauro Talevi
 */
public class HtmlPrintStreamScenarioReporter extends PrintStreamScenarioReporter {

    public HtmlPrintStreamScenarioReporter() {
        super();
    }

    public HtmlPrintStreamScenarioReporter(KeyWords keywords) {
        super(keywords);
    }

    public HtmlPrintStreamScenarioReporter(PrintStream output) {
        super(output);
    }

    public HtmlPrintStreamScenarioReporter(Properties outputPatterns) {
        super(outputPatterns);
    }

    public HtmlPrintStreamScenarioReporter(PrintStream output, Properties outputPatterns, KeyWords keywords,
            boolean reportErrors) {
        super(output, outputPatterns, keywords, reportErrors);
    }

    public void successful(String step) {
        String defaultPattern = "<li class=\"step\"><span class=\"step.successful\"><span class=\"step.text\">{0}</span></span></li>\n";
        output.print(format("successful.html", defaultPattern, escapeHtml(step)));
    }

    public void pending(String step) {
        String defaultPattern = "<li class=\"step\"><span class=\"step.pending\"><span class=\"step.text\">{0}</span> - {1}</span></li>\n";
        output.print(format("pending.html", defaultPattern, escapeHtml(step), keywords.pending()));
    }

    public void notPerformed(String step) {
        String defaultPattern = "<li class=\"step\"><span class=\"step.notPerformed\"><span class=\"step.text\">{0}</span> - {1}</span></li>\n";
        output.print(format("pending.html", defaultPattern, escapeHtml(step), keywords.notPerformed()));
    }

    public void failed(String step, Throwable cause) {
        this.cause = cause;
        String defaultPattern = "<li class=\"step\"><span class=\"step.failed\">"
                + "<span class=\"step.text\">{0}</span> - {1} </span>\n";
        output.print(format("failed.html", defaultPattern, escapeHtml(step), keywords.failed()));
    }

    public void beforeStory(StoryDefinition story) {
        beforeStory(story.getBlurb());
    }

    public void beforeStory(Blurb blurb) {
        String defaultPattern = "<html>\n<head>\n<title>{0}</title>\n</head>\n<body>\n<h1 class=\"story\">{0}</h1>\n";
        output.print(format("beforeStory.html", defaultPattern, blurb.asString()));
    }

    public void beforeScenario(String title) {
        cause = null;
        String defaultPattern = "<h2>{0} {1}</h2>\n<ol title=\"{1}\" class=\"scenario\">\n";
        output.print(format("beforeScenario.html", defaultPattern, keywords.scenario(), escapeHtml(title)));
    }

    public void afterScenario() {
        output.print(format("afterScenario.html", "</ol>\n"));
    }

    public void afterStory() {
        output.print(format("afterStory.html", "<body>\n</html>\n"));
    }

    public void givenScenarios(List<String> givenScenarios) {
        String defaultPattern = "<span class=\"givenScenarios\">{0} {1}</span>\n";
        output.print(format("givenScenarios.html", defaultPattern, keywords.givenScenarios(), escapeHtml(givenScenarios.toString())));
    }

    public void examplesTable(ExamplesTable table) {
        String defaultPattern = "<h3 class=\"examplesTable\">{0}</h3>\n<table class=\"examplesTable\">\n";
        output.print(format("examplesTable.html", defaultPattern, keywords.examplesTable()));
        final List<Map<String, String>> rows = table.getRows();
        final Set<String> columnNames = rows.get(0).keySet();
        output.println("<thead>\n<tr>");
        for (String columnName : columnNames) {
            output.printf("<th>%s</th>", escapeHtml(columnName));
        }
        output.println("</tr>\n</thead>");
        output.println("<tbody>");
        for (Map<String, String> row : rows) {
            output.println("<tr>");
            for (String columnName : columnNames) {
                output.printf("<td>%s</td>", escapeHtml(row.get(columnName)));
            }
            output.println("</tr>");
        }
        output.println("</tbody>");
        output.println("</table>");
    }

    public void examplesTableRow(Map<String, String> tableRow) {
        String defaultPattern = "\n<h3 class=\"examplesTableRow\">{0} {1}</h3>\n";
        output.print(format("examplesTableRow.html", defaultPattern, keywords.examplesTableRow(), escapeHtml(tableRow.toString())));
    }

}
