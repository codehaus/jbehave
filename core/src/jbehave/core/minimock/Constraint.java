/*
 * Created on 02-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.minimock;


/**
 * Represents a constraint on a method argument.
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface Constraint {
    boolean matches(Object arg);
}