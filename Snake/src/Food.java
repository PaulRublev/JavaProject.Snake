import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

class Food extends AnyObject {
	
	private static final long serialVersionUID = 1L;
	
	Food(JComponent field, Point coordinates) {
		makeFood(coordinates);
		field.add(this);
	}
	Food(JComponent field) {
		makeFood(generateCoordinates(field));
		field.add(this);
	}
	
	Point generateCoordinates(JComponent field) {
		int x;
		int y;
		do {	// Generate free random coordinates for food
			x = ((int)(Math.random() * (field.getWidth() - 2 * AnyObject.defaultThickness) + AnyObject.defaultThickness) / 
					AnyObject.defaultThickness) * AnyObject.defaultThickness;
			y = ((int)(Math.random() * (field.getHeight() - 2 * AnyObject.defaultThickness) + AnyObject.defaultThickness) / 
					AnyObject.defaultThickness) * AnyObject.defaultThickness;
		} while (!field.findComponentAt(new Point(x, y)).getClass().equals(field.getClass()));
		return new Point(x, y);
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