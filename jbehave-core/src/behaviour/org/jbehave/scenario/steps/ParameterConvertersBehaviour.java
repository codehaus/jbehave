package org.jbehave.scenario.steps;

import static org.jbehave.Ensure.ensureThat;
import static org.hamcrest.CoreMatchers.equalTo;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class ParameterConvertersBehaviour {

	@Test
	public void shouldConvertStringsToNumbers() {
		ParameterConverters converters = new ParameterConverters();
		ensureThat((Integer)converters.convert("3", int.class), equalTo(3));
	}
	
//	@SuppressWarnings("unchecked")
//	@Test
//	public void shouldConvertCommaSeparatedValuesToListsOfNumbers() throws ParseException {
//		ParameterConverters converters = new ParameterConverters();
//		List<Number> list = (List<Number>)converters.convert("3, 5, 6, 8.00", Arrays.asList(new Number[]{}).getClass());
//		ensureThat(list.get(0), equalTo(NumberFormat.getInstance().parse("3")));
//		ensureThat(list.get(1), equalTo(NumberFormat.getInstance().parse("5")));
//		ensureThat(list.get(2), equalTo(NumberFormat.getInstance().parse("6")));
//		ensureThat(list.get(3), equalTo(NumberFormat.getInstance().parse("8.00")));
//	}
}
