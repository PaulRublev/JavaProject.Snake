import java.awt.Dimension;
import java.awt.Point;

class Food extends AnyObject {
	
	private static final long serialVersionUID = 1L;
	
	Food(Point coordinates) {
		makeFood(coordinates);
	}
	
	public void makeFood(Point coordinates) {
		Point foodLocation = coordinates;
		Dimension foodSize = new Dimension(AnyObject.defaultThickness, AnyObject.defaultThickness);
		Config.getView(this);
		setLocation(foodLocation);
		setSize(foodSize);
	}
	
}