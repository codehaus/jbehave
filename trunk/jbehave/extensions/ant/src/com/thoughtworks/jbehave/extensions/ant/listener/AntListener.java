/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.extensions.ant.listener;

import com.thoughtworks.jbehave.core.listener.ResultListener;
import com.thoughtworks.jbehave.core.result.Result;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class AntListener implements ResultListener {
	private boolean verificationFailed;
    private final ResultListener delegate;
    
    public AntListener(ResultListener delegate) {
        this.delegate = delegate;
    }

	public boolean verificationFailed() {
		return verificationFailed;
	}

    public void gotResult(Result result) {
        if (!result.succeeded()) {
            verificationFailed = true;
        }
        delegate.gotResult(result);
    }
}
