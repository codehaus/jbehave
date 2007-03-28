package org.jbehave.threaded.swing;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Mock;
import org.jbehave.core.threaded.QueuedMiniMap;
import org.jbehave.core.threaded.TimeoutException;
import org.jbehave.threaded.swing.HeadlessChecker;
import org.jbehave.threaded.swing.WindowGrabber;



public class WindowGrabberBehaviour extends UsingMiniMock {
    

    public void shouldGetAnyWindowFromMiniMap() throws TimeoutException {
        checkForHeadless();
        Mock miniMapMock = mock(QueuedMiniMap.class);
        
        WindowGrabber grabber = new WindowGrabber((QueuedMiniMap)miniMapMock);
        
        miniMapMock.expects("get").with(eq("frame.name"), eq(WindowGrabber.DEFAULT_WINDOW_TIMEOUT));
        grabber.getWindow("frame.name");
        
        verifyMocks();
        grabber.dispose();
    }
    
    public void shouldAddNewWindowsToAndRemoveNewWindowsFromMiniMap() {
        checkForHeadless();
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
            throw new RuntimeException(e);
        }
    }

    
    private void checkForHeadless() {
        new HeadlessChecker().check();
    }
}
