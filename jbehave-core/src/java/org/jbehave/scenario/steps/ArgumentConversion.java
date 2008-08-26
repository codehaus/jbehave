package org.jbehave.scenario.steps;

import static java.util.Arrays.asList;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for converting argument values to Java objects.
 * 
 * @author Elizabeth Keogh
 * @author Mauro Talevi
 */
public class ArgumentConversion {

    private static final String NL = System.getProperty("line.separator");
    private static final String COMMA = ",";
    private static final List<ArgumentConverter> DEFAULT_CONVERTERS = asList(new NumberConverter(), new NumberListConverter(), new StringListConverter());
    private final StepMonitor monitor;
    private final List<ArgumentConverter> converters = new ArrayList<ArgumentConverter>();

    public ArgumentConversion() {
        this(new SilentStepMonitor());
    }

    public ArgumentConversion(StepMonitor monitor) {
        this.monitor = monitor;
        this.converters.addAll(DEFAULT_CONVERTERS);
    }

    public Object convert(String value, Type type) {
        // check if any converters accepts type
        for (ArgumentConverter converter : converters) {
            if (converter.accept(type)) {
                Object converted = converter.convertValue(value, type);
                monitor.convertedValueOfType(value, type, converted, converter.getClass());
                return converted;
            }
        }
        // default to String
        return replaceNewlinesWithSystemNewlines(value);
    }

    private Object replaceNewlinesWithSystemNewlines(String value) {
        return value.replaceAll("(\n)|(\r\n)", NL);
    }

    private static interface ArgumentConverter {

        boolean accept(Type type);

        Object convertValue(String value, Type type);

    }

    @SuppressWarnings("serial")
    private static class InvalidArgumentException extends RuntimeException {

        public InvalidArgumentException(String message, Throwable cause) {
            super(message, cause);
        }

    }

    private static class NumberConverter implements ArgumentConverter {

        public boolean accept(Type type) {
            if (type instanceof Class) {
                return (type == Integer.class || type == int.class || type == Long.class || type == long.class
                        || type == Double.class || type == double.class || type == Float.class || type == float.class);
            }
            return false;
        }

        public Object convertValue(String value, Type type) {
            if (type == Integer.class || type == int.class) {
                return Integer.valueOf(value);
            } else if (type == Long.class || type == long.class) {
                return Long.valueOf(value);
            } else if (type == Double.class || type == double.class) {
                return Double.valueOf(value);
            } else if (type == Float.class || type == float.class) {
                return Float.valueOf(value);
            }
            return value;
        }

    }

    private static class NumberListConverter implements ArgumentConverter {

        public boolean accept(Type type) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type rawType = parameterizedType.getRawType();
                Type argumentType = parameterizedType.getActualTypeArguments()[0];
                return List.class.isAssignableFrom((Class<?>) rawType)
                        && Number.class.isAssignableFrom((Class<?>) argumentType);
            }
            return false;
        }

        public Object convertValue(String value, Type type) {
            List<String> values = asList(value.split(COMMA));
            NumberFormat numberFormat = NumberFormat.getInstance();
            List<Number> numbers = new ArrayList<Number>();
            for (String numberValue : values) {
                try {
                    numbers.add(numberFormat.parse(numberValue));
                } catch (ParseException e) {
                    throw new InvalidArgumentException(numberValue, e);
                }
            }
            return numbers;
        }

    }

    private static class StringListConverter implements ArgumentConverter {

        public boolean accept(Type type) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type rawType = parameterizedType.getRawType();
                Type argumentType = parameterizedType.getActualTypeArguments()[0];
                return List.class.isAssignableFrom((Class<?>) rawType)
                        && String.class.isAssignableFrom((Class<?>) argumentType);
            }
            return false;
        }

        public Object convertValue(String value, Type type) {
            return asList(value.split(COMMA));
        }

    }

}
