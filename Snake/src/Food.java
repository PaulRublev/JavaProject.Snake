import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

class Food extends AnyObject {
	
	private static final long serialVersionUID = 1L;
	
	Food(Point coordinates) {
		makeFood(coordinates);
	}
	
	void makeFood(Point coordinates) {
		Border foodBorder = BorderFactory.createLineBorder(Color.black, 2);
		Point foodLocation = coordinates;
		Dimension foodSize = new Dimension(AnyObject.defaultThickness, AnyObject.defaultThickness);
		setBorder(foodBorder);
		setLocation(foodLocation);
		setSize(foodSize);
	}
	
}