package org.jbehave.threaded.swing;

import org.jbehave.core.Block;
import org.jbehave.core.exception.PendingException;
import org.jbehave.core.mock.UsingMatchers;

/**
 * Use this class at the beginning of any Swing behaviours which
 * shouldn't be run in Headless mode. Those behaviours will be
 * shown as pending instead (which won't break your automated
 * build).
 */
public class HeadlessCheckerBehaviour extends UsingMatchers {

    private String envHeadless;

    public void shouldThrowAPendingExceptionIfRunningInHeadlessMode() throws Exception {
        storeCurrentHeadlessMode();
        
        final HeadlessChecker headlessChecker = new HeadlessChecker();
        
        ensureThrowsExceptionOnHeadless(headlessChecker);
        ensureDoesNotThrowExceptionWhenNotHeadless(headlessChecker);
        
        resetOriginalHeadlessMode();
    }

    private void ensureDoesNotThrowExceptionWhenNotHeadless(final HeadlessChecker headlessChecker) throws Exception {
        System.getProperties().remove("java.awt.headless");
        ensureDoesNotThrowException(new Block() {
            public void run() throws Exception {
                headlessChecker.check();
            }
        });
    }

    private void ensureThrowsExceptionOnHeadless(final HeadlessChecker headlessChecker) throws Exception {
        System.setProperty("java.awt.headless", "true");
        ensureThrows(PendingException.class, new Block() {
            public void run() throws Exception {
                headlessChecker.check();
            }
        });
    }

    private void resetOriginalHeadlessMode() {
        if (envHeadless != null) {
            System.setProperty("java.awt.headless", envHeadless);
        }
    }

    private void storeCurrentHeadlessMode() {
        envHeadless = System.getProperty("java.awt.headless");
    }
}
