/*
 * Created on 29-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.invokers;

import com.thoughtworks.jbehave.core.BehaviourMethod;
import com.thoughtworks.jbehave.core.BehaviourMethodResult;
import com.thoughtworks.jbehave.core.Result;
import com.thoughtworks.jbehave.core.MethodInvoker;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class DontInvokeMethod implements MethodInvoker {
    public Result invoke(BehaviourMethod behaviourMethod) {
        return new BehaviourMethodResult(behaviourMethod);
    }
}
