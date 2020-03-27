import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

class Wall extends AnyObject {
	
	private static final long serialVersionUID = 1L;
	private Color color = Color.BLACK;
	
	Wall(Point coordinates, Dimension size) {
		setLocation(coordinates);
		setSize(size);
		setOpaque(true);
		setBackground(color);
	}
	
}