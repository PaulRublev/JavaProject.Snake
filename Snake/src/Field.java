import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.util.LinkedList;

import javax.swing.JComponent;

interface MoveListener {
	void snakeMove(Directions direction);
}

class Field extends JComponent implements MoveListener {
	
	private static final long serialVersionUID = 1L;
	private int maxRandomWallsQuantity = 5;
	private int minRandomWallsQuantity = 2;
	private int maxRandomWallSize = 4;
	private int minRandomWallSize = 1;
	private Wall upWall;
	private Wall downWall;
	private Wall leftWall;
	private Wall rightWall;
	private Food food;
	private Snake snake;
	private int maxSnakeLenght;
	private ScoreListener scoreListener;
	
	Field(int width, int height, ScoreListener scoreListener) {
		this.scoreListener = scoreListener;
		setSize(width, height);
		setFocusable(true);
		addKeyListener(new KeyEventHandler(this));
		
		setWallsAround();
		setRandomWalls();
		
		int snakeLeftX = (getWidth() / 2 / AnyObject.defaultThickness) * AnyObject.defaultThickness;
		int snakeUpY = (getHeight() / 2 / AnyObject.defaultThickness) * AnyObject.defaultThickness;
		snake = new Snake(new Point(snakeLeftX, snakeUpY), Directions.DOWN);
		for (AnyObject snakeParts : snake.snakeBody) {
			add(snakeParts);
		}
		maxSnakeLenght = snake.snakeBody.size();
		
		food = new Food(generateCoordinates());
		add(food);
	}
	
	public void snakeMove(Directions direction) {
		int componentCount = getComponentCount();
		AnyObject head = snake.snakeBody.peek();
		setComponentZOrder(head, componentCount - 1);
		
		Point tailLocation = new Point(snake.snakeBody.peekLast().getLocation());
		
		Directions wrongDirection = Directions.DOWN;
		int dX = snake.snakeBody.peek().getX() - snake.snakeBody.get(1).getX();
		int dY = snake.snakeBody.peek().getY() - snake.snakeBody.get(1).getY();
		Point deltaPoint = new Point(dX, dY);
		if (deltaPoint.equals(new Point(AnyObject.defaultThickness, 0))) {
			wrongDirection = Directions.LEFT;
		} else if (deltaPoint.equals(new Point(0, AnyObject.defaultThickness))) {
			wrongDirection = Directions.UP;
		} else if (deltaPoint.equals(new Point(-AnyObject.defaultThickness, 0))) {
			wrongDirection = Directions.RIGHT;
		} else if (deltaPoint.equals(new Point(0, -AnyObject.defaultThickness))) {
			wrongDirection = Directions.DOWN;
		}
		
		if (!direction.equals(wrongDirection)) {
			for (int i = snake.snakeBody.size() - 1; i > 0; i--) {
				snake.snakeBody.get(i).setLocation(snake.snakeBody.get(i - 1).getLocation());
			}

			switch (direction) {
			case UP:
				head.setLocation(head.getX(), head.getY() - AnyObject.defaultThickness);
				break;
			case DOWN:
				head.setLocation(head.getX(), head.getY() + AnyObject.defaultThickness);
				break;
			case LEFT:
				head.setLocation(head.getX() - AnyObject.defaultThickness, head.getY());
				break;
			case RIGHT:
				head.setLocation(head.getX() + AnyObject.defaultThickness, head.getY());
				break;
			}
		}
		
		if (snake.isFed) {
			snake.addSnakePart(tailLocation);
			AnyObject snakeTail = snake.snakeBody.peekLast();
			add(snakeTail);
			snake.isFed = false;
		}
		
		if (scoreListener != null && maxSnakeLenght < snake.snakeBody.size()) {
			maxSnakeLenght = snake.snakeBody.size();
			scoreListener.refreshScore(maxSnakeLenght - 3);
		}
		
		componentCount = getComponentCount();
		setComponentZOrder(head, componentCount - 1);
		Component componentUnderHead = findComponentAt(head.getLocation());
		if (componentUnderHead.getClass().equals(Food.class)) {
			snake.isFed = true;
			food.makeFood(generateCoordinates());
		} else if (componentUnderHead.getClass().equals(Wall.class)) {
			setFocusable(false);
			scoreListener.saveMaxScore();
		} else if (componentUnderHead.getClass().equals(Snake.SnakeBody.class)) {
			Snake.SnakeBody cuttedBody = (Snake.SnakeBody) componentUnderHead;
			LinkedList<AnyObject> cuttedTail = new LinkedList<AnyObject>();
			for (int i = snake.snakeBody.indexOf(cuttedBody); i < snake.snakeBody.size(); i++) {
				remove(snake.snakeBody.get(i));
				cuttedTail.add(snake.snakeBody.get(i));
			}
			snake.snakeBody.removeAll(cuttedTail);
		}
	}
	
	void setWallsAround() {
		Point upWallCoordinates = new Point(0, 0);
		Dimension upNDownWallSize = new Dimension(getWidth(), AnyObject.defaultThickness);
		Point downWallCoordinates = new Point(0, getHeight() - AnyObject.defaultThickness);
		Point leftWallCoordinates = new Point(0, AnyObject.defaultThickness);
		Dimension leftNRightWallSize = new Dimension(AnyObject.defaultThickness,
				getHeight() - 2 * AnyObject.defaultThickness);
		Point rightWallCoordinates = new Point(getWidth() - AnyObject.defaultThickness, AnyObject.defaultThickness);
		
		upWall = new Wall(upWallCoordinates, upNDownWallSize);
		add(upWall);
		downWall = new Wall(downWallCoordinates, upNDownWallSize);
		add(downWall);
		leftWall = new Wall(leftWallCoordinates, leftNRightWallSize);
		add(leftWall);
		rightWall = new Wall(rightWallCoordinates, leftNRightWallSize);
		add(rightWall);
	}
	
	void setRandomWalls() {
		Wall randomWall;
		Dimension randomWallSize;
		Point randomWallCoordinates;
		int snakeLeftX = (getWidth() / 2 / AnyObject.defaultThickness) * AnyObject.defaultThickness;
		int snakeUpY = (getHeight() / 2 / AnyObject.defaultThickness) * AnyObject.defaultThickness;
		int snakeRightX = snakeLeftX;
		int snakeDownY = snakeUpY + 3;
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
				crossedX = false;
				crossedY = false;
				randomWallCoordinates = generateCoordinates();
				wallLeftX = randomWallCoordinates.x;
				wallUpY = randomWallCoordinates.y;
				wallRightX = randomWallCoordinates.x + randomWallSize.width;
				wallDownY = randomWallCoordinates.y + randomWallSize.height;
				if (wallLeftX <= snakeRightX && wallLeftX >= snakeLeftX 
						|| wallRightX <= snakeRightX && wallRightX >= snakeLeftX
						|| wallLeftX <= snakeLeftX && wallRightX >= snakeRightX) {
					crossedX = true;
				}
				if (wallUpY <= snakeDownY && wallUpY >= snakeUpY 
						|| wallDownY <= snakeDownY && wallDownY >= snakeUpY
						|| wallUpY <= snakeUpY && wallDownY >= snakeDownY) {
					crossedY = true;
				}
			} while (randomWallCoordinates.x + randomWallSize.width > getWidth() ||
					randomWallCoordinates.y + randomWallSize.height > getHeight() ||
					crossedX && crossedY);
			randomWall = new Wall(randomWallCoordinates, randomWallSize);
			add(randomWall);
		}
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