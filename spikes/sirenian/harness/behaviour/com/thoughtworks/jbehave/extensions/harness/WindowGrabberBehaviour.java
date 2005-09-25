package com.thoughtworks.jbehave.extensions.harness;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.extensions.harness.time.TimeoutException;

public class WindowGrabberBehaviour extends UsingMiniMock {
    
    public void shouldAddNewWindowsToAndRemoveNewWindowsFromMiniMap() {
        Mock miniMapMock = mock(QueuedMiniMap.class);
        WindowGrabber grabber = new WindowGrabber((QueuedMiniMap)miniMapMock);
        
        JFrame frame = new JFrame();
        frame.setName("frame.name");
        miniMapMock.expects("put").with("frame.name", frame);
        frame.setVisible(true);
                
        miniMapMock.expects("remove").with("frame.name");
        frame.dispose();
        waitForIdle();
        verifyMocks();
        grabber.dispose();
    }
    
    private void waitForIdle() {
        try {
            // Ensures that window grabber gets close event before mocks verified
            SwingUtilities.invokeAndWait(new Runnable() { public void run() {}});
        } catch (Exception e) {
            Verify.that(false); // Should never happen
        }
    }

    public void shouldGetAnyWindowFromMiniMap() throws TimeoutException {
        Mock miniMapMock = mock(QueuedMiniMap.class);
        
        WindowGrabber grabber = new WindowGrabber((QueuedMiniMap)miniMapMock);
        
        miniMapMock.expects("get").with(same("frame.name"), eq(WindowGrabber.DEFAULT_WINDOW_TIMEOUT));
        grabber.getWindow("frame.name");
        
        verifyMocks();
        grabber.dispose();
    }
}
