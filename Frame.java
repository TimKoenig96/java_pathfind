import javax.swing.JFrame;

import java.awt.GridLayout;

public class Frame {
	private final JFrame frame;
	private final int x;
	private final int y;

	Frame(int cell_size, Grid grid) {
		this.x = grid.x;
		this.y = grid.y;

		// Create the frame
		this.frame = new JFrame("Pathfinder");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);
		this.frame.setSize(cell_size * this.x, cell_size * this.y);
		this.frame.setLayout(new GridLayout(this.y, this.x));

		// Set up the visual representation of the grid
		this.createVisualGrid(grid);
		this.frame.setVisible(true);
	}

	// Create a visual representation of a grid
	private void createVisualGrid(Grid grid) {

		// Add all tiles to the panel
		for (Cell[] row : grid.cells) {
			for (Cell cell : row) {
				this.frame.add(cell.panel);
			}
		}
	}
}
