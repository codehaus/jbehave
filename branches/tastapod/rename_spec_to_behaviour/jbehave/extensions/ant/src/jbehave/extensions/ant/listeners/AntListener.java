/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.extensions.ant.listeners;

import jbehave.listeners.NullListener;
import jbehave.framework.CriteriaVerification;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class AntListener extends NullListener{
	private boolean failBuild;

	public CriteriaVerification criteriaVerificationEnding(CriteriaVerification verification, Object specInstance) {
		if (!verification.succeeded()) {
			failBuild = true;
		}
		return verification;
	}

	public boolean failBuild() {
		return failBuild;
	}


}
