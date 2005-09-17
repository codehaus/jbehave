package com.thoughtworks.jbehave.extensions.harness;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.minimock.Mock;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;
import com.thoughtworks.jbehave.extensions.harness.time.TimeoutException;

public class WindowGrabberBehaviour extends UsingMiniMock {

    private static final String FRAME_NAME = "frame.name";
    
    public void shouldAddNewWindowsToAndRemoveNewWindowsFromMiniMap() {
        Mock miniMapMock = mock(QueuedMiniMap.class);
        WindowGrabber grabber = new WindowGrabber((QueuedMiniMap)miniMapMock);
        
        JFrame frame = new JFrame();
        frame.setName(FRAME_NAME);
        miniMapMock.expects("put").with(FRAME_NAME, frame);
        frame.setVisible(true);
                
        miniMapMock.expects("remove").with(FRAME_NAME);
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
        
        miniMapMock.expects("get").with(same(FRAME_NAME), eq(WindowGrabber.DEFAULT_WINDOW_TIMEOUT));
        grabber.getWindow(FRAME_NAME);
        
        verifyMocks();
        grabber.dispose();
    }
}
