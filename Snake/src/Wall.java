import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

public class Wall {
	
	class Barrier extends AnyObject {
		private static final long serialVersionUID = 1L;
	}
	
	Barrier upWall;
	Barrier downWall;
	Barrier leftWall;
	Barrier rightWall;
	Border 	border = BorderFactory.createLineBorder(Color.black, 4);
	
	Wall() {
		
	}
	Wall(JComponent field) {
		Dimension upNDownWallDimension = new Dimension(field.getWidth(), AnyObject.defaultThickness);
		Point upWallLocation = new Point(0, 0);
		upWall = makeWall(upWallLocation, upNDownWallDimension, field);
		field.add(upWall);
		Point downWallLocation = new Point(0, field.getHeight() - AnyObject.defaultThickness);
		downWall = makeWall(downWallLocation, upNDownWallDimension, field);
		field.add(downWall);
		Dimension leftNRightWallDimension = new Dimension(AnyObject.defaultThickness,
				field.getHeight() - 2 * AnyObject.defaultThickness);
		Point leftWallLocation = new Point(0, AnyObject.defaultThickness);
		leftWall = makeWall(leftWallLocation, leftNRightWallDimension, field);
		field.add(leftWall);
		Point rightWallLocation = new Point(field.getWidth() - AnyObject.defaultThickness, AnyObject.defaultThickness);
		rightWall = makeWall(rightWallLocation, leftNRightWallDimension, field);
		field.add(rightWall);
	}

	Barrier makeWall(Point coordinates, Dimension dimension, JComponent field) {
		Barrier someWall = new Barrier();
		someWall.setLocation(coordinates);
		someWall.setSize(dimension);
		someWall.setBorder(border);
		field.add(someWall);
		return someWall;
	}
	
}