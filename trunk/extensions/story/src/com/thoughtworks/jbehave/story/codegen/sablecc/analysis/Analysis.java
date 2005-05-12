/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.thoughtworks.jbehave.story.codegen.sablecc.analysis;

import com.thoughtworks.jbehave.story.codegen.sablecc.node.*;

public interface Analysis extends Switch
{
    Object getIn(Node node);
    void setIn(Node node, Object in);
    Object getOut(Node node);
    void setOut(Node node, Object out);

    void caseStart(Start node);
    void caseAStory(AStory node);
    void caseATitle(ATitle node);
    void caseARole(ARole node);
    void caseAFeature(AFeature node);
    void caseABenefit(ABenefit node);
    void caseAScenario(AScenario node);
    void caseAScenarioTitle(AScenarioTitle node);
    void caseAPhrase(APhrase node);
    void caseAWordWordOrSpace(AWordWordOrSpace node);
    void caseASpaceWordOrSpace(ASpaceWordOrSpace node);

    void caseTTitleKeyword(TTitleKeyword node);
    void caseTScenarioKeyword(TScenarioKeyword node);
    void caseTAsA(TAsA node);
    void caseTIWant(TIWant node);
    void caseTSoThat(TSoThat node);
    void caseTSpace(TSpace node);
    void caseTWord(TWord node);
    void caseTEndl(TEndl node);
    void caseEOF(EOF node);
}
