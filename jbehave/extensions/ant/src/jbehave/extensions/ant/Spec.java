/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.extensions.ant;


/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class Spec {
	private String specName;

	public void setSpecName(String specName) {
		this.specName = specName;

	}

	public String getSpecName() {
		return specName;
	}
}
