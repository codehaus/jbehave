/*
 * Created on 29-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.invoker;

import com.thoughtworks.jbehave.core.behaviour.BehaviourMethod;
import com.thoughtworks.jbehave.core.result.BehaviourMethodResult;
import com.thoughtworks.jbehave.core.result.Result;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class DontInvokeMethod implements MethodInvoker {
    public Result invoke(BehaviourMethod behaviourMethod) {
        return new BehaviourMethodResult(behaviourMethod);
    }
}
