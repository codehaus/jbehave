package com.thoughtworks.jbehave.core.responsibility;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.ResponsibilityListener;

/**
 * @author <a href="mailto:dguy@thoughtworks.com">Damian Guy</a>
 *
 */
public class NotifyingResponsibilityVerifierBehaviour {
    
    public static class StubListener implements ResponsibilityListener {
        
        private Result result;

        public StubListener(Result result) {
            this.result = result;
        }

        public void responsibilityVerificationStarting(Method responsibilityMethod) {
        }
        
        public Result responsibilityVerificationEnding(Result r,
                Object behaviourClassInstance) {
            return this.result;
        }
}
    
    public void shouldReturnResultFromListener() throws Exception {
        // setup
        NotifyingResponsibilityVerifier verifier = new NotifyingResponsibilityVerifier();
        Result result = new Result("fake", "fake", null);
        Method method = getClass().getMethods()[0];
        
        // execute
        Result returned = verifier.verifyResponsibility(new StubListener(result), method, null);
    
        // verify
        Verify.that("should return result from listener", result == returned);
    }

}
