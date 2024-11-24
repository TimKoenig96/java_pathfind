import java.awt.Color;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JPanel;

enum CellType {
	START(60, 200, 60, "Start"),
	FREE(255, 255, 255, " "),
	OBSTACLE(200, 60, 60, " "),
	QUEUED(190, 190, 190, "Queued"),
	CHECKING(60, 200, 170, "Checking"),
	CHECKED(110, 110, 110, "Checked"),
	END(60, 140, 200, "End"),
	PREPATH(155, 100, 70, "Pre-path"),
	PATH(220, 110, 30, "Path");

	final Color color;
	final String label;

	CellType(int red, int green, int blue, String label) {
		this.color = new Color(red, green, blue);
		this.label = label;
	}
}

public class Cell {
	CellType type;
	Cell parent;
	final JLabel label;
	final JPanel panel;
	final int x;
	final int y;

	Cell(CellType type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;

		// Create panel
		this.panel = new JPanel();

		// Set coordinates label
		this.panel.add(new JLabel("x" + x + " y" + y));

		// Set type label
		this.label = new JLabel(type.label);
		this.panel.add(this.label);

		// Set background color
		this.panel.setBackground(type.color);
	}

	public void setType(CellType new_type) {
		this.type = new_type;
		this.panel.setBackground(new_type.color);
		this.label.setText(new_type.label);
	}

	public void flashBackground(int duration) {
		Color flash_color = new Color(220, 220, 0);
		int flash_count = 2;
		
		for (int i = 0; i < flash_count * 2; i++) {

			if (i % 2 == 0) {
				this.panel.setBackground(flash_color);
			} else {
				this.panel.setBackground(this.type.color);
			}

			try {
				TimeUnit.MILLISECONDS.sleep((long) duration);
			} catch (InterruptedException e) {}
		}
	}
}
