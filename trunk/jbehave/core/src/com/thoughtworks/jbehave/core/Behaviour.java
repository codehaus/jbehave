/*
 * Created on 29-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;

import com.thoughtworks.jbehave.core.verify.Result;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public interface Behaviour {

    Result verify() throws Exception;

}
