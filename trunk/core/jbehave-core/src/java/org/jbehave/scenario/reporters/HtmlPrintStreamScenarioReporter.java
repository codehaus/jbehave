package org.jbehave.scenario.reporters;

import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbehave.scenario.definition.ExamplesTable;
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

    public HtmlPrintStreamScenarioReporter(PrintStream printStream) {
        usePrintStream(printStream);
    }

    private void print(String message) {
        output.print(message);
    }

    private void println(String message) {
        output.println(message);
    }

    public void successful(String step) {
        String defaultPattern = "<div class=\"step.successful\">{0}</div>\n";
        print(format("successful.html", defaultPattern, escapeHtml(step)));
    }

    public void pending(String step) {
        String defaultPattern = "<div class=\"step.pending\">{0}<span class=\"keyword.pending\">({1})</span></div>\n";
        print(format("pending.html", defaultPattern, escapeHtml(step), keywords.pending()));
    }

    public void notPerformed(String step) {
        String defaultPattern = "<div class=\"step.notPerformed\">{0}<span class=\"keyword.notPerformed\">({1})</span></div>\n";
        print(format("notPerformed.html", defaultPattern, escapeHtml(step), keywords.notPerformed()));
    }

    public void failed(String step, Throwable cause) {
        this.cause = cause;
        String defaultPattern = "<div class=\"step.failed\">{0}<span class=\"keyword.failed\">({1})</span></div>\n";
        print(format("failed.html", defaultPattern, escapeHtml(step), keywords.failed()));
    }

    public void beforeStory(StoryDefinition story, boolean embeddedStory) {
        String defaultPattern = "<div class=\"story\">\n<h1>{0}</h1>\n({1})\n";
        print(format("beforeStory.html", defaultPattern, story.getBlurb().asString(), story.getStoryPath()));
    }

    public void afterStory(boolean embeddedStory) {
        print(format("afterStory.html", "</div>\n"));
    }

    public void beforeScenario(String title) {
        cause = null;
        String defaultPattern = "<div class=\"scenario\">\n<h2>{0} {1}</h2>\n";
        print(format("beforeScenario.html", defaultPattern, keywords.scenario(), escapeHtml(title)));
    }

    public void afterScenario() {
        print(format("afterScenario.html", "</div>\n"));
    }

    public void givenScenarios(List<String> givenScenarios) {
        String defaultPattern = "<div class=\"givenScenarios\">{0} {1}</div>\n";
        print(format("givenScenarios.html", defaultPattern, keywords.givenScenarios(), escapeHtml(givenScenarios
                .toString())));
    }

    public void examplesTable(ExamplesTable table) {
        String defaultPattern = "<h3 class=\"examplesTable\">{0}</h3>\n<table class=\"examplesTable\">\n";
        print(format("examplesTable.html", defaultPattern, keywords.examplesTable()));
        final List<Map<String, String>> rows = table.getRows();
        final Set<String> columnNames = rows.get(0).keySet();
        println("<thead>\n<tr>");
        for (String columnName : columnNames) {
            print(format("examplesTableHeader.html", "<th>{0}</th>", escapeHtml(columnName)));
        }
        println("</tr>\n</thead>");
        println("<tbody>");
        for (Map<String, String> row : rows) {
            println("<tr>");
            for (String columnName : columnNames) {
                print(format("examplesTableCell.html", "<td>{0}</td>", escapeHtml(row.get(columnName))));
            }
            println("</tr>");
        }
        println("</tbody>");
        println("</table>");
    }

    public void examplesTableRow(Map<String, String> tableRow) {
        String defaultPattern = "\n<h3 class=\"examplesTableRow\">{0} {1}</h3>\n";
        print(format("examplesTableRow.html", defaultPattern, keywords.examplesTableRow(), escapeHtml(tableRow
                .toString())));
    }

}
