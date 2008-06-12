package org.jbehave.scenario.steps;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class CandidateStep {

    private final Method method;
    private final Steps steps;
    private final StepMonitor monitor;
    private Pattern pattern;

    public CandidateStep(String matchThis, Method method, Steps steps, StepPatternBuilder patternConverter, StepMonitor monitor) {
        this.method = method;
        this.steps = steps;
        this.monitor = monitor;
        pattern = patternConverter.buildPattern(matchThis);
    }

    public boolean matches(String step) {
        if (step.startsWith(precursor())) {
            String trimmed = trimPrecursor(step);
            Matcher matcher = pattern.matcher(trimmed);
            boolean matches = matcher.matches();
            monitor.stepMatchesPattern(step, matches, pattern.pattern());
            return matches;
        }
        return false;
    }

    private String trimPrecursor(String string) {
        return string.substring(precursor().length());
    }

    public Step createFrom(final String stepAsString) {
        Matcher matcher = pattern.matcher(trimPrecursor(stepAsString));
        matcher.find();
        final Object[] args = new Object[matcher.groupCount()];
        for (int group = 0; group < args.length; group++) {
            String arg = matcher.group(group + 1);
            Object converted = convert(arg, method.getParameterTypes()[group]);
            args[group] = converted;
        }
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
                    return StepResult.failure(stepAsString, t.getCause());
                }
                return StepResult.failure(stepAsString, t);
            }

            public StepResult doNotPerform() {
                return StepResult.notPerformed(stepAsString);
            }

        };
    }

    private Object convert(String value, Class<?> clazz) {
        if (clazz == Integer.class || clazz == int.class) {
            return Integer.valueOf(value);
        } else if (clazz == Long.class || clazz == long.class) {
            return Long.valueOf(value);
        } else if (clazz == Double.class || clazz == double.class) {
            return Double.valueOf(value);
        } else if (clazz == Float.class || clazz == float.class) {
            return Float.valueOf(value);
        }
        return value;
    }

    protected abstract String precursor();

}
