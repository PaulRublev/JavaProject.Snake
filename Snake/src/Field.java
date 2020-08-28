import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.JComponent;

enum Directions {
	UP,
	DOWN,
	LEFT,
	RIGHT
}

class Field extends JComponent {
	
	private static final long serialVersionUID = 1L;
	private static final int maxRandomWallsQuantity = 5;
	private static final int minRandomWallsQuantity = 2;
	private static final int maxRandomWallSize = 4;
	private static final int minRandomWallSize = 1;
	private Wall upWall;
	private Wall downWall;
	private Wall leftWall;
	private Wall rightWall;
	private Food food;
	private Snake snake;
	private int maxSnakeLength;
	private ScoreListener scoreListener;
	private Config config;
	
	Field(int width, int height, ScoreListener scoreListener, Config config) {
		this.config = config;
		this.scoreListener = scoreListener;
		setSize(width, height);
		setFocusable(true);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Directions direction;
				switch (e.getExtendedKeyCode()) {
				case 37: // Left
					direction = Directions.LEFT;
					snakeMove(direction);
					break;
				case 38: // Up
					direction = Directions.UP;
					snakeMove(direction);
					break;
				case 39: // Right
					direction = Directions.RIGHT;
					snakeMove(direction);
					break;
				case 40: // Down
					direction = Directions.DOWN;
					snakeMove(direction);
					break;
				}
			}
		});
		
		setWallsAround();
		setRandomWalls();
		
		int snakeLeftX = (getWidth() / 2 / AnyObject.defaultThickness) * AnyObject.defaultThickness;
		int snakeUpY = (getHeight() / 2 / AnyObject.defaultThickness) * AnyObject.defaultThickness;
		snake = new Snake(new Point(snakeLeftX, snakeUpY), Directions.DOWN, config);
		for (AnyObject snakeParts : snake.snakeBody) {
			add(snakeParts);
		}
		maxSnakeLength = snake.snakeBody.size();
		
		food = new Food(generateCoordinates(), config);
		add(food);
	}
	
	private void snakeMove(Directions direction) {
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
		
		if (scoreListener != null && maxSnakeLength < snake.snakeBody.size()) {
			maxSnakeLength = snake.snakeBody.size();
			scoreListener.refreshScore(maxSnakeLength - 3);
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
	
	private void setWallsAround() {
		Point upWallCoordinates = new Point(0, 0);
		Dimension upNDownWallSize = new Dimension(getWidth(), AnyObject.defaultThickness);
		Point downWallCoordinates = new Point(0, getHeight() - AnyObject.defaultThickness);
		Point leftWallCoordinates = new Point(0, AnyObject.defaultThickness);
		Dimension leftNRightWallSize = new Dimension(AnyObject.defaultThickness,
				getHeight() - 2 * AnyObject.defaultThickness);
		Point rightWallCoordinates = new Point(getWidth() - AnyObject.defaultThickness, AnyObject.defaultThickness);
		
		upWall = new Wall(upWallCoordinates, upNDownWallSize, config);
		add(upWall);
		downWall = new Wall(downWallCoordinates, upNDownWallSize, config);
		add(downWall);
		leftWall = new Wall(leftWallCoordinates, leftNRightWallSize, config);
		add(leftWall);
		rightWall = new Wall(rightWallCoordinates, leftNRightWallSize, config);
		add(rightWall);
	}
	
	private void setRandomWalls() {
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
			randomWall = new Wall(randomWallCoordinates, randomWallSize, config);
			add(randomWall);
		}
	}
	
	private Point generateCoordinates() {
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
	
	private Dimension generateSize() {
		int width = (int) (Math.random() * (maxRandomWallSize - minRandomWallSize + 1) + minRandomWallSize)
				* AnyObject.defaultThickness;
		int height  = (int) (Math.random() * (maxRandomWallSize - minRandomWallSize + 1) + minRandomWallSize)
				* AnyObject.defaultThickness;
		return new Dimension(width, height);
	}
}