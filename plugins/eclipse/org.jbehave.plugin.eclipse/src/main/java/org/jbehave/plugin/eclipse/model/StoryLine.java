package org.jbehave.plugin.eclipse.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class StoryLine {
    /** represents a parsed line of a story. Instances are returned by #parseLine */


    static List keyWords=Arrays.asList(new String[]{"title:","scenario:","when","then","given"});
	List words;
	private String keyWord;
	private StoryLine(String keyWord, List words) {
		this.words=words;
		this.keyWord=keyWord;
	}
	
	

	
	public String getKeyWord() {
		return keyWord;
	}




	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}



    /** returns null if line is not valid story line*/

    public static  StoryLine parseLine(String line) {
		line=line.replaceAll("\n", "");
		line=line.replaceAll("\r", "");
		List words=new ArrayList(Arrays.asList(line.split(" ")));
		if (words.size()<2)
			return null;
		
		if (((String)words.get(0)).equalsIgnoreCase("and")){
			words.remove(0);
			if (words.size()<2)
				return null;
		}
		
		if (keyWords.contains(((String)words.get(0)).toLowerCase())){	
			String kw=(String) words.get(0);
			words.remove(0);
			return new StoryLine(kw,words);
			
		}
		return null;
		
	}

	
	public String asText(){
		StringBuffer returnValue=new StringBuffer();
		for (Iterator iter = words.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			returnValue.append(element);
			if (iter.hasNext())
				returnValue.append(' ');
		}
		return returnValue.toString();
	}
	
	public String asClassName() {
		String className="";
		for (Iterator iter = words.iterator(); iter.hasNext();) {
			String word = (String) iter.next();
			className+=capitalize(word);				
		}
		System.out.println("Class: '"+className+"'");
		return className;
	}
	
	 public static String capitalize(String str) {
	        int strLen;
	        if (str == null || (strLen = str.length()) == 0) {
	            return str;
	        }
	        return new StringBuffer(strLen)
	            .append(Character.toTitleCase(str.charAt(0)))
	            .append(str.substring(1))
	            .toString();
	    }
}
