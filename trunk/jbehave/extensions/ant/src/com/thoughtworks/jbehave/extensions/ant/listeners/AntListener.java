/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.extensions.ant.listeners;

import com.thoughtworks.jbehave.core.responsibility.Result;
import com.thoughtworks.jbehave.listeners.ListenerSupport;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class AntListener extends ListenerSupport {
	private boolean failBuild;

	public Result responsibilityVerificationEnding(Result result, Object specInstance) {
		if (!result.succeeded()) {
			failBuild = true;
		}
		return result;
	}

	public boolean failBuild() {
		return failBuild;
	}


}
