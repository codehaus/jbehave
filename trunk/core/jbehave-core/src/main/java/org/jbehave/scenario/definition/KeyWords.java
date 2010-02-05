package org.jbehave.scenario.definition;

import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbehave.scenario.i18n.StringEncoder;

/**
 * Provides the keywords which allow parsers to find steps in scenarios and
 * match those steps with candidates through the annotations (Given, When and Then)
 * or though other keywords (And, "!--").  It also provides keywords used in reporting.  
 */
public class KeyWords {

    public static final String SCENARIO = "Scenario";
    public static final String GIVEN_SCENARIOS = "GivenScenarios";
    public static final String EXAMPLES_TABLE = "ExamplesTable";
    public static final String GIVEN = "Given";
    public static final String WHEN = "When";
    public static final String THEN = "Then";
    public static final String AND = "And";
    public static final String IGNORABLE = "Ignorable";
    public static final String PENDING = "Pending";
    public static final String NOT_PERFORMED = "NotPerformed";
    public static final String FAILED = "Failed";
    public static final String EXAMPLES_TABLE_ROW = "ExamplesTableRow";
    public static final List<String> KEYWORDS = asList(SCENARIO, GIVEN_SCENARIOS, EXAMPLES_TABLE, GIVEN, WHEN, THEN,
            AND, IGNORABLE, PENDING, NOT_PERFORMED, FAILED, EXAMPLES_TABLE_ROW);

    private final String scenario;
    private final String givenScenarios;
    private final String examplesTable;
    private final String given;
    private final String when;
    private final String then;
    private final String and;
    private final String ignorable;
    private final String pending;
    private final String notPerformed;
    private final String failed;
    private final String examplesTableRow;
    private final String[] others;
    private StringEncoder encoder;

    public static Map<String, String> defaultKeywords() {
        Map<String, String> keywords = new HashMap<String, String>();
        keywords.put(SCENARIO, "Scenario:");
        keywords.put(GIVEN_SCENARIOS, "GivenScenarios:");
        keywords.put(EXAMPLES_TABLE, "Examples:");
        keywords.put(GIVEN, "Given");
        keywords.put(WHEN, "When");
        keywords.put(THEN, "Then");
        keywords.put(AND, "And");
        keywords.put(IGNORABLE, "!--");
        keywords.put(PENDING, "PENDING");
        keywords.put(NOT_PERFORMED, "NOT PERFORMED");
        keywords.put(FAILED, "FAILED");
        keywords.put(EXAMPLES_TABLE_ROW, "Example:");
        return keywords;
    }

    public KeyWords() {
        this(defaultKeywords());
    }

    public KeyWords(Map<String, String> keywords) {
        this(keywords, new StringEncoder());
    }

    public KeyWords(Map<String, String> keywords, StringEncoder encoder) {
        this.scenario = keywords.get(SCENARIO);
        this.givenScenarios =  keywords.get(GIVEN_SCENARIOS);
        this.examplesTable = keywords.get(EXAMPLES_TABLE);
        this.given =  keywords.get(GIVEN);
        this.when = keywords.get(WHEN);
        this.then = keywords.get(THEN);
        this.and = keywords.get(AND);
        this.ignorable = keywords.get(IGNORABLE);
        this.pending = keywords.get(PENDING);
        this.notPerformed = keywords.get(NOT_PERFORMED);
        this.failed = keywords.get(FAILED);
        this.examplesTableRow = keywords.get(EXAMPLES_TABLE_ROW);
        this.others = new String[]{and, ignorable};
        this.encoder = encoder;
    }

    /**
     * @deprecated Use KeyWords(Map<String,String>, StringEncoder)
     */
    public KeyWords(String scenario, String givenScenarios, String examplesTable, String given, String when,
            String then, String... others) {
        this.scenario = scenario;
        this.givenScenarios = givenScenarios;
        this.examplesTable = examplesTable;
        this.given = given;
        this.when = when;
        this.then = then;
        if (others.length < 6) {
            throw new IllegalArgumentException("Insufficient keywords: " + asList(others) + ", but requires another "
                    + (6 - others.length));
        }
        this.and = others[0];
        this.ignorable = others[1];
        this.pending = others[2];
        this.notPerformed = others[3];
        this.failed = others[4];
        this.examplesTableRow = others[5];
        this.others = others;
    }

    public String scenario() {
        return scenario;
    }

    public String givenScenarios() {
        return givenScenarios;
    }

    public String examplesTable() {
        return examplesTable;
    }

    public String given() {
        return given;
    }

    public String when() {
        return when;
    }

    public String then() {
        return then;
    }

    public String and() {
        return and;
    }

    public String ignorable() {
        return ignorable;
    }

    public String pending() {
        return pending;
    }

    public String notPerformed() {
        return notPerformed;
    }

    public String failed() {
        return failed;
    }

    public String examplesTableRow() {
        return examplesTableRow;
    }

    public String[] others() {
        return others;
    }

    public String encode(String value) {
        if (encoder != null) {
            return encoder.encode(value);
        }
        return value;
    }

}
