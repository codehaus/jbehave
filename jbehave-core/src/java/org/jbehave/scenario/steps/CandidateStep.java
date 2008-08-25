package org.jbehave.scenario.steps;

import static java.util.Arrays.asList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jbehave.scenario.errors.PendingError;
import org.jbehave.scenario.parser.StepPatternBuilder;

public class CandidateStep {

    private static final String NL = System.getProperty("line.separator");
    private static final String COMMA = ",";
    private final Method method;
    private final Steps steps;
    private final StepMonitor monitor;
    private final String[] startingWords;
    private final Pattern pattern;
    private final List<? extends ArgumentConverter> converters;

    public CandidateStep(String matchThis, Method method, Steps steps, StepPatternBuilder patterBuilder,
            StepMonitor monitor, String... startingWords) {
        this.method = method;
        this.steps = steps;
        this.monitor = monitor;
        this.startingWords = startingWords;
        this.pattern = patterBuilder.buildPattern(matchThis);
        this.converters = asList(new NumberConverter(), new NumberListConverter(), new StringListConverter());
    }

    public boolean matches(String step) {
        String word = findStartingWord(step);
        if (word == null) {
            return false;
        }
        String trimmed = trimStartingWord(word, step);
        Matcher matcher = pattern.matcher(trimmed);
        boolean matches = matcher.matches();
        monitor.stepMatchesPattern(step, matches, pattern.pattern());
        return matches;
    }

    private String trimStartingWord(String word, String step) {
        return step.substring(word.length() + 1); // 1 for the space after
    }

    public Step createFrom(final String stepAsString) {
        String startingWord = findStartingWord(stepAsString);
        Matcher matcher = pattern.matcher(trimStartingWord(startingWord, stepAsString));
        matcher.find();
        Type[] types = method.getGenericParameterTypes();
        final Object[] args = new Object[matcher.groupCount()];
        for (int group = 0; group < args.length; group++) {
            String arg = matcher.group(group + 1);
            Object converted = convert(arg, types[group]);
            args[group] = converted;
        }
        return createStep(stepAsString, args);
    }

    private String findStartingWord(final String stepAsString) {
        for (String word : startingWords) {
            if (stepAsString.startsWith(word)) {
                return word;
            }
        }
        return null;
    }

    private Step createStep(final String stepAsString, final Object[] args) {
        return new Step() {
            public StepResult perform() {
                try {
                    method.invoke(steps, args);
                    return StepResult.success(stepAsString);
                } catch (Throwable t) {
                    return failureWithOriginalException(stepAsString, t);
                }
            }

            private StepResult failureWithOriginalException(final String stepAsString, Throwable t) {
                if (t instanceof InvocationTargetException && t.getCause() != null) {
                    if (t.getCause() instanceof PendingError) {
                        return StepResult.pending(stepAsString, (PendingError) t.getCause());
                    } else {
                        return StepResult.failure(stepAsString, t.getCause());
                    }
                }
                return StepResult.failure(stepAsString, t);
            }

            public StepResult doNotPerform() {
                return StepResult.notPerformed(stepAsString);
            }

        };
    }

    private Object convert(String value, Type type) {
        // check if any converters accepts type
        for (ArgumentConverter converter : converters) {
            if (converter.accept(type)) {
                monitor.convertingValueOfType(value, type, converter.getClass());
                return converter.convertValue(value, type);
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
