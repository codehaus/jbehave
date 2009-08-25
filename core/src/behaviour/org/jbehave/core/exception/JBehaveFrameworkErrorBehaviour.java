package org.jbehave.core.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.jbehave.core.Block;
import org.jbehave.core.mock.UsingMatchers;

public class JBehaveFrameworkErrorBehaviour extends UsingMatchers {
    

    public void shouldIncludeTheStackTraceOfCausingError() {
        ByteArrayOutputStream bufferForError = new ByteArrayOutputStream();
        PrintStream streamForError = new PrintStream(bufferForError);
        
        ByteArrayOutputStream bufferForOther = new ByteArrayOutputStream();
        PrintStream streamForOther = new PrintStream(bufferForOther);
        
        IllegalArgumentException cause = new IllegalArgumentException();
        JBehaveFrameworkError error = new JBehaveFrameworkError(cause);
        
        cause.printStackTrace(streamForOther);
        error.printStackTrace(streamForError);

        ensureThat(bufferForError, contains(JBehaveFrameworkError.class.getName()));
        ensureThat(bufferForError.toString(), contains(bufferForOther.toString()));
    }
    
    public void shouldPrintAStackTraceWithoutErrorsIfOnlyMessageProvided() {
        ByteArrayOutputStream bufferForError = new ByteArrayOutputStream();
        PrintStream streamForError = new PrintStream(bufferForError);
        
        ByteArrayOutputStream bufferForOther = new ByteArrayOutputStream();
        PrintStream streamForOther = new PrintStream(bufferForOther);
        
        Error errorWithExpectedStackTrace = new Error("A message");
        JBehaveFrameworkError error = new JBehaveFrameworkError("A message");
        
        errorWithExpectedStackTrace.printStackTrace(streamForOther);
        error.printStackTrace(streamForError);

        ensureThat(bufferForError, contains(JBehaveFrameworkError.class.getName()));
        ensureThat(bufferForError.toString(), contains("A message"));
    }
    
    public void shouldPrintAStackTraceWithoutErrorsOrMessageIfNoneProvided() {
        ByteArrayOutputStream bufferForError = new ByteArrayOutputStream();
        PrintStream streamForError = new PrintStream(bufferForError);
        
        JBehaveFrameworkError error = new JBehaveFrameworkError();
        
        error.printStackTrace(streamForError);
        
        ensureThat(bufferForError, contains(JBehaveFrameworkError.class.getName()));
    }
    
    public void shouldCopeWithNullArguments() throws Exception {
        final PrintStream aStream = new PrintStream(new ByteArrayOutputStream());
        
        Exception exception = runAndCatch(Exception.class, new Block() {
            public void run() throws Exception {
                new JBehaveFrameworkError(null, null).printStackTrace(aStream);
                new JBehaveFrameworkError((String)null).printStackTrace(aStream);
                new JBehaveFrameworkError((Throwable)null).printStackTrace(aStream);
            }
        });
        ensureThat(exception, isNull());
    }

}
