/*
 * Created on 24-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import jbehave.core.story.visitor.Visitable;
import jbehave.core.story.visitor.Visitor;

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
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[Narrative role=");
        buffer.append(role);
        buffer.append(", feature=");
        buffer.append(feature);
        buffer.append(", benefit=");
        buffer.append(benefit);
        buffer.append("]");
        return buffer.toString();
    }
}
