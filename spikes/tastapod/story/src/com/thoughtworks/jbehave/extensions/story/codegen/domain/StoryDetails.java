/*
 * Created on 01-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.codegen.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North </a>
 */
public class StoryDetails extends BasicDetails {
    private final String role;
    private final String feature;
    private final String benefit;
    private final List scenarios = new ArrayList();

    public StoryDetails(String name, String role, String feature, String benefit) {
        super(name, "");
        this.role = role;
        this.feature = feature;
        this.benefit = benefit;
    }

    public void addScenario(ScenarioDetails scenario) {
        scenarios.add(scenario);
    }

    public String getBenefit() {
        return benefit;
    }

    public String getFeature() {
        return feature;
    }

    public String getRole() {
        return role;
    }

    public List getScenarios() {
        return scenarios;
    }
}