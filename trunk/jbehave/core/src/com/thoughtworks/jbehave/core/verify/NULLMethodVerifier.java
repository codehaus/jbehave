/*
 * Created on 17-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.verify;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.MethodListener;


class NULLMethodVerifier implements MethodVerifier {
    public Result verifyMethod(MethodListener listener, Method method, Object instance) {
        return new Result(method.getDeclaringClass().getName(), method.getName());
    }
}