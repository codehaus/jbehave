/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.extensions.ant.listeners;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.MethodListener;
import com.thoughtworks.jbehave.core.verify.Result;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class AntListener implements MethodListener {
	private boolean failBuild;

    public void methodVerificationStarting(Method responsibilityMethod) {
    }

	public Result methodVerificationEnding(Result result, Object specInstance) {
		if (!result.succeeded()) {
			failBuild = true;
		}
		return result;
	}

	public boolean failBuild() {
		return failBuild;
	}
}
