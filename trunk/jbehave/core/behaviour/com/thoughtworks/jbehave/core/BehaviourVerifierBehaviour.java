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

import com.thoughtworks.jbehave.core.verify.BehaviourVerifier;
import com.thoughtworks.jbehave.core.verify.Result;
import com.thoughtworks.jbehave.core.verify.Verify;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourVerifierBehaviour {
    private final static List sequenceOfEvents = new ArrayList(); // poor man's mock
    
    public void setUp() {
        sequenceOfEvents.clear();
    }
    
    private static class StoresNotifications implements BehaviourListener {
        private final Behaviour expectedBehaviourInstance;

        private StoresNotifications(Behaviour behaviour) {
            this.expectedBehaviourInstance = behaviour;
        }

        public void behaviourVerificationStarting(Behaviour behaviour) {
            Verify.sameInstance(expectedBehaviourInstance, behaviour);
            sequenceOfEvents.add("behaviourVerificationStarting");
        }

        public Result behaviourVerificationEnding(Result result, Behaviour behaviour) {
            sequenceOfEvents.add("behaviourVerificationEnding");
            return result;
        }

        public boolean caresAbout(Behaviour behaviour) {
            return true;
        }
    }

    private static class LogsCallToVerify implements Behaviour {
        public Result verify() throws Exception {
            sequenceOfEvents.add("verify");
            return null;
        }
    }

    public void shouldNotifyListenerBeforeAndAfterVerification() throws Exception {
        // setup
        final Behaviour behaviour = new LogsCallToVerify();
        BehaviourListener listener = new StoresNotifications(behaviour);
        
        // execute
        new BehaviourVerifier(listener).verify(behaviour);
        
        // verify
        List expectedSequenceOfEvents = Arrays.asList(
                new String[]{"behaviourVerificationStarting", "verify", "behaviourVerificationEnding"}
        );
        Verify.equal(expectedSequenceOfEvents, sequenceOfEvents);
    }

}
