/*
 * Created on 23-Nov-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.listener;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jbehave.core.behaviour.Behaviour;
import org.jbehave.core.result.Result;
import org.jbehave.core.util.CamelCaseConverter;
import org.jbehave.core.util.Timer;



/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class PlainTextListener implements BehaviourListener {

    private static final String NL = System.getProperty("line.separator");
    protected final PrintWriter out;
    private int methodsVerified = 0;
    private final List failures = new ArrayList();
    private final List pending = new ArrayList();
    protected final Timer timer;

    public PlainTextListener(Writer writer, Timer timer) {
        out = new PrintWriter(writer);
        this.timer = timer;
        timer.start();
    }

    public void gotResult(Result result) {
        methodsVerified++;
        if (result.failed()) {
            failures.add(result);
        }
        else if (result.isPending()) {
            pending.add(result);
        }
        out.print(result.status().symbol());
        out.flush();
    }

    public void printReport() {
        timer.stop();
        out.println();
        printElapsedTime();
        printDetails();
        printSummaryCounts();
        out.flush();
    }

    private void printElapsedTime() {
        out.println("Time: " + timer.elapsedTimeMillis()/1000.0 + "s\n");
    }

    protected void printDetails() {
        printFailures();
        printPending();
    }

    private void printFailures() {
        if (!failures.isEmpty()) {
            out.println("Failures: " + failures.size() + ".");
            out.println();
            int count = 1;
            for (Iterator i = failures.iterator(); i.hasNext(); count++) {
                Result result = (Result) i.next();
                printResult(count, result);
                result.cause().printStackTrace(out);
                out.println();
            }
        }
    }

    private void printPending() {
        if (!pending.isEmpty()) {
            out.println("Pending: " + pending.size() + ".");
            out.println();
            int count = 1;
            for (Iterator i = pending.iterator(); i.hasNext(); count++) {
                Result result =  (Result) i.next();
                printResult(count, result);
                out.println("\t" + result.cause().getMessage());
                StackTraceElement element = findFirstNonJBehaveStackElement(result.cause());
                if (element.getLineNumber() >= 0) {
                    out.println("\tat " + element.toString() + NL);
                }
                out.println();
            }
        }
    }

    protected void printResult(int count, Result result) {
        String containerName = result.containerName();
        String shortName = containerName.substring(containerName.lastIndexOf('.') + 1);
        shortName = shortName.substring(shortName.lastIndexOf('$') + 1);
        if (shortName.endsWith("Behaviour")) {
            shortName = shortName.substring(0, shortName.length() - "Behaviour".length());
        }
        out.println(count + ") " + shortName + " " + new CamelCaseConverter(result.name()).toPhrase() + ":");
    }

    private StackTraceElement findFirstNonJBehaveStackElement(Throwable throwable) {
        StackTraceElement[] elements = throwable.getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            StackTraceElement element = elements[i];
            String className = element.getClassName();
            if (!className.startsWith("org.jbehave.")) {
                return elements[i];
            }
        }
        return null;
    }

    private void printSummaryCounts() {
        out.print("Total: " + methodsVerified + ".");
        if (pending.size() > 0) {
            out.print(" Pending: " + pending.size() + ".");
        }
        if (failures.size() > 0) {
            out.print(" Failures: " + failures.size() + ".");
        }
        else {
            out.print(" Success!");
        }
        out.println();
    }

    public boolean hasBehaviourFailures() {
        return !failures.isEmpty();
    }

    public void before(Behaviour behaviour) {
    }

    public void after(Behaviour behaviour) {
    }
}
