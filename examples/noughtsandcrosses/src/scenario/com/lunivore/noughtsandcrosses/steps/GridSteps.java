package com.lunivore.noughtsandcrosses.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;

import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.Then;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.steps.Steps;
import org.lunivore.tyburn.WindowControl;

import com.lunivore.noughtsandcrosses.NoughtsAndCrosses;
import com.lunivore.noughtsandcrosses.view.ComponentNames;

public class GridSteps extends Steps {

	public static String ROWS = "abc";
	public static String COLUMNS = "123";
	protected static final String NL = System.getProperty("line.separator");
	protected WindowControl windowControl;

	@Given("the game is running")
	public void givenTheGameIsRunning() {
		windowControl = new WindowControl(ComponentNames.NOUGHTSANDCROSSES);
		new NoughtsAndCrosses();
	}
	
	@Given("a grid that looks like $grid")
	public void givenThatTheGridLooksLike(String grid) throws Exception {
		givenTheGameIsRunning();
		ArrayList<String> oTurns = new ArrayList<String>();
		ArrayList<String> xTurns = new ArrayList<String>();
		
		captureMoves(oTurns, xTurns, grid);	
		performMoves(oTurns, xTurns);
	}
	
	@Then("the message should read \"$message\"")
	public void thenTheMessageShouldRead(String message) throws Exception {
		JLabel messageLabel = (JLabel) windowControl.findComponent(ComponentNames.MESSAGE);
		ensureThat(messageLabel.getText(), equalTo(message));
	}

	@Then("the grid should look like $grid")
	public void thenTheGridShouldLookLike(String grid) throws Exception {
		Component gridPanel = windowControl.findComponent(ComponentNames.GRID);
		ensureThat(gridPanel.toString(), equalTo(grid));
	}

	@When("the player clicks $space")
	public void whenPlayerClicksInSpace(String space) throws Exception {
		windowControl.clickButton(space);
	}
	
	private void performMoves(List<String> oTurns, List<String> xTurns) throws Exception {
		while (xTurns.size() > 0) {
			whenPlayerClicksInSpace(xTurns.remove(0));
			if (oTurns.size() >0) {
				whenPlayerClicksInSpace(oTurns.remove(0));
			}
		}
	}

	private void captureMoves(List<String> oTurns, List<String> xTurns, String grid) {

		List<String> lines = Arrays.asList(grid.split(NL));
		for(int row=0;row<3;row++) {
			for(int col=0;col<3;col++) {
				char player = lines.get(row).charAt(col);
				String spaceLabel = "" + ROWS.charAt(row) + COLUMNS.charAt(col);
				if(player == 'O') {oTurns.add(spaceLabel);}
				if(player == 'X') {xTurns.add(spaceLabel);}
			}
		}
	}
}
