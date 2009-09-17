/*
 * Copyright (c) terms as published in http://waffle.codehaus.org/license.html
 */
package org.jbehave.scenario.i18n;

import java.util.ListResourceBundle;

/**
 * Empty resource bundle, used as <a
 * href="http://en.wikipedia.org/wiki/Null_Object_pattern">Null-Object</a> when
 * no resource bundle is found.
 * 
 * @author Mauro Talevi
 */
class EmptyResourceBundle extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] { { "", "" } };
    }
}
