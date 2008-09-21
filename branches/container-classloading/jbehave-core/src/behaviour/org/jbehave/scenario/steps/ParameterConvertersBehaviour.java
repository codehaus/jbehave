package org.jbehave.scenario.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.util.JUnit4Ensure.ensureThat;

import java.beans.IntrospectionException;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import org.junit.Test;

public class ParameterConvertersBehaviour {

	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance();

    @Test
	public void shouldConvertStringsToNumbers() {
		ParameterConverters converters = new ParameterConverters();
		ensureThat((Integer)converters.convert("3", int.class), equalTo(3));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldConvertCommaSeparatedValuesToListsOfNumbers() throws ParseException, IntrospectionException {
		ParameterConverters converters = new ParameterConverters();
		Type type = SomeSteps.methodFor("aMethodWithListOfNumbers").getGenericParameterTypes()[0];
        List<Number> list = (List<Number>)converters.convert("3, 5, 6, 8.00", type);
		ensureThat(list.get(0), equalTo(NUMBER_FORMAT.parse("3")));
		ensureThat(list.get(1), equalTo(NUMBER_FORMAT.parse("5")));
		ensureThat(list.get(2), equalTo(NUMBER_FORMAT.parse("6")));
		ensureThat(list.get(3), equalTo(NUMBER_FORMAT.parse("8.00")));
	}
}
