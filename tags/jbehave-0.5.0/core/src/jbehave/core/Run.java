package jbehave.core;


import java.io.PrintStream;
import java.io.PrintWriter;

import jbehave.core.behaviour.BehaviourClass;
import jbehave.core.behaviour.BehaviourMethodVerifier;
import jbehave.core.exception.JBehaveFrameworkError;
import jbehave.core.listener.PlainTextMethodListener;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Run {
    public static void main(String[] args) {
    	runBehaviourToStream(args, System.out);
    }
    
    public static void runBehaviourToStream(String[] args, PrintStream printStream) {
    	boolean behavesAsExpected = true;
    	for (int i = 0; i < args.length; i++) {
	        try {
				PlainTextMethodListener textListener = runBehaviour(args[i], printStream);
	            textListener.printReport();
	            if (textListener.hasBehaviourFailures()) {
	                behavesAsExpected = false;
	            }
	        } catch (Exception e) {
	            System.err.println("Problem verifying behaviour class " + args[0]);
	            e.printStackTrace();
	            System.exit(1);
	        } catch (JBehaveFrameworkError e) {
	            System.err.println("Problem verifying behaviour class " + args[0]);
	            e.printStackTrace();
	            System.exit(1);
	        }
    	}
    	if (!behavesAsExpected) {System.exit(1);}
    }

	private static PlainTextMethodListener runBehaviour(String behaviourClassName, PrintStream printStream) throws ClassNotFoundException {
		Class classToVerify = Class.forName(behaviourClassName);
		BehaviourClass visitableClass = new BehaviourClass(classToVerify);
		PlainTextMethodListener textListener = new PlainTextMethodListener(new PrintWriter(printStream));
		BehaviourMethodVerifier verifier = new BehaviourMethodVerifier();
		verifier.addListener(textListener);
		visitableClass.accept(verifier);
		return textListener;
	}
}
