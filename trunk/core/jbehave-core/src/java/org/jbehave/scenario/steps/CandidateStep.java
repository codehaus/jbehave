package org.jbehave.scenario.steps;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jbehave.scenario.annotations.Named;
import org.jbehave.scenario.errors.PendingError;
import org.jbehave.scenario.parser.StepPatternBuilder;

import com.thoughtworks.paranamer.NullParanamer;
import com.thoughtworks.paranamer.Paranamer;

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
	private final String[] groupNames;
	
	private StepMonitor stepMonitor = new SilentStepMonitor();
	private Paranamer paranamer = new NullParanamer();

	public CandidateStep(String stepAsString, Method method,
			CandidateSteps steps, StepPatternBuilder patterBuilder,
			StepMonitor stepMonitor, ParameterConverters parameterConverters,
			String... startingWords) {
		this(stepAsString, method, steps, patterBuilder, parameterConverters,
				startingWords);
		useStepMonitor(stepMonitor);
	}

	public CandidateStep(String stepAsString, Method method,
			CandidateSteps steps, StepPatternBuilder patternBuilder,
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

	public void useParanamer(Paranamer paranamer) {
		this.paranamer = paranamer;
	}

	public boolean matches(String stepAsString) {
		String word = findStartingWord(stepAsString);
		if (word == null) {
			return false;
		}
		String trimmed = trimStartingWord(word, stepAsString);
		Matcher matcher = pattern.matcher(trimmed);
		boolean matches = matcher.matches();
		stepMonitor.stepMatchesPattern(stepAsString, matches, pattern.pattern());
		return matches;
	}

	private String trimStartingWord(String word, String step) {
		return step.substring(word.length() + 1); // 1 for the space after
	}

	public Step createFrom(Map<String, String> tableRow,
			final String stepAsString) {
		String startingWord = findStartingWord(stepAsString);
		Matcher matcher = pattern.matcher(trimStartingWord(startingWord,
				stepAsString));
		matcher.find();
		Type[] types = method.getGenericParameterTypes();
		String[] annotationNames = annotatedParameterNames();
		String[] parameterNames = paranamer.lookupParameterNames(method, false);
		Object[] args = argsForStep(tableRow, matcher, types,
				annotationNames, parameterNames);
		return createStep(stepAsString, args);
	}

	private Object[] argsForStep(Map<String, String> tableRow,
			Matcher matcher, Type[] types, String[] annotationNames,
			String[] parameterNames) {
		final Object[] args = new Object[types.length];
		for (int position = 0; position < types.length; position++) {
			String arg = argForPosition(position, annotationNames, parameterNames,
					tableRow, matcher);
			args[position] = parameterConverters.convert(arg, types[position]);
		}
		return args;
	}

	private String argForPosition(int position, String[] annotationNames,
			String[] parameterNames, Map<String, String> tableRow,
			Matcher matcher) {
		int annotatedNamePosition = parameterPosition(annotationNames, position);
		int parameterNamePosition = parameterPosition(parameterNames, position);
		String arg = null;
		if (annotatedNamePosition != -1 && isGroupName(annotationNames[position])) {
			String name = annotationNames[position];
			stepMonitor.usingAnnotatedNameForArg(name, position);
			arg = getGroup(matcher, name);
		} else if (parameterNamePosition != -1
				&& isGroupName(parameterNames[position])) {
			String name = parameterNames[position];
			stepMonitor.usingParameterNameForArg(name, position);
			arg = getGroup(matcher, name);
		} else if (annotatedNamePosition != -1
				&& isTableFieldName(tableRow, annotationNames[position])) {
			String name = annotationNames[position];
			stepMonitor.usingTableAnnotatedNameForArg(name, position);
			arg = getTableValue(tableRow, name);
		} else if (parameterNamePosition != -1
				&& isTableFieldName(tableRow, parameterNames[position])) {
			String name = parameterNames[position];
			stepMonitor.usingTableParameterNameForArg(name, position);
			arg = getTableValue(tableRow, name);
		} else {
			stepMonitor.usingNaturalOrderForArg(position);
			arg = matcher.group(position + 1);
		}
		stepMonitor.foundArg(arg, position);
		return arg;
	}

	private String getTableValue(Map<String, String> tableRow, String name) {
		return tableRow.get(name);
	}

	private boolean isTableFieldName(Map<String, String> tableRow,
			String name) {
		return tableRow.get(name) != null;
	}

	private String getGroup(Matcher matcher, String name) {
		for (int i = 0; i < groupNames.length; i++) {
			String groupName = groupNames[i];
			if (name.equals(groupName)) {
				return matcher.group(i + 1);
			}
		}
		throw new NoGroupFoundForNameException("No group found for name "+name+" amongst "+Arrays.asList(groupNames));
	}

	private boolean isGroupName(String name) {
		for (String groupName : groupNames) {
			if (name.equals(groupName)) {
				return true;
			}
		}
		return false;
	}

	private int parameterPosition(String[] names, int position) {
		if (names.length == 0) {
			return -1;
		}
		String name = names[position];
		for (int i = 0; i < names.length; i++) {
			String annotatedName = names[i];
			if (annotatedName != null && name.equals(annotatedName)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Extract annotated parameter names from the @Named parameter annotations
	 * 
	 * @return An array of annotated parameter names, which <b>may</b> include
	 *         <code>null</code> values for parameters that are not annotated
	 */
	private String[] annotatedParameterNames() {
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		String[] names = new String[parameterAnnotations.length];
		for (int x = 0; x < parameterAnnotations.length; x++) {
			Annotation[] annotations = parameterAnnotations[x];
			for (int y = 0; y < annotations.length; y++) {
				Annotation annotation = annotations[y];
				if (annotation.annotationType().isAssignableFrom(Named.class)) {
					names[x] = ((Named) annotation).value();
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

			private StepResult failureWithOriginalException(
					final String stepAsString, Throwable t) {
				if (t instanceof InvocationTargetException
						&& t.getCause() != null) {
					if (t.getCause() instanceof PendingError) {
						return StepResult.pending(stepAsString,
								(PendingError) t.getCause());
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

	public String getStepAsString() {
		return stepAsString;
	}

	public Pattern getPattern() {
		return pattern;
	}

	@Override
	public String toString() {
		return stepAsString;
	}
	
	@SuppressWarnings("serial")
	public static class NoGroupFoundForNameException extends RuntimeException {

		public NoGroupFoundForNameException(String message) {
			super(message);
		}

	}

}
