package org.jbehave.scenario.reporters;

import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;
import static org.jbehave.scenario.steps.CandidateStep.PARAMETER_VALUE_END;
import static org.jbehave.scenario.steps.CandidateStep.PARAMETER_VALUE_START;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

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

    public void successful(String step) {
        String defaultPattern = "<div class=\"step.successful\">{0}</div>\n";
        print(format("successful", defaultPattern, escapeHtml(step)));
    }

    public void pending(String step) {
        String defaultPattern = "<div class=\"step.pending\">{0}<span class=\"keyword.pending\">({1})</span></div>\n";
        print(format("pending", defaultPattern, escapeHtml(step), keywords.pending()));
    }

    public void notPerformed(String step) {
        String defaultPattern = "<div class=\"step.notPerformed\">{0}<span class=\"keyword.notPerformed\">({1})</span></div>\n";
        print(format("notPerformed", defaultPattern, escapeHtml(step), keywords.notPerformed()));
    }

    public void failed(String step, Throwable cause) {
        this.cause = cause;
        String defaultPattern = "<div class=\"step.failed\">{0}<span class=\"keyword.failed\">({1})</span></div>\n";
        print(format("failed", defaultPattern, escapeHtml(step), keywords.failed()));
    }

    public void beforeStory(StoryDefinition story, boolean embeddedStory) {
        String defaultPattern = "<div class=\"story\">\n<h1>{0}</h1>\n({1})\n";
        print(format("beforeStory", defaultPattern, story.getBlurb().asString(), story.getStoryPath()));
    }

    public void afterStory(boolean embeddedStory) {
        print(format("afterStory", "</div>\n"));
    }

    public void beforeScenario(String title) {
        cause = null;
        String defaultPattern = "<div class=\"scenario\">\n<h2>{0} {1}</h2>\n";
        print(format("beforeScenario", defaultPattern, keywords.scenario(), escapeHtml(title)));
    }

    public void afterScenario() {
        print(format("afterScenario", "</div>\n"));
    }

    public void givenScenarios(List<String> givenScenarios) {
        String defaultPattern = "<div class=\"givenScenarios\">{0} {1}</div>\n";
        print(format("givenScenarios", defaultPattern, keywords.givenScenarios(), escapeHtml(givenScenarios
                .toString())));
    }

    public void examplesTable(ExamplesTable table) {
        print(format("examplesTable", "<h3 class=\"examplesTable\">{0}</h3>\n", keywords.examplesTable()));
        print(format("examplesTableStart", "<table class=\"examplesTable\">\n"));
        final List<Map<String, String>> rows = table.getRows();
        final List<String> headers = table.getHeaders();
        print(format("examplesTableHeadStart", "<thead>\n<tr>\n"));
        for (String header : headers) {
            print(format("examplesTableHeadCell", "<th>{0}</th>", escapeHtml(header)));
        }
        print(format("examplesTableHeadEnd", "</tr>\n</thead>\n"));
        print(format("examplesTableBodyStart", "<tbody>\n"));
        for (Map<String, String> row : rows) {
            print(format("examplesTableRowStart", "<tr>\n"));
            for (String header : headers) {
                print(format("examplesTableCell", "<td>{0}</td>", escapeHtml(row.get(header))));
            }
            print(format("examplesTableRowEnd", "</tr>\n"));
        }
        print(format("examplesTableBodyEnd", "</tbody>\n"));
        print(format("examplesTableEnd", "</table>\n"));
    }

    public void examplesTableRow(Map<String, String> tableRow) {
        String defaultPattern = "\n<h3 class=\"examplesTableRow\">{0} {1}</h3>\n";
        print(format("examplesTableRow", defaultPattern, keywords.examplesTableRow(), escapeHtml(tableRow
                .toString())));
    }
    
    protected void print(String text) {
        output.print(text.replace(escapeHtml(PARAMETER_VALUE_START), "<span class=\"step.parameter\">")
                         .replace(escapeHtml(PARAMETER_VALUE_END), "</span>"));
    }

}
