/*
 * Created on 17-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.listener;

import java.io.IOException;
import java.io.Writer;

import com.thoughtworks.jbehave.extensions.story.domain.Scenario;
import com.thoughtworks.jbehave.extensions.story.domain.ScenarioUsingMiniMock;
import com.thoughtworks.jbehave.extensions.story.domain.UnimplementedException;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class TextScenarioListener implements ScenarioListener {

    public static final String SUCCESS = ".";
    public static final String FAILURE = "F";
    public static final String UNIMPLEMENTED = "U";

    private final Writer writer;

    public TextScenarioListener(Writer writer) {
        this.writer = writer;
    }

    public void scenarioUnimplemented(Scenario scenario, UnimplementedException cause) {
        try {
            writer.write(UNIMPLEMENTED);
            writer.flush();
        }
        catch (IOException e) {
        }
    }

    public void scenarioFailed(Scenario scenario, Exception cause) {
        try {
            writer.write(FAILURE);
            writer.flush();
        }
        catch (IOException e) {
        }
    }

    public void scenarioSucceeded(Scenario scenario) {
        try {
            writer.write(SUCCESS);
            writer.flush();
        }
        catch (IOException e) {
        }
    }

    public void componentUsesMocks(Object component) {
        System.err.println("Component " + component.getClass().getName() + " uses mocks!");
    }
}
