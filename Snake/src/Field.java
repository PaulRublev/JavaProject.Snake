import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JPanel;

class Field extends JComponent {
	
	private static final long serialVersionUID = 1L;
	
	Field(int x, int y, int width, int height, ScoreListener scoreListener) {
		setSize(width, height);
		setLocation(x, y);
		setLayout(null);
		
		new Wall(this);
		new Wall().makeWall(new Point(20, 20), new Dimension(10, 20), this);
		Food food = new Food(this);
		Snake snake = new Snake(this, scoreListener);
	}
	
}