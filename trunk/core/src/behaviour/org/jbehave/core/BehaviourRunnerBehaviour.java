package org.jbehave.core;

import java.io.IOException;
import java.io.Writer;

import org.jbehave.core.mock.UsingMatchers;

/**
 * @author <a href="mailto:ekeogh@thoughtworks.com">Elizabeth Keogh</a>
 * @author Mauro Talevi
 */
public class BehaviourRunnerBehaviour extends UsingMatchers {

    private static int runCount;
    private static String methodCalled;

    public void shouldRunClassFromArgumentSuccessfully() {
        runCount = 0;
        new BehaviourRunner(new NullWriter()).verifyBehaviour(RunnableBehaviour.class);
        Ensure.that(runCount == 1);
    }

    public void shouldRunTwoClassesFromArgumentsSuccessfully() {
        runCount = 0;
        BehaviourRunner run = new BehaviourRunner(new NullWriter());
        run.verifyBehaviour(RunnableBehaviour.class);
        run.verifyBehaviour(RunnableBehaviour.class);
        Ensure.that(runCount == 2);
    }

    public void shouldRunClassNameFromArgumentSuccessfully() throws ClassNotFoundException {
        runCount = 0;
        new BehaviourRunner(new NullWriter()).verifyBehaviour(RunnableBehaviour.class.getName());
        Ensure.that(runCount == 1);
    }

    public void shouldRunClassNamePlusOneMethodSuccessfully() throws ClassNotFoundException {
        runCount = 0;
        BehaviourRunner run = new BehaviourRunner(new NullWriter());
        run.verifyBehaviour(RunnableBehaviourWithTwoMethods.class.getName() + ":" + "shouldRunNumberOne");
        ensureThat(run.succeeded(), eq(true));
        ensureThat(runCount, eq(1));
        ensureThat(methodCalled, eq("shouldRunNumberOne"));
    }

    public void shouldRunClassNameUsingCustomClassLoader() throws ClassNotFoundException {
        runCount = 0;
        new BehaviourRunner(new NullWriter(), new CustomClassLoader(false))
                .verifyBehaviour(RunnableBehaviour.class.getName());
        Ensure.that(runCount == 1);
    }

    public void shouldFailToRunClassNameUsingInvalidClassLoader() throws ClassNotFoundException {
        try {
            new BehaviourRunner(new NullWriter(), new CustomClassLoader(true)).verifyBehaviour(RunnableBehaviour.class
                    .getName());
        } catch (ClassNotFoundException e) {
            Ensure.that(runCount == 0);
        }
    }

    public static class RunnableBehaviour {
        public void shouldBeRunByRunClass() {
            runCount++;
        }
    }

    public static class RunnableBehaviourWithTwoMethods {
        public void shouldRunNumberOne() {
            runCount++;
            methodCalled = "shouldRunNumberOne";
        }

        public void shouldRunNumberTwo() {
            runCount++;
            methodCalled = "shouldRunNumberTwo";
        }
    }


    private static class NullWriter extends Writer {
        public void close() throws IOException {
            // no-op
        }

        public void flush() throws IOException {
            // no-op
        }

        public void write(char[] cbuf, int off, int len) throws IOException {
            // no-op
        }
    };


    private static class CustomClassLoader extends ClassLoader {

        boolean invalid;

        public CustomClassLoader(boolean invalid) {
            this.invalid = invalid;
        }

        public Class findClass(String name) throws ClassNotFoundException {
            if (invalid) {
                throw new ClassNotFoundException();
            }
            return BehaviourRunner.class.getClassLoader().loadClass(name);
        }
    }
}
