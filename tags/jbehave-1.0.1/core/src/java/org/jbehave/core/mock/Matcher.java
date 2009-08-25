/*
 * Created on 02-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.mock;


/**
 * Represents a matcher on a method argument.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface Matcher {
    boolean matches(Object arg);
}