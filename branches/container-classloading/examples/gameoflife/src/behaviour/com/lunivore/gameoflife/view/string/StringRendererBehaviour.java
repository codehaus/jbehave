package com.lunivore.gameoflife.view.string;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.util.JUnit4Ensure.ensureThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import org.junit.Test;

import com.lunivore.gameoflife.domain.Grid;

public class StringRendererBehaviour {

    private static final String NL = System.getProperty("line.separator");

    @Test
    public void shouldRenderCellsAsCrosses() {
        StringRenderer renderer = new StringRenderer();
        Grid grid = mock(Grid.class);
        stub(grid.getWidth()).toReturn(5);
        stub(grid.getHeight()).toReturn(6);
        stub(grid.hasLife(3, 4)).toReturn(true);
        renderer.gridChanged(grid);
        
        ensureThat(renderer.asString(), equalTo(
                "....." + NL +
                "....." + NL +
                "....." + NL +
                "....." + NL +
                "...X." + NL +
                "....."
                ));
    }
    
}
