/*
 * Created on 17-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.listener;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.jbehave.core.listener.PlainTextListener;
import com.thoughtworks.jbehave.core.result.Result;
import com.thoughtworks.jbehave.core.util.ConvertCase;
import com.thoughtworks.jbehave.core.util.Timer;
import com.thoughtworks.jbehave.extensions.story.result.ScenarioResult;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class PlainTextScenarioListener extends PlainTextListener {
    private final List usedMocks = new ArrayList();
    
    public PlainTextScenarioListener(Writer writer) {
        super(writer, new Timer());
    }
    
    public PlainTextScenarioListener(Writer writer, Timer timer) {
        super(writer, timer);
    }
    
    public void gotResult(Result result) {
        if (result instanceof ScenarioResult) {
            ScenarioResult scenarioResult = (ScenarioResult) result;
            if (scenarioResult.usedMocks()) {
               usedMocks.add(result);
            }
        }
        super.gotResult(result);
    }
    
    protected void printDetails() {
        super.printDetails();
        if (!usedMocks.isEmpty()) {
            printUsedMocks();
        }
    }
    
    protected void printResult(int count, Result result) {
        String storyClassName = result.containerName();
        String storyName =  new ConvertCase(result.containerName()).toSeparateWords();
        out.println(count + ") " + storyName + " -> " + result.name() + " [" + storyClassName + "]");
    }
    
    private void printUsedMocks() {
        if (!usedMocks.isEmpty()) {
            out.println();
            out.println("Used mocks: " + usedMocks.size());
            out.println();
            int count = 1;
            for (Iterator i = usedMocks.iterator(); i.hasNext(); count++) {
                Result result =  (Result) i.next();
                printResult(count, result);
            }
            out.println();
        }
    }
}
