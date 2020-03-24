import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

public class Food extends AnyObject {
	
	private static final long serialVersionUID = 1L;
	
	Food(JComponent field) {
		makeFood(field);
		field.add(this);
	}
	
	void makeFood(JComponent field) {
		int x;
		int y;
		do {	// Generate free random coordinates for food
			x = ((int)(Math.random() * (field.getWidth() - 2 * AnyObject.defaultThickness) + AnyObject.defaultThickness) / 
					AnyObject.defaultThickness) * AnyObject.defaultThickness;
			y = ((int)(Math.random() * (field.getHeight() - 2 * AnyObject.defaultThickness) + AnyObject.defaultThickness) / 
					AnyObject.defaultThickness) * AnyObject.defaultThickness;
		} while (!field.findComponentAt(new Point(x, y)).getClass().equals(field.getClass()));
		Border foodBorder = BorderFactory.createLineBorder(Color.black, 2);
		Point foodLocation = new Point(x, y);
		Dimension foodSize = new Dimension(AnyObject.defaultThickness, AnyObject.defaultThickness);
		setBorder(foodBorder);
		setLocation(foodLocation);
		setSize(foodSize);
	}
	
}