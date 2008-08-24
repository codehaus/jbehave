package org.jbehave.scenario.parser;

import org.jbehave.scenario.Scenario;

public class CasePreservingResolver implements ScenarioFileNameResolver {

    private final String extension;

    public CasePreservingResolver(String extension) {
        this.extension = extension;
    }

    public String resolve(Class<? extends Scenario> scenarioClass) {
        String packageDir = scenarioClass.getPackage().getName().replaceAll("\\.", "/");        
        return packageDir + "/" + scenarioClass.getSimpleName() + extension;
    }

}
