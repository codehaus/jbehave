/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.thoughtworks.jbehave.extensions.story.codegen.sablecc.generated.analysis;

import java.util.*;
import com.thoughtworks.jbehave.extensions.story.codegen.sablecc.generated.node.*;

public class AnalysisAdapter implements Analysis
{
    private Hashtable in;
    private Hashtable out;

    public Object getIn(Node node)
    {
        if(in == null)
        {
            return null;
        }

        return in.get(node);
    }

    public void setIn(Node node, Object in)
    {
        if(this.in == null)
        {
            this.in = new Hashtable(1);
        }

        if(in != null)
        {
            this.in.put(node, in);
        }
        else
        {
            this.in.remove(node);
        }
    }

    public Object getOut(Node node)
    {
        if(out == null)
        {
            return null;
        }

        return out.get(node);
    }

    public void setOut(Node node, Object out)
    {
        if(this.out == null)
        {
            this.out = new Hashtable(1);
        }

        if(out != null)
        {
            this.out.put(node, out);
        }
        else
        {
            this.out.remove(node);
        }
    }
    public void caseStart(Start node)
    {
        defaultCase(node);
    }

    public void caseAStory(AStory node)
    {
        defaultCase(node);
    }

    public void caseATitle(ATitle node)
    {
        defaultCase(node);
    }

    public void caseARole(ARole node)
    {
        defaultCase(node);
    }

    public void caseAFeature(AFeature node)
    {
        defaultCase(node);
    }

    public void caseABenefit(ABenefit node)
    {
        defaultCase(node);
    }

    public void caseAPhrase(APhrase node)
    {
        defaultCase(node);
    }

    public void caseAWordWordOrSpace(AWordWordOrSpace node)
    {
        defaultCase(node);
    }

    public void caseASpaceWordOrSpace(ASpaceWordOrSpace node)
    {
        defaultCase(node);
    }

    public void caseTTitleKeyword(TTitleKeyword node)
    {
        defaultCase(node);
    }

    public void caseTAsA(TAsA node)
    {
        defaultCase(node);
    }

    public void caseTIWant(TIWant node)
    {
        defaultCase(node);
    }

    public void caseTSoThat(TSoThat node)
    {
        defaultCase(node);
    }

    public void caseTSpace(TSpace node)
    {
        defaultCase(node);
    }

    public void caseTWord(TWord node)
    {
        defaultCase(node);
    }

    public void caseTEndl(TEndl node)
    {
        defaultCase(node);
    }

    public void caseEOF(EOF node)
    {
        defaultCase(node);
    }

    public void defaultCase(Node node)
    {
    }
}
