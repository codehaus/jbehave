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
public class UnderscoredCamelCaseResolver implements ScenarioNameResolver {

    private static final String DOT_REGEX = "\\.";
    private static final String SLASH = "/";
    private static final String PATTERN = "([A-Z].*?)([A-Z]|\\z)";
    private static final String UNDERSCORE = "_";
    private final String extension;

    public UnderscoredCamelCaseResolver() {
        this("");
    }

    public UnderscoredCamelCaseResolver(String extension) {
        this.extension = extension;
    }

    public String resolve(Class<? extends RunnableScenario> scenarioClass) {
        String packageDir = scenarioClass.getPackage().getName().replaceAll(DOT_REGEX, SLASH);
        Matcher matcher = Pattern.compile(PATTERN).matcher(scenarioClass.getSimpleName());
        int startAt = 0;
        StringBuilder builder = new StringBuilder();
        while (matcher.find(startAt)) {
            builder.append(matcher.group(1).toLowerCase());
            builder.append(UNDERSCORE);
            startAt = matcher.start(2);
        }

        String underscoredName = builder.substring(0, builder.length() - 1);
        return packageDir + SLASH + underscoredName + extension;
    }

}
