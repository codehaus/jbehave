/*
 * Created on 28-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.story.visitor;



/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface Visitable {
    void accept(Visitor visitor) throws Exception;
}
