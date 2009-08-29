package org.jbehave.scenario.steps;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jbehave.scenario.annotations.Named;
import org.jbehave.scenario.errors.PendingError;
import org.jbehave.scenario.parser.StepPatternBuilder;

/**
 * Creates step from its candidate string representations
 * 
 * @author Elizabeth Keogh
 * @author Mauro Talevi
 */
public class CandidateStep {

	private final String stepAsString;
    private final Method method;
    private final CandidateSteps steps;
    private final ParameterConverters parameterConverters;
    private final String[] startingWords;
    private final Pattern pattern;
    private StepMonitor stepMonitor = new SilentStepMonitor();
	private String[] parameterNames;

    public CandidateStep(String stepAsString, Method method, CandidateSteps steps, StepPatternBuilder patterBuilder,
            StepMonitor stepMonitor, ParameterConverters parameterConverters, String... startingWords) {
        this(stepAsString, method, steps, patterBuilder, parameterConverters, startingWords);
        useStepMonitor(stepMonitor);
    }

    public CandidateStep(String stepAsString, Method method, CandidateSteps steps, StepPatternBuilder patternBuilder,
            ParameterConverters parameterConverters, String... startingWords) {
        this.stepAsString = stepAsString;
        this.method = method;
        this.steps = steps;
        this.parameterConverters = parameterConverters;
        this.startingWords = startingWords;
        this.pattern = patternBuilder.buildPattern(stepAsString);
        this.parameterNames = patternBuilder.extractParameterNames(stepAsString);
    }

    public void useStepMonitor(StepMonitor stepMonitor) {
        this.stepMonitor = stepMonitor;
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

    public Step createFrom(Map<String, String> tableValues, final String stepAsString) {
        String startingWord = findStartingWord(stepAsString);
        Matcher matcher = pattern.matcher(trimStartingWord(startingWord, stepAsString));
        matcher.find();
        Type[] types = method.getGenericParameterTypes();
        String[] annotatedParameterNames = annotatedParameterNames();
        int groupCount = matcher.groupCount();
		final Object[] args = new Object[types.length];
        for (int group = 0; group < types.length; group++) {
            int parameterIndex = parameterIndex(annotatedParameterNames, group);
            int groupIndex = -1; 
            Type type = null;
            if ( parameterIndex != -1 ){ // we are using annotated parameters
            	groupIndex = parameterIndex + 1;
                type = types[parameterIndex];
            } else {                    // default natural ordering
                groupIndex = group + 1;
                type = types[group];
            }

            String arg = null;
            if ( useAnnotatedParameterNames(tableValues) ){
            	arg = tableValues.get(annotatedParameterNames[parameterIndex]);
            } else {
            	arg = matcher.group(groupIndex);            	
            }
            Object converted = parameterConverters.convert(arg, type);
            args[group] = converted;
        }
        return createStep(stepAsString, args);
    }

	private boolean useAnnotatedParameterNames(Map<String, String> tableValues) {
		return tableValues.size() > 0;
	}

    private int parameterIndex(String[] annotatedNames, int group) {
    	String name = annotatedNames[group];    	
    	for ( int index = 0; index < annotatedNames.length; index++ ){
            String annotatedName = annotatedNames[index];
            if ( annotatedName != null && name.equals(annotatedName) ){
    			return index;
    		}
    	}
    	return -1;
	}

	/**
     * Extract annotated parameter names from the @Named parameter annotations
     * @return An array of annotated parameter names, which <b>may</b> include <code>null</code> values
     * for parameters that are not annotated
     */
    private String[] annotatedParameterNames() {
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
    	String[] names = new String[parameterAnnotations.length];
        for (int x = 0; x < parameterAnnotations.length; x ++) {
        	Annotation[] annotations = parameterAnnotations[x];        	
        	for (int y = 0; y < annotations.length; y ++) {
        		Annotation annotation = annotations[y];
    			if (annotation.annotationType().isAssignableFrom(Named.class) ){
    				names[x] = ((Named)annotation).value();
        		}
        	}
        }
		return names;
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
    
    public String getStepAsString(){
    	return stepAsString;
    }

    public Pattern getPattern(){
    	return pattern;
    }
    
    @Override
    public String toString() {
    	return stepAsString;
    }

}
