import java.awt.Dimension;
import java.awt.Point;

class Wall extends AnyObject {
	
	private static final long serialVersionUID = 1L;
	
	Wall(Point coordinates, Dimension size) {
		setLocation(coordinates);
		setSize(size);
		Config.getView(this);
	}
	
}