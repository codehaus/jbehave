/*
 * Created on 24-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.story.domain;

import jbehave.core.story.renderer.Renderable;
import jbehave.core.story.renderer.Renderer;

/**
 * <p>A Narrative contains elements that describe the purpose of a 
 * {@link Story}. It consists of a role to whom a benefit is to be 
 * delivered, the feature which will deliver that benefit, and 
 * the benefit itself.</p>
 * 
 * <p>A Narrative's elements can be used to describe the story thus:<ul>
 * <li>As a <role></li>
 * <li>I want <feature></li>
 * <li>So that <benefit></li>
 * </ul></p>
 * 
 * <p>eg:<ul>
 * <li>As a bank manager</li>
 * <li>I want the hole in the wall to refuse cash to overdrawn customers</li>
 * <li>So that I don't end up chasing their debts.</li>
 * </p>
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Narrative implements Renderable {

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
    public void narrateTo(Renderer renderer) {
        renderer.renderNarrative(this);
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
