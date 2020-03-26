import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JComponent;

class Field extends JComponent {
	
	private static final long serialVersionUID = 1L;
	int maxRandomWallsQuantity = 5;
	int minRandomWallsQuantity = 2;
	int maxRandomWallSize = 4;
	int minRandomWallSize = 1;
	Wall upWall;
	Wall downWall;
	Wall leftWall;
	Wall rightWall;
	Food food;
	Snake snake;
	
	Field(int width, int height, ScoreListener scoreListener) {
		setSize(width, height);
		
		Point upWallCoordinates = new Point(0, 0);
		Dimension upNDownWallSize = new Dimension(width, AnyObject.defaultThickness);
		Point downWallCoordinates = new Point(0, height - AnyObject.defaultThickness);
		Point leftWallCoordinates = new Point(0, AnyObject.defaultThickness);
		Dimension leftNRightWallSize = new Dimension(AnyObject.defaultThickness,
				height - 2 * AnyObject.defaultThickness);
		Point rightWallCoordinates = new Point(width - AnyObject.defaultThickness, AnyObject.defaultThickness);
		
		upWall = new Wall(upWallCoordinates, upNDownWallSize);
		add(upWall);
		downWall = new Wall(downWallCoordinates, upNDownWallSize);
		add(downWall);
		leftWall = new Wall(leftWallCoordinates, leftNRightWallSize);
		add(leftWall);
		rightWall = new Wall(rightWallCoordinates, leftNRightWallSize);
		add(rightWall);
		
		Wall randomWall;
		Dimension randomWallSize;
		Point randomWallCoordinates;
		int leftX = (getWidth() / 2 / AnyObject.defaultThickness) * AnyObject.defaultThickness;
		int upY = (getHeight() / 2 / AnyObject.defaultThickness) * AnyObject.defaultThickness;
		int rightX = leftX;
		int downY = upY + 3;
		int wallLeftX;
		int wallUpY;
		int wallRightX;
		int wallDownY;
		boolean crossedX = false;
		boolean crossedY = false;
		int randomWallsQuantity = (int) (Math.random() * (maxRandomWallsQuantity - minRandomWallsQuantity + 1)
				+ minRandomWallsQuantity);
		for (int i = 0; i < randomWallsQuantity; i++) {
			randomWallSize = generateSize();
			do {
				randomWallCoordinates = generateCoordinates();
				wallLeftX = randomWallCoordinates.x;
				wallUpY = randomWallCoordinates.y;
				wallRightX = randomWallCoordinates.x + randomWallSize.width;
				wallDownY = randomWallCoordinates.y + randomWallSize.height;
				if (wallLeftX <= rightX && wallLeftX >= leftX 
						|| wallRightX <= rightX && wallRightX >= leftX
						|| wallLeftX <= leftX && wallRightX >= rightX) {
					crossedX = true;
				}
				if (wallUpY <= downY && wallUpY >= upY 
						|| wallDownY <= downY && wallDownY >= upY
						|| wallUpY <= upY && wallDownY >= downY) {
					crossedY = true;
				}
			} while (randomWallCoordinates.x + randomWallSize.width > width ||
					randomWallCoordinates.y + randomWallSize.height > height ||
					crossedX && crossedY);
			randomWall = new Wall(randomWallCoordinates, randomWallSize);
			add(randomWall);
		}
		//snake = new Snake(this, scoreListener);
		food = new Food(generateCoordinates());
		add(food);
	}
	
	Point generateCoordinates() {
		int x;
		int y;
		do {
			x = ((int)(Math.random() * (getWidth() - 2 * AnyObject.defaultThickness) + AnyObject.defaultThickness) / 
					AnyObject.defaultThickness) * AnyObject.defaultThickness;
			y = ((int)(Math.random() * (getHeight() - 2 * AnyObject.defaultThickness) + AnyObject.defaultThickness) / 
					AnyObject.defaultThickness) * AnyObject.defaultThickness;
		} while (!findComponentAt(new Point(x, y)).getClass().equals(getClass()));
		return new Point(x, y);
	}
	
	Dimension generateSize() {
		int width = (int) (Math.random() * (maxRandomWallSize - minRandomWallSize + 1) + minRandomWallSize)
				* AnyObject.defaultThickness;
		int height  = (int) (Math.random() * (maxRandomWallSize - minRandomWallSize + 1) + minRandomWallSize)
				* AnyObject.defaultThickness;
		return new Dimension(width, height);
	}
}