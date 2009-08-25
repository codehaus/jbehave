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
/* TODO 2.0 Move this to org.jbehave.core.matchers */
/* TODO 2.0 Add the describe(arg) method and remove all CustomMatcher ifs / casts */
public interface Matcher {
    boolean matches(Object arg);
}