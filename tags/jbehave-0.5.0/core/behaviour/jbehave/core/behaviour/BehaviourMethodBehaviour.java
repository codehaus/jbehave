/*
 * Created on 05-Oct-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.behaviour;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jbehave.core.Ensure;
import jbehave.core.minimock.Mock;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.result.Result;
import jbehave.core.visitor.Visitor;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class BehaviourMethodBehaviour extends UsingMiniMock {
	
    public void shouldDispatchItselfToVisitor() throws Exception {
        // given...
        Mock visitor = mock(Visitor.class);
        BehaviourMethod behaviourMethod = new BehaviourMethod(null, (Method)null);
        
        // expect...
        visitor.expects("visitBehaviourMethod").with(behaviourMethod);
        
        // when...
        behaviourMethod.accept((Visitor) visitor);
    }

	public static class StoresInvocation {
        public boolean methodWasInvoked = false;
        public void shouldDoSomething() {
            methodWasInvoked = true;
        }
    }
	
    public void shouldInvokeMethod() throws Throwable {
        // given
		StoresInvocation instance = new StoresInvocation();
        BehaviourMethod behaviourMethod = new BehaviourMethod(instance, "shouldDoSomething");
        
        // when
        behaviourMethod.invoke();
        
        // then
        Ensure.that(instance.methodWasInvoked);
    }
    
    public static class HasSetUpAndTearDown {
        public final List whatHappened = new ArrayList();
        
        public void setUp() throws Exception {
            whatHappened.add("setUp");
        }
        public void verify() throws Exception {
            whatHappened.add("verify");
        }
        public void tearDown() throws Exception {
            whatHappened.add("tearDown");
        }
        public void shouldDoSomething() throws Exception {
            whatHappened.add("shouldDoSomething");
        }
    }
    
    public void shouldInvokeSetUpVerifyAndTearDownInTheCorrectSequence() throws Throwable {
        // given
        HasSetUpAndTearDown instance = new HasSetUpAndTearDown();
        BehaviourMethod behaviourMethod = new BehaviourMethod(instance, "shouldDoSomething");
        
        // expect
        List expected = Arrays.asList(new String[] {
                "setUp", "shouldDoSomething", "verify", "tearDown"
        });
        
        // when
        behaviourMethod.invoke();
        
        // then
        ensureThat(instance.whatHappened, eq(expected));
    }

    public static class CheckedException extends Exception {}
    
    public static class SetUpThrowsException extends HasSetUpAndTearDown {
        public void setUp() throws Exception {
            throw new CheckedException();
        }
    }
    
    public void shouldReportExceptionFromSetUp() throws Throwable {
        // given
        BehaviourMethod behaviourMethod =
			new BehaviourMethod(new SetUpThrowsException(), "shouldDoSomething");
        
        // when
        Result result = behaviourMethod.invoke();
        
        // then
        ensureThat(result.cause(), isA(CheckedException.class));
    }
    
    public static class MethodThrowsException extends HasSetUpAndTearDown {
        public void shouldDoSomething() throws Exception {
            throw new CheckedException();
        }
    }
    
    public void shouldReportExceptionFromMethod() throws Throwable {
        // given
        BehaviourMethod behaviourMethod =
			new BehaviourMethod(new MethodThrowsException(), "shouldDoSomething");
        
        // when
        Result result = behaviourMethod.invoke();
        
        // then
        ensureThat(result.cause(), isA(CheckedException.class));
    }
    
    public static class VerifyThrowsException extends HasSetUpAndTearDown {
        public void verify() throws Exception {
            throw new CheckedException();
        }
    }
    
    public void shouldReportExceptionFromVerify() throws Throwable {
        // given
        BehaviourMethod behaviourMethod =
			new BehaviourMethod(new VerifyThrowsException(), "shouldDoSomething");
        
        // when
        Result result = behaviourMethod.invoke();
        
        // then
        ensureThat(result.cause(), isA(CheckedException.class));
    }
    
    public static class TearDownThrowsException extends HasSetUpAndTearDown {
        public void tearDown() throws Exception {
            throw new CheckedException();
        }
    }
    
    public void shouldReportExceptionFromTearDown() throws Throwable {
        // given
        BehaviourMethod behaviourMethod =
			new BehaviourMethod(new TearDownThrowsException(), "shouldDoSomething");
        
        // when
        Result result = behaviourMethod.invoke();
        
        // then
        ensureThat(result.cause(), isA(CheckedException.class));
    }
    
    public static class MethodAndVerifyBothThrowException extends HasSetUpAndTearDown {
        public void verify() throws Exception {
            throw new Exception("from verify");
        }
        public void shouldDoSomething() throws Exception {
            throw new Exception("from method");
        }
    }
    
    public void shouldReportExceptionFromMethodIfMethodAndVerifyBothThrowException() throws Throwable {
        // given
        BehaviourMethod behaviourMethod =
			new BehaviourMethod(new MethodAndVerifyBothThrowException(), "shouldDoSomething");
        
        // when
        Result result = behaviourMethod.invoke();
        
        // then
        ensureThat(result.cause().getMessage(), eq("from method"));
    }
    
    public static class MethodAndTearDownBothThrowException extends HasSetUpAndTearDown {
        public void tearDown() throws Exception {
            throw new Exception("from tearDown");
        }
        public void shouldDoSomething() throws Exception {
            throw new Exception("from method");
        }
    }
    
    public void shouldReportExceptionFromMethodIfMethodAndTearDownBothThrowException() throws Throwable {
        // given
        BehaviourMethod behaviourMethod =
			new BehaviourMethod(new MethodAndTearDownBothThrowException(), "shouldDoSomething");
        
        // when
        Result result = behaviourMethod.invoke();
        
        // then
        ensureThat(result.cause().getMessage(), eq("from method"));
    }
    
    public static class VerifyAndTearDownBothThrowException extends HasSetUpAndTearDown {
        public void verify() throws Exception {
            throw new Exception("from verify");
        }
        public void tearDown() throws Exception {
            throw new Exception("from tearDown");
        }
        public void shouldDoSomething() throws Exception {
        }
    }
    
    public void shouldReportExceptionFromVerifyIfVerifyAndTearDownBothThrowException() throws Throwable {
        // given
        BehaviourMethod behaviourMethod =
			new BehaviourMethod(new VerifyAndTearDownBothThrowException(), "shouldDoSomething");
        
        // when
        Result result = behaviourMethod.invoke();
        
        // then
        ensureThat(result.cause().getMessage(), eq("from verify"));
    }
}
