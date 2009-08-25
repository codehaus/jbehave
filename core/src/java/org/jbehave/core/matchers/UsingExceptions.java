package org.jbehave.core.matchers;

import org.jbehave.core.Block;
import org.jbehave.core.exception.PendingException;
import org.jbehave.core.exception.VerificationException;

public class UsingExceptions {

    public static Exception runAndCatch(Class exceptionType, Block block) throws Exception {
        try {
            block.run();
        }
        catch (Exception e) {
            if (exceptionType.isAssignableFrom(e.getClass())) {
                return e;
            } else {
                throw e;
            }
        }
        return null;
    }

    public static void fail(String message) {
        throw new VerificationException(message);
    }

    public static void fail(String message, Exception e) {
        throw new VerificationException(message, e);
    }

    public static void fail(String message, Object expected, Object actual) {
        throw new VerificationException(message, expected, actual);
    }

    public static void pending(String message) {
        throw new PendingException(message);
    }

    public static void pending() {
        pending("TODO");
    }

    public static void todo() {
        pending();
    }

    public static void todo(String message) {
        pending(message);
    }

}
