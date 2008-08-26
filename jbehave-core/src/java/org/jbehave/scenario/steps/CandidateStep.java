package org.jbehave.scenario.steps;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jbehave.scenario.errors.PendingError;
import org.jbehave.scenario.parser.StepPatternBuilder;

/**
 * Creates steps from candidate string representations
 * 
 * @author Elizabeth Keogh
 * @author Mauro Talevi
 */
public class CandidateStep {

    private final Method method;
    private final Steps steps;
    private final StepMonitor monitor;
    private final String[] startingWords;
    private final Pattern pattern;
    private final ArgumentConversion conversion;

    public CandidateStep(String matchThis, Method method, Steps steps, StepPatternBuilder patterBuilder,
            StepMonitor monitor, ArgumentConversion argumentConversion, String... startingWords) {
        this.method = method;
        this.steps = steps;
        this.monitor = monitor;
        this.conversion = argumentConversion;
        this.startingWords = startingWords;
        this.pattern = patterBuilder.buildPattern(matchThis);
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
            Object converted = conversion.convert(arg, types[group]);
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

}
