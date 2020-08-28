import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

class Food extends AnyObject {
	
	private static final long serialVersionUID = 1L;
	private Config config;
	
	Food(Point coordinates, Config config) {
		this.config = config;
		makeFood(coordinates);
	}
	
	public void makeFood(Point coordinates) {
		Point foodLocation = coordinates;
		Dimension foodSize = new Dimension(AnyObject.defaultThickness, AnyObject.defaultThickness);
		setView();
		setLocation(foodLocation);
		setSize(foodSize);
	}
	
	private void setView() {
		switch (config.getViewConfiguration()) {
		case DEFAULT:
			setBorder(BorderFactory.createLineBorder(Color.black, 2));
			break;
		case ONE:
			setOpaque(true);
			setBackground(Color.WHITE);
			setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			break;
		case TWO:
			
			break;
		}
	}
}