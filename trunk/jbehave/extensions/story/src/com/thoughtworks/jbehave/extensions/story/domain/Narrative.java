/*
 * Created on 24-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.domain;

import com.thoughtworks.jbehave.extensions.story.visitor.Visitable;
import com.thoughtworks.jbehave.extensions.story.visitor.Visitor;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Narrative implements Visitable {

    private final String role;
    private final String feature;
    private final String benefit;

    public Narrative(String role, String feature, String benefit) {
        this.role = role;
        this.feature = feature;
        this.benefit = benefit;
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
    public void accept(Visitor visitor) {
        visitor.visitNarrative(this);
    }
}
