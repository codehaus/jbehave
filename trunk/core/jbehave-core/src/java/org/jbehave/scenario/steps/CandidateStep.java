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
import com.thoughtworks.paranamer.Paranamer;
import com.thoughtworks.paranamer.NullParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.BytecodeReadingParanamer;

/**
 * Creates step from its candidate string representations
 * 
 * @author Elizabeth Keogh
 * @author Mauro Talevi
 * @author Paul Hammant
 */
public class CandidateStep {

	private final String stepAsString;
    private final Method method;
    private final CandidateSteps steps;
    private final ParameterConverters parameterConverters;
    private final String[] startingWords;
    private final Pattern pattern;
    private StepMonitor stepMonitor = new SilentStepMonitor();
	private String[] groupNames;
    private Paranamer paranamer = new NullParanamer();

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
        this.groupNames = patternBuilder.extractGroupNames(stepAsString);
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
        String[] annotationNames = annotatedParameterNames();
        String[] parameterNames = paranamer.lookupParameterNames(method, false);
        final Object[] args = new Object[types.length];
        int groupCount = matcher.groupCount();
        for (int ix = 0; ix < types.length; ix++) {
            int annotatedNameIx = parameterIndex(annotationNames, ix);
            int parameterNameIx = parameterIndex(parameterNames, ix);
            String arg = null;
            if (annotatedNameIx != -1 && isGroupName(annotationNames[ix])) {
                arg = getGroup(matcher, annotationNames[ix]);
            } else if (parameterNameIx != -1 && isGroupName(parameterNames[ix])) {
                arg = getGroup(matcher, parameterNames[ix]);
            } else if (annotatedNameIx != -1 && isTableFieldName(tableValues, annotationNames[ix])) {
                arg = getTableValue(tableValues, annotationNames[ix]);
            } else if (parameterNameIx != -1 && isTableFieldName(tableValues, parameterNames[ix])) {
                arg = getTableValue(tableValues, parameterNames[ix]);
            } else {
                arg = matcher.group(ix + 1);
            }
            args[ix] = parameterConverters.convert(arg, types[ix]);
        }
        return createStep(stepAsString, args);
    }

    private String getTableValue(Map<String, String> tableValues, String name) {
        return tableValues.get(name);
    }

    private boolean isTableFieldName(Map<String, String> tableValues, String name) {
        return tableValues.get(name) != null;
    }

    private String getGroup(Matcher matcher, String name) {
        for (int i = 0; i < groupNames.length; i++) {
            String groupName = groupNames[i];
            if (name.equals(groupName)) {
                return matcher.group(i + 1);
            }
        }
        throw new RuntimeException("no group for name");
    }

    private boolean isGroupName(String name) {
        for (String groupName : groupNames) {
            if (name.equals(groupName)) {
                return true;
            }
        }
        return false;
    }

    private int parameterIndex(String[] names, int ix) {
        if (names.length == 0) {
            return -1;
        }
    	String name = names[ix];
    	for ( int index = 0; index < names.length; index++ ){
            String annotatedName = names[index];
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

    public void useParanamer(boolean useIt) {
        if (useIt) {
            this.paranamer = new CachingParanamer(new BytecodeReadingParanamer());
        } else {
            this.paranamer = new NullParanamer();
        }
    }
}
