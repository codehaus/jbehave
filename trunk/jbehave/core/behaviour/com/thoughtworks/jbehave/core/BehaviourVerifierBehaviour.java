/*
 * Created on 29-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourVerifierBehaviour {
    private final static List sequenceOfEvents = new ArrayList(); // poor man's mock
    private BehaviourVerifier verifier;
    
    public void setUp() {
        sequenceOfEvents.clear();
        verifier = new BehaviourVerifier();
    }
    
    private static class StoresNotifications implements BehaviourListener {
        private final Behaviour expectedBehaviour;
        private final Result expectedResult;

        private StoresNotifications(Behaviour expectedBehaviour, Result expectedResult) {
            this.expectedBehaviour = expectedBehaviour;
            this.expectedResult = expectedResult;
        }

        public void behaviourVerificationStarting(Behaviour behaviour) {
            Verify.sameInstance(expectedBehaviour, behaviour);
            sequenceOfEvents.add("behaviourVerificationStarting");
        }

        public Result behaviourVerificationEnding(Result result, Behaviour behaviour) {
            Verify.sameInstance(expectedBehaviour, behaviour);
            Verify.sameInstance(expectedResult, result);
            sequenceOfEvents.add("behaviourVerificationEnding");
            return result;
        }

        public boolean caresAbout(Behaviour behaviour) {
            return true;
        }
    }

    private static class LogsCallToVerify implements Behaviour {
        private final Result resultToReturn;
        
        public LogsCallToVerify(Result resultToReturn) {
            this.resultToReturn = resultToReturn;
        }
        
        public Result verify() throws Exception {
            sequenceOfEvents.add("verify");
            return resultToReturn;
        }
    }

    public void shouldNotifyListenersBeforeAndAfterVerification() throws Exception {
        // setup
        Result result = new Result("name", Result.SUCCEEDED);
        final Behaviour behaviour = new LogsCallToVerify(result);
        BehaviourListener listener = new StoresNotifications(behaviour, result);
        verifier.registerListener(listener);
        
        Verify.pending("Verify multiple listeners");
        
        // execute
        verifier.verify(behaviour);
        
        // verify
        List expectedSequenceOfEvents = Arrays.asList(
                new String[]{"behaviourVerificationStarting", "verify", "behaviourVerificationEnding"}
        );
        Verify.equal(expectedSequenceOfEvents, sequenceOfEvents);
    }

}
