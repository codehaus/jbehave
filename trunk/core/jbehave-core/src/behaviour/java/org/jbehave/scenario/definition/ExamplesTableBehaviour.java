package org.jbehave.scenario.definition;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExamplesTableBehaviour {

    @Test
    public void shouldParseTableIntoHeadersAndRows() {
        String tableAsString = "|one|two|\n" + 
                               "|11|12|\n" +
                               "|21|22|\n";
        ensureTableContentIsParsed(new ExamplesTable(tableAsString));
    }

    @Test
    public void shouldTrimTableBeforeParsing() {
        String tableAsString = "|one|two|\n" + 
                               "|11|12|\n" +
                               "|21|22|\n";
        ensureTableContentIsParsed(new ExamplesTable("\n    \n" +tableAsString + "\n    \n"));
    }

    private void ensureTableContentIsParsed(ExamplesTable table) {
        assertEquals(asList("one", "two"), table.getHeaders());
        assertEquals(2, table.getRows().size());
        assertEquals("11", tableElement(table, 0, "one"));
        assertEquals("12", tableElement(table, 0, "two"));
        assertEquals("21", tableElement(table, 1, "one"));
        assertEquals("22", tableElement(table, 1, "two"));
    }

    private String tableElement(ExamplesTable table, int row, String header) {
        return table.getRow(row).get(header);
    }

}
