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
    private final CandidateSteps steps;
    private final StepMonitor stepMonitor;
    private final ParameterConverters parameterConverters;
    private final String[] startingWords;
    private final Pattern pattern;

    public CandidateStep(String matchThis, Method method, CandidateSteps steps, StepPatternBuilder patterBuilder,
            StepMonitor stepMonitor, ParameterConverters parameterConverters, String... startingWords) {
        this.method = method;
        this.steps = steps;
        this.stepMonitor = stepMonitor;
        this.parameterConverters = parameterConverters;
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
        stepMonitor.stepMatchesPattern(step, matches, pattern.pattern());
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
            Object converted = parameterConverters.convert(arg, types[group]);
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
                    stepMonitor.performing(stepAsString);
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
