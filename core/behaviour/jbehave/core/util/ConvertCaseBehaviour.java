/*
 * Created on 02-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.core.util;

import java.text.SimpleDateFormat;

import jbehave.core.minimock.UsingConstraints;


/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class ConvertCaseBehaviour extends UsingConstraints {
    
    public void shouldConvertStringToSeparateWords() throws Exception {
        // given...
        String string = "StringInCamelCase";

		// when...
        String result = new ConvertCase(string).toSeparateWords();
        
        // then...
        ensureThat(result, eq("string in camel case"));
    }
    
    public void shouldLeaveUppercaseWordsWhenConvertingToSeparateWords() throws Exception {
        // given...
        String string = "UPPERCASEAndASingleLetter";

        // when...
        String result = new ConvertCase(string).toSeparateWords();
        
        // then...
        ensureThat(result, eq("UPPERCASE and a single letter"));
    }
    
    public void shouldConvertRegularClassNameToSeparateWords() throws Exception {
        // given...
        Class type = SimpleDateFormat.class;

        // when...
        String result = new ConvertCase(type).toSeparateWords();
        
        // then...
        ensureThat(result, eq("simple date format"));
    }
    
    private static class InnerClass{}
    
    public void shouldConvertInnerPartOfInnerClassNameToSeparateWords() throws Exception {
        // given...
        Class type = InnerClass.class;

        // when...
        String result = new ConvertCase(type).toSeparateWords();
        
        // then...
        ensureThat(result, eq("inner class"));
    }
    
    public void shouldConvertClassNameOfArbitraryObjectToSeparateWords() throws Exception {
        // given...
        Object object = new InnerClass();

        // when...
        String result = new ConvertCase(object).toSeparateWords();
        
        // then...
        ensureThat(result, eq("inner class"));
    }
    
    public void shouldConvertLowercaseWordsToCamelCase() throws Exception {
        // given...
        String string = "the cat sat on the mat";

        // when...
        String result = new ConvertCase(string).toCamelCase();
        
        // then...
        ensureThat(result, eq("TheCatSatOnTheMat"));
    }
}
