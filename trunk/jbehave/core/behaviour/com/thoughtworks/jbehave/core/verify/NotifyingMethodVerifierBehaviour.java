package com.thoughtworks.jbehave.core.verify;

import java.lang.reflect.Method;

import com.thoughtworks.jbehave.core.MethodListener;

/**
 * @author <a href="mailto:dguy@thoughtworks.com">Damian Guy</a>
 *
 */
public class NotifyingMethodVerifierBehaviour {
    
    public static class StubListener implements MethodListener {
        
        private Result result;

        public StubListener(Result result) {
            this.result = result;
        }

        public void methodVerificationStarting(Method method) {
        }
        
        public Result methodVerificationEnding(Result r,
                Object behaviourClassInstance) {
            return this.result;
        }
}
    
    public void shouldReturnResultFromListener() throws Exception {
        // setup
        NotifyingMethodVerifier verifier = new NotifyingMethodVerifier();
        Result result = new Result("fake", "fake", null);
        Method method = getClass().getMethods()[0];
        
        // execute
        Result returned = verifier.verifyMethod(new StubListener(result), method, null);
    
        // verify
        Verify.that("should return result from listener", result == returned);
    }

}
