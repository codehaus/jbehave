/*
 * Created on 25-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package spike;
import com.thoughtworks.jbehave.extensions.story.renderers.PlainTextRenderer;

import stories.UserWithdrawsCash;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Run {

    public static void main(String[] args) {
        
        PlainTextRenderer renderer = new PlainTextRenderer(System.out);
        new UserWithdrawsCash().accept(renderer);
    }
}
