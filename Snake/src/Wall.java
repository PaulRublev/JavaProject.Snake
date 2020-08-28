import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

class Wall extends AnyObject {
	
	private static final long serialVersionUID = 1L;
	private Config config;
	
	Wall(Point coordinates, Dimension size, Config config) {
		this.config = config;
		setLocation(coordinates);
		setSize(size);
		setView();
	}
	
	private void setView() {
		switch (config.getViewConfiguration()) {
		case DEFAULT:
			setOpaque(true);
			setBackground(Color.BLACK);
			break;
		case ONE:
			setOpaque(true);
			setBackground(Color.LIGHT_GRAY);
			setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			break;
		case TWO:
			
			break;
		}
	}
}