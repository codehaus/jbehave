package jbehave.core;


import jbehave.core.mock.UsingConstraints;

import java.io.OutputStream;
import java.io.PrintStream;


/**
 * @author <a href="mailto:ekeogh@thoughtworks.com">Elizabeth Keogh</a>
 * @author Mauro Talevi
 */
public class RunBehaviour extends UsingConstraints {

	private static int runCount;
    private static String methodCalled;

    public static class RunnableBehaviour {
		public void shouldBeRunByRunClass() {
			runCount++;
		}
	}

    public static class RunnableBehaviourWithTwoMethods {
        public void shouldRunNumberOne() {
            runCount ++;
            methodCalled = "shouldRunNumberOne";
        }

        public void shouldRunNumberTwo() {
            runCount ++;
            methodCalled = "shouldRunNumberTwo";
        }
    }

    private PrintStream nullPrintStream = new PrintStream(new OutputStream() {
		public void write(int b) {
			// do nothing
		}
	});
	
	public void shouldRunClassFromArgumentSuccessfully() {
		runCount = 0;
		new Run(nullPrintStream).verifyBehaviour(RunnableBehaviour.class);
		Ensure.that(runCount == 1);
	}
	
	public void shouldRunTwoClassesFromArgumentsSuccessfully() {
		runCount = 0;
		Run run = new Run(nullPrintStream);
        run.verifyBehaviour(RunnableBehaviour.class);
        run.verifyBehaviour(RunnableBehaviour.class);
		Ensure.that(runCount == 2);
	}
    
    public void shouldRunClassNameFromArgumentSuccessfully() throws ClassNotFoundException {
        runCount = 0;
        new Run(nullPrintStream).verifyBehaviour(RunnableBehaviour.class.getName());
        Ensure.that(runCount == 1);
    }

    public void shouldRunClassNamePlusOneMethodSuccessfully() throws ClassNotFoundException {
        runCount = 0;
        Run run = new Run(nullPrintStream);
        run.verifyBehaviour(RunnableBehaviourWithTwoMethods.class.getName() + ":" + "shouldRunNumberOne");
        ensureThat(run.succeeded(), eq(true));
        ensureThat(runCount, eq(1));
        ensureThat(methodCalled, eq("shouldRunNumberOne"));
    }

    public void shouldRunClassNameUsingCustomClassLoader() throws ClassNotFoundException {
        runCount = 0;
        new Run(nullPrintStream, new CustomClassLoader(false)).verifyBehaviour(RunnableBehaviour.class.getName());
        Ensure.that(runCount == 1);
    }

    public void shouldFailToRunClassNameUsingInvalidClassLoader() throws ClassNotFoundException {
        try {
            new Run(nullPrintStream, new CustomClassLoader(true))
                    .verifyBehaviour(RunnableBehaviour.class.getName());
        } catch (ClassNotFoundException e) {
            Ensure.that(runCount == 0);
        }        
    }
    
    private static class CustomClassLoader extends ClassLoader {
        
        boolean invalid;
        
        public CustomClassLoader(boolean invalid) {
            this.invalid = invalid;
        }

        public Class findClass(String name) throws ClassNotFoundException{
            if ( invalid ){
                throw new ClassNotFoundException();
            }
            return Run.class.getClassLoader().loadClass(name);            
        }
    }
}
