/*
 * Created on 29-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.util;

import java.lang.reflect.Method;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class DontInvokeMethod implements MethodInvoker {
    public void invoke(Object proxy, Method method) throws Throwable {
    }
}
