/*
 * Created on 02-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.util;

import java.text.SimpleDateFormat;

import com.thoughtworks.jbehave.core.responsibility.Verify;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class CaseConverterBehaviour {
    
    private CaseConverter caseConverter;

    public void setUp() {
        caseConverter = new CaseConverter();
    }
    
    public void shouldConvertStringToSeparateWords() throws Exception {
        // given
        String string = "StringInCamelCase";

        String result = caseConverter.toSeparateWords(string);
        
        // then
        Verify.equal("string in camel case", result);
    }
    
    public void shouldLeaveUppercaseWordsWhenConvertingToSeparateWords() throws Exception {
        // given...
        String string = "UPPERCASEAndSomeOtherStuff";

        // when...
        String result = caseConverter.toSeparateWords(string);
        
        // then...
        Verify.equal("UPPERCASE and some other stuff", result);
    }
    
    public void shouldConvertRegularClassNameToSeparateWords() throws Exception {
        // given...
        Class type = SimpleDateFormat.class;

        // when...
        String result = caseConverter.toSeparateWords(type);
        
        // then...
        Verify.equal("simple date format", result);
    }
    
    private static class InnerClass{}
    
    public void shouldConvertInnerPartOfInnerClassNameToSeparateWords() throws Exception {
        // given...
        Class type = InnerClass.class;

        // when...
        String result = caseConverter.toSeparateWords(type);
        
        // then...
        Verify.equal("inner class", result);
    }
    
    public void shouldConvertClassNameOfArbitraryObjectToSeparateWords() throws Exception {
        // given...
        Object object = new InnerClass();

        // when...
        String result = caseConverter.toSeparateWords(object);
        
        // then...
        Verify.equal("inner class", result);
    }
    
    public void shouldConvertLowercaseWordsToCamelCase() throws Exception {
        // given...
        String string = "the cat sat on the mat";

        // when...
        String result = caseConverter.toCamelCase(string);
        
        // then...
        Verify.equal("TheCatSatOnTheMat", result);
    }
}
