package org.jbehave.scenario.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import org.junit.Test;

public class CamelCaseToUnderscoreConverterBehaviour {

    @Test
    public void shouldConvertCamelCasedClassNameToUnderscoredName() {
        CamelCaseToUnderscoreConverter converter = new CamelCaseToUnderscoreConverter();
        ensureThat(converter.convert(CamelCaseToUnderscoreConverterBehaviour.class),
                equalTo("org/jbehave/scenario/parser/camel_case_to_underscore_converter_behaviour"));

    }
}
