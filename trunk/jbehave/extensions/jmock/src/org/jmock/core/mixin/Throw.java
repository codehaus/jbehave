/*
 * Created on 16-Mar-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package org.jmock.core.mixin;

import org.jmock.core.Stub;
import org.jmock.core.stub.ThrowStub;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class Throw {
    public static Stub exception( Throwable throwable ) {
        return new ThrowStub(throwable);
    }
}
