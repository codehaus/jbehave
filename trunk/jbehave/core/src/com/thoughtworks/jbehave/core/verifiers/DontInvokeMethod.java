/*
 * Created on 29-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core.verifiers;

import com.thoughtworks.jbehave.core.Result;
import com.thoughtworks.jbehave.core.Verifiable;
import com.thoughtworks.jbehave.core.Verifier;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class DontInvokeMethod implements Verifier {
    public Result verify(Verifiable verifiable) {
        return new Result("", Result.SUCCEEDED);
    }
}
