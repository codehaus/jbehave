package org.jbehave.scenario.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import java.beans.IntrospectionException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.jbehave.scenario.definition.ExamplesTable;
import org.jbehave.scenario.steps.ParameterConverters.ExamplesTableConverter;
import org.jbehave.scenario.steps.ParameterConverters.NumberListConverter;
import org.junit.Test;

public class ParameterConvertersBehaviour {

	private static final NumberFormat NUMBER_FORMAT = NumberFormat
			.getInstance();

	@Test
	public void shouldConvertValuesToNumbers() {
		ParameterConverters converters = new ParameterConverters();
		ensureThat((Integer) converters.convert("3", Integer.class), equalTo(3));
		ensureThat((Integer) converters.convert("3", int.class), equalTo(3));
		ensureThat((Float) converters.convert("3.0", Float.class), equalTo(3.0f));
		ensureThat((Float) converters.convert("3.0", float.class), equalTo(3.0f));
		ensureThat((Long) converters.convert("3", Long.class), equalTo(3L));
		ensureThat((Long) converters.convert("3", long.class), equalTo(3L));
		ensureThat((Double) converters.convert("3.0", Double.class), equalTo(3.0d));
		ensureThat((Double) converters.convert("3.0", double.class), equalTo(3.0d));
		ensureThat((BigInteger) converters.convert("3", BigInteger.class), equalTo(new BigInteger("3")));
		ensureThat((BigDecimal) converters.convert("3.0", BigDecimal.class), equalTo(new BigDecimal("3.0")));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldConvertCommaSeparatedValuesToListsOfNumbers()
			throws ParseException, IntrospectionException {
		ParameterConverters converters = new ParameterConverters();
		Type type = SomeSteps.methodFor("aMethodWithListOfNumbers")
				.getGenericParameterTypes()[0];
		List<Number> list = (List<Number>) converters.convert(
				"3, 0.5, 6.1f, 8.00", type);
		ensureThat(list.get(0), equalTo(NUMBER_FORMAT.parse("3")));
		ensureThat(list.get(1), equalTo(NUMBER_FORMAT.parse("0.5")));
		ensureThat(list.get(2), equalTo(NUMBER_FORMAT.parse("6.1f")));
		ensureThat(list.get(3), equalTo(NUMBER_FORMAT.parse("8.00")));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldConvertCommaSeparatedValuesToListsOfNumbersWithCustomFormat()
			throws ParseException, IntrospectionException {
		NumberFormat numberFormat = new DecimalFormat("#,####");
		ParameterConverters converters = new ParameterConverters(
				new NumberListConverter(numberFormat, " "));
		Type type = SomeSteps.methodFor("aMethodWithListOfNumbers")
				.getGenericParameterTypes()[0];
		List<Number> list = (List<Number>) converters.convert(
				"3,000 0.5 6.1f 8.00", type);
		ensureThat(list.get(0), equalTo(numberFormat.parse("3,000")));
		ensureThat(list.get(1), equalTo(numberFormat.parse("0.5")));
		ensureThat(list.get(2), equalTo(numberFormat.parse("6.1f")));
		ensureThat(list.get(3), equalTo(numberFormat.parse("8.00")));
	}
	
    @Test
    public void shouldConvertMultilineTableParameter()
            throws ParseException, IntrospectionException {
        ParameterConverters converters = new ParameterConverters(
                new ExamplesTableConverter());
        Type type = SomeSteps.methodFor("aMethodWithExamplesTable")
                .getGenericParameterTypes()[0];
        ExamplesTable table = (ExamplesTable) converters.convert(
                "|col1|col2|\n|row11|row12|\n|row21|row22|\n", type);
        ensureThat(table.getRowCount(), equalTo(2));
        Map<String, String> row1 = table.getRow(0);
        ensureThat(row1.get("col1"), equalTo("row11"));
        ensureThat(row1.get("col2"), equalTo("row12"));
        Map<String, String> row2 = table.getRow(1);
        ensureThat(row2.get("col1"), equalTo("row21"));
        ensureThat(row2.get("col2"), equalTo("row22"));
    }
	
}
