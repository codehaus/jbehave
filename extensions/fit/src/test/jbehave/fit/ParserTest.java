package jbehave.fit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;

import junit.framework.TestCase;
import fit.Parse;

public class ParserTest extends TestCase {

    public void testCanParseTablesToRetrieveNameOfFixture() throws Exception {
        Parse tables = new Parse(read(getReader("jbehave/fit/Fit.html")),
                new String[] { "html", "body", "table", "tr", "td" });
        Parse parts = tables.parts;
        Parse leaf = parts.leaf();
        StringWriter writer = new StringWriter();
        leaf.print(new PrintWriter(writer));
        assertTrue(writer.getBuffer().toString().trim().contains(TestFixture.class.getName()));
    }

    private Reader getReader(String resource) {
        return new InputStreamReader(this.getClass().getClassLoader()
                .getResourceAsStream(resource));
    }

    protected String read(Reader in) throws IOException {
        BufferedReader br = new BufferedReader(in);
        StringBuffer sb = new StringBuffer();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            line = br.readLine();
        }
        in.close();
        return sb.toString();
    }
}
