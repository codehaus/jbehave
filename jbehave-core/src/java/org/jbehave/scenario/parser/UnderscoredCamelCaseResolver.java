package org.jbehave.scenario.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jbehave.scenario.RunnableScenario;

/**
 * <p>
 * Resolves scenario names converting the Java scenario class to underscored eg:
 * "org.jbehave.scenario.ICanLogin.java" -> "org/jbehave/scenario/i_can_login".
 * </p>
 * <p>
 * By default no extension is used, but this can be configured via the
 * constructor so that we can resolve name to eg
 * "org/jbehave/scenario/i_can_login.scenario".
 * </p>
 */
public class UnderscoredCamelCaseResolver extends AbstractScenarioNameResolver {

    private static final String DEFAULT_EXTENSION = "";
    private static final String SIMPLE_TO_UNDERSCORED_PATTERN = "([A-Z0-9].*?)([A-Z0-9]|\\z)";
    private static final String UNDERSCORE = "_";
    private final String extension;

    public UnderscoredCamelCaseResolver() {
        this(DEFAULT_EXTENSION);
    }

    public UnderscoredCamelCaseResolver(String extension) {
        this.extension = extension;
    }

    public String resolve(Class<? extends RunnableScenario> scenarioClass) {
        return toPackageDir(scenarioClass) + SLASH + toUnderscoredName(scenarioClass.getSimpleName()) + extension;
    }

	private String toUnderscoredName(String simpleName) {
		Matcher matcher = Pattern.compile(SIMPLE_TO_UNDERSCORED_PATTERN).matcher(simpleName);
        int startAt = 0;
        StringBuilder builder = new StringBuilder();
        while (matcher.find(startAt)) {
            builder.append(matcher.group(1).toLowerCase());
            builder.append(UNDERSCORE);
            startAt = matcher.start(2);
        }
        return builder.substring(0, builder.length() - 1);
	}

}
