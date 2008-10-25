package org.jbehave.scenario.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.util.JUnit4Ensure.ensureThat;

import java.beans.IntrospectionException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import org.jbehave.scenario.steps.ParameterConverters.NumberListConverter;
import org.junit.Test;

public class ParameterConvertersBehaviour {

	private static final NumberFormat NUMBER_FORMAT = NumberFormat
			.getInstance();

	@Test
	public void shouldConvertStringsToNumbers() {
		ParameterConverters converters = new ParameterConverters();
		ensureThat((Integer) converters.convert("3", int.class), equalTo(3));
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

}
