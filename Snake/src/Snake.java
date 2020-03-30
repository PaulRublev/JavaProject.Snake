import java.awt.*;
import java.util.LinkedList;

class Snake {
	
	class SnakeBody extends AnyObject {
		private static final long serialVersionUID = 1L;
		
		SnakeBody() {
			Config.getView(this);
		}
	}
	class SnakeHead extends AnyObject {
		private static final long serialVersionUID = 1L;
		
		public SnakeHead() {
			Config.getView(this);
		}
	}

	public LinkedList<AnyObject> snakeBody;
	private Dimension bodySize = new Dimension(AnyObject.defaultThickness, AnyObject.defaultThickness);
	public boolean isFed = false;
	
	Snake(Point initCoordinates, Directions tailDirection) {
		snakeBody = new LinkedList<AnyObject>();
		
		switch (tailDirection) {
		case UP:
			for (int i = 0; i < 3; i++) {
				addSnakePart(new Point(initCoordinates.x, initCoordinates.y - AnyObject.defaultThickness * i));
			}
			break;
		case DOWN:
			for (int i = 0; i < 3; i++) {
				addSnakePart(new Point(initCoordinates.x, initCoordinates.y + AnyObject.defaultThickness * i));
			}
			break;
		case LEFT:
			for (int i = 0; i < 3; i++) {
				addSnakePart(new Point(initCoordinates.x - AnyObject.defaultThickness * i, initCoordinates.y));
			}
			break;
		case RIGHT:
			for (int i = 0; i < 3; i++) {
				addSnakePart(new Point(initCoordinates.x + AnyObject.defaultThickness * i, initCoordinates.y));
			}
			break;
		}
		
	}
	
	public void addSnakePart(Point location) {
		AnyObject body;
		if (snakeBody.size() == 0) {
			body = new SnakeHead();
		} else {
			body = new SnakeBody();
		}
		body.setLocation(location);
		body.setSize(bodySize);
		snakeBody.add(body);
	}
	
}