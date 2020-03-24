import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JComponent;

class Field extends JComponent {
	
	private static final long serialVersionUID = 1L;
	
	Field(int width, int height) {
		setSize(width, height);
		setLayout(null);
		
		new Wall(this);
		new Wall().makeWall(new Point(20, 20), new Dimension(10, 20), this);
		Food food = new Food(this);
		Snake snake = new Snake(this);
	}
	
}