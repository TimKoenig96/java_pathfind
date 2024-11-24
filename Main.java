public class Main {
	public static void main(String[] args) {

		// Variables to configure
		int x = 10;							// x = Columns = Horizontal
		int y = 18;							// y = Rows    = Vertical
		int cell_size = 55;				// Pixel size of single cell
		int delay = 1000;					// Delay until program starts solving
		int time_between = 100;			// Time between each step
		double wall_ratio = 0.2;		// Wall to free tile ratio
		boolean do_flashing = true;	// Flashing animation

		// Create grid to solve, creates one JPanel for each cell
		Grid grid = new Grid(x, y, wall_ratio);

		// Create the GUI, sets defaults and uses JPanel cells from grid
		new Frame(cell_size, grid);

		// Solve the grid
		grid.findPath(delay, time_between, do_flashing);
	}
}
