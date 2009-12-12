package org.jbehave.scenario.reporters;

import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbehave.scenario.definition.Blurb;
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

    private final PrintStreamFactory printStreamFactory;

    public HtmlPrintStreamScenarioReporter(PrintStreamFactory printStreamFactory) {
        this.printStreamFactory = printStreamFactory;
        usePrintStream(printStreamFactory.getPrintStream());
    }

    public void successful(String step) {
        String defaultPattern = "<div class=\"step.successful\">{0}</div>\n";
        String s = format("successful.html", defaultPattern, escapeHtml(step));
        prt(s);
    }

    private void prt(String s) {
        output.print(s);
    }

    public void pending(String step) {
        String defaultPattern = "<div class=\"step.pending\">{0}<span class=\"keyword.pending\">({1})</span></div>\n";
        prt(format("pending.html", defaultPattern, escapeHtml(step), keywords.pending()));
    }

    public void notPerformed(String step) {
        String defaultPattern = "<div class=\"step.notPerformed\">{0}<span class=\"keyword.notPerformed\">({1})</span></div>\n";
        prt(format("notPerformed.html", defaultPattern, escapeHtml(step), keywords.notPerformed()));
    }

    public void failed(String step, Throwable cause) {
        this.cause = cause;
        String defaultPattern = "<div class=\"step.failed\">{0}<span class=\"keyword.failed\">({1})</span></div>\n";
        prt(format("failed.html", defaultPattern, escapeHtml(step), keywords.failed()));
    }

    public void beforeStory(StoryDefinition story, boolean embeddedStory) {
        usePrintStream(printStreamFactory.getPrintStream());
        beforeStory(story.getBlurb());
    }

    public void beforeStory(Blurb blurb) {
        String defaultPattern = "<div class=\"story\">\n<h1>{0}</h1>\n";
        prt(format("beforeStory.html", defaultPattern, blurb.asString()));
    }

    public void afterStory(boolean embeddedStory) {
        prt(format("afterStory.html", "</div>\n"));
    }
    
    public void beforeScenario(String title) {
        cause = null;
        String defaultPattern = "<div class=\"scenario\">\n<h2>{0} {1}</h2>\n";
        prt(format("beforeScenario.html", defaultPattern, keywords.scenario(), escapeHtml(title)));
    }

    public void afterScenario() {
        prt(format("afterScenario.html", "</div>\n"));
    }

    public void givenScenarios(List<String> givenScenarios) {
        String defaultPattern = "<span class=\"givenScenarios\">{0} {1}</span>\n";
        prt(format("givenScenarios.html", defaultPattern, keywords.givenScenarios(), escapeHtml(givenScenarios.toString())));
    }

    public void examplesTable(ExamplesTable table) {
        String defaultPattern = "<h3 class=\"examplesTable\">{0}</h3>\n<table class=\"examplesTable\">\n";
        prt(format("examplesTable.html", defaultPattern, keywords.examplesTable()));
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
        prt(format("examplesTableRow.html", defaultPattern, keywords.examplesTableRow(), escapeHtml(tableRow.toString())));
    }

}
