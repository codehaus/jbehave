/*
 * Created on 25-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.extensions.story.base.Expectation;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class MockableOutcome extends Outcome {

    public MockableOutcome() {
        super(new Expectation[0]);
    }
}
