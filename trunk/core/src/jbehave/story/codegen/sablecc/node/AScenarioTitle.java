/* This file was generated by SableCC (http://www.sablecc.org/). */

package jbehave.story.codegen.sablecc.node;

import jbehave.story.codegen.sablecc.analysis.Analysis;

public final class AScenarioTitle extends PScenarioTitle
{
    private TScenarioKeyword _scenarioKeyword_;
    private TSpace _space_;
    private PPhrase _phrase_;
    private TEndl _endl_;

    public AScenarioTitle()
    {
    }

    public AScenarioTitle(
        TScenarioKeyword _scenarioKeyword_,
        TSpace _space_,
        PPhrase _phrase_,
        TEndl _endl_)
    {
        setScenarioKeyword(_scenarioKeyword_);

        setSpace(_space_);

        setPhrase(_phrase_);

        setEndl(_endl_);

    }
    public Object clone()
    {
        return new AScenarioTitle(
            (TScenarioKeyword) cloneNode(_scenarioKeyword_),
            (TSpace) cloneNode(_space_),
            (PPhrase) cloneNode(_phrase_),
            (TEndl) cloneNode(_endl_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAScenarioTitle(this);
    }

    public TScenarioKeyword getScenarioKeyword()
    {
        return _scenarioKeyword_;
    }

    public void setScenarioKeyword(TScenarioKeyword node)
    {
        if(_scenarioKeyword_ != null)
        {
            _scenarioKeyword_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _scenarioKeyword_ = node;
    }

    public TSpace getSpace()
    {
        return _space_;
    }

    public void setSpace(TSpace node)
    {
        if(_space_ != null)
        {
            _space_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _space_ = node;
    }

    public PPhrase getPhrase()
    {
        return _phrase_;
    }

    public void setPhrase(PPhrase node)
    {
        if(_phrase_ != null)
        {
            _phrase_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _phrase_ = node;
    }

    public TEndl getEndl()
    {
        return _endl_;
    }

    public void setEndl(TEndl node)
    {
        if(_endl_ != null)
        {
            _endl_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        _endl_ = node;
    }

    public String toString()
    {
        return ""
            + toString(_scenarioKeyword_)
            + toString(_space_)
            + toString(_phrase_)
            + toString(_endl_);
    }

    void removeChild(Node child)
    {
        if(_scenarioKeyword_ == child)
        {
            _scenarioKeyword_ = null;
            return;
        }

        if(_space_ == child)
        {
            _space_ = null;
            return;
        }

        if(_phrase_ == child)
        {
            _phrase_ = null;
            return;
        }

        if(_endl_ == child)
        {
            _endl_ = null;
            return;
        }

    }

    void replaceChild(Node oldChild, Node newChild)
    {
        if(_scenarioKeyword_ == oldChild)
        {
            setScenarioKeyword((TScenarioKeyword) newChild);
            return;
        }

        if(_space_ == oldChild)
        {
            setSpace((TSpace) newChild);
            return;
        }

        if(_phrase_ == oldChild)
        {
            setPhrase((PPhrase) newChild);
            return;
        }

        if(_endl_ == oldChild)
        {
            setEndl((TEndl) newChild);
            return;
        }

    }
}
