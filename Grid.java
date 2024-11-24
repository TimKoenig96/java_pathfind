import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Grid {
	final int x; // Number of cells (horizontal)
	final int y; // Number of cells (vertical)
	final double obstacle_ratio;
	Cell[][] cells;

	Grid(int x, int y, double obstacle_ratio) {

		// Set instance variables
		this.x = x;
		this.y = y;
		this.obstacle_ratio = obstacle_ratio;

		// Create the grid
		cells = new Cell[y][x];
		this.createGrid();
	}

	// Create grid and cells
	private void createGrid() {

		// Iterate through rows and columns
		for (int row = 0; row < this.y; row++) {
			for (int col = 0; col < this.x; col++) {

				// Get a random double between 0 and 1
				double random = Math.random();

				// Instantiate cell, taking obstacle ratio into consideration
				cells[row][col] = new Cell((random < obstacle_ratio ? CellType.OBSTACLE : CellType.FREE), col, row);
			}
		}

		// Override start- and endpoint
		cells[0][0].setType(CellType.START);
		cells[this.y - 1][this.x - 1].setType(CellType.END);
	}

	// Find a path through the grid
	public void findPath(int delay, int time_between, boolean do_flashing) {
		Cell end_cell = null;

		// Create the queue and queue up the first cell
		ArrayList<Cell> queue = new ArrayList<Cell>();
		queue.add(this.cells[0][0]);

		// Start processing the queue until it is empty
		while (queue.size() > 0 && end_cell == null) {

			// Get currently processing cell
			Cell current_cell = queue.remove(0);
			CellType current_cell_type = current_cell.type;

			// Set current cell to checking
			current_cell.setType(CellType.CHECKING);

			// Get all possible children (neighboring cells)
			Cell[] children = this.getAllPossibleChildren(current_cell);

			// Iterate through all children
			for (Cell cell : children) {

				// Flash cell for visualizing
				if (do_flashing) cell.flashBackground(time_between);

				// Set cell parent
				cell.parent = current_cell;

				// Cell is end, set found to true
				if (cell.type == CellType.END) {
					end_cell = cell;
					break;
				}

				// Child cell is free, add to queue and set type
				if (cell.type != CellType.FREE) continue;

				// Add free cell to queue
				queue.add(cell);
				cell.setType(CellType.QUEUED);

				// Wait before next execution
				try {
					TimeUnit.MILLISECONDS.sleep((long) time_between);
				} catch (InterruptedException e) {}
			}

			// Set current cell to checked or back to original if start / end
			if (current_cell_type == CellType.START) current_cell.setType(CellType.START);
			else if (current_cell_type == CellType.END) current_cell.setType(CellType.END);
			else current_cell.setType(CellType.CHECKED);
		}

		// Loop ended, construct the path if the end cell was found
		if (end_cell != null) constructPath(end_cell, time_between);
	}

	// Construct the path to take
	private void constructPath(Cell end_cell, int time_between) {

		// Path as an array list and the current cell
		List<Cell> path = new ArrayList<Cell>();
		Cell current_cell = end_cell;

		// While the cell isn't batman
		while (current_cell.parent != null) {

			// Add current cell to the array list
			path.add(current_cell);

			// Set the current cells color
			current_cell.setType(CellType.PREPATH);

			// Update the current cell
			current_cell = current_cell.parent;

			// Wait a moment
			try {
				TimeUnit.MILLISECONDS.sleep((long) time_between);
			} catch (InterruptedException e) {}
		}

		// Reverse the path
		Collections.reverse(path);

		// Draw proper path
		for (Cell cell : path) {
			cell.setType(CellType.PATH);

			// Wait a moment
			try {
				TimeUnit.MILLISECONDS.sleep((long) time_between);
			} catch (InterruptedException e) {}
		}
	}

	// Get all neighboring children of specified cell if free or end
	private Cell[] getAllPossibleChildren(Cell cell) {

		// Get current cell X and Y
		int x = cell.x;
		int y = cell.y;

		// Get all children
		Cell[] children = new Cell[] {
			(y - 1 >= 0 ? this.cells[y - 1][x] : null),		// Up
			(y + 1 < this.y ? this.cells[y + 1][x] : null),	// Down
			(x - 1 >= 0 ? this.cells[y][x - 1] : null),		// Left
			(x + 1 < this.x ? this.cells[y][x + 1] : null)	// Right
		};

		// Filter out null values and undesireable cell types
		children = Arrays
			.stream(children)
			.filter(v -> v != null && (v.type == CellType.FREE || v.type == CellType.END))
			.toArray(Cell[]::new);

		// Return children
		return children;
	}
}
