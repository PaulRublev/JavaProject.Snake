

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;


class AnyObject extends JLabel {
	
	final static int defaultThickness = 10;
	
}

class Wall {
	
	class Barrier extends AnyObject {
		
	}
	
	Barrier upWall;
	Barrier downWall;
	Barrier leftWall;
	Barrier rightWall;
	Border border = BorderFactory.createLineBorder(Color.black, 4);
	
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

class Food extends AnyObject {
	
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

class Snake implements KeyListener {
	
	class SnakeBody extends AnyObject {
		
	}
	class SnakeHead extends AnyObject {
		
	}
	enum Directions {
		UP,
		DOWN,
		LEFT,
		RIGHT
	}
	
	LinkedList<AnyObject> snakeBody = new LinkedList<AnyObject>();
	Dimension bodySize = new Dimension(AnyObject.defaultThickness, AnyObject.defaultThickness);
	Border border = BorderFactory.createLineBorder(Color.black, 1);
	JComponent field;
	Directions directions = Directions.UP;
	boolean isFed = false;
	
	Snake(JComponent field) {
		this.field = field;
		Point initHeadLocation = new Point();
		initHeadLocation.x = (field.getWidth() / 2 / AnyObject.defaultThickness) * AnyObject.defaultThickness;
		initHeadLocation.y = (field.getHeight() / 2 / AnyObject.defaultThickness) * AnyObject.defaultThickness;
		addSnakePart(initHeadLocation);
		AnyObject head = snakeBody.peek();
		head.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		head.addKeyListener(this);
		head.setFocusable(true);
		field.setComponentZOrder(head, 6);	// Set the necessary priority for head
		
		Point initBodyLocation = new Point();
		initBodyLocation.x = initHeadLocation.x;
		initBodyLocation.y = initHeadLocation.y + AnyObject.defaultThickness;
		addSnakePart(initBodyLocation);
		initBodyLocation.x = initHeadLocation.x;
		initBodyLocation.y = initHeadLocation.y + 2 * AnyObject.defaultThickness;
		addSnakePart(initBodyLocation);
	}
	
	void addSnakePart(Point location) {
		AnyObject body;
		if (snakeBody.size() == 0) 
			body = new SnakeHead();
		else
			body = new SnakeBody();
		body.setLocation(location);
		body.setSize(bodySize);
		body.setBorder(border);
		snakeBody.add(body);
		field.add(body);
		field.setComponentZOrder(body, 1);	// Set the necessary priority for body
	}
	
	void move(Directions direction) {
		AnyObject head = snakeBody.peek();
		for (int i = snakeBody.size() - 1; i > 0; i--) {
			snakeBody.get(i).setLocation(snakeBody.get(i - 1).getLocation());
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

	@Override
	public void keyPressed(KeyEvent e) {
		Point tailLocation = new Point(snakeBody.peekLast().getLocation());
		Directions wrongDirection = Directions.DOWN;
		int dX = snakeBody.peek().getX() - snakeBody.get(1).getX();
		int dY = snakeBody.peek().getY() - snakeBody.get(1).getY();
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
		switch (e.getExtendedKeyCode()) {
		case 37:	// Left
			if (!Directions.LEFT.equals(wrongDirection)) {
				directions = Directions.LEFT;
				move(directions);
			}
			break;
		case 38:	// Up
			if (!Directions.UP.equals(wrongDirection)) {
				directions = Directions.UP;
				move(directions);
			}
			break;
		case 39:	// Right
			if (!Directions.RIGHT.equals(wrongDirection)) {
				directions = Directions.RIGHT;
				move(directions);
			}
			break;
		case 40:	// Down
			if (!Directions.DOWN.equals(wrongDirection)) {
				directions = Directions.DOWN;
				move(directions);
			}
			break;
		}
		if (isFed) {
			addSnakePart(tailLocation);
			isFed = false;
		}
		actionAfterMove();
	}
	
	void actionAfterMove() {
		AnyObject head = snakeBody.peek();
		Component componentUnderHead = field.findComponentAt(head.getLocation());
		if (componentUnderHead.getClass().equals(Food.class)) {
			isFed = true;
			Food food = (Food) componentUnderHead;
			food.makeFood(field);
		} else if (componentUnderHead.getClass().equals(Wall.Barrier.class)) {
			head.setFocusable(false);
		} else if (componentUnderHead.getClass().equals(SnakeBody.class)) {
			SnakeBody cuttedBody = (SnakeBody) componentUnderHead;
			LinkedList<AnyObject> cuttedTail = new LinkedList<AnyObject>();
			for (int i = snakeBody.indexOf(cuttedBody); i < snakeBody.size(); i++) {
				field.remove(snakeBody.get(i));
				cuttedTail.add(snakeBody.get(i));
			}
			snakeBody.removeAll(cuttedTail);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//required by KeyListener interface
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//required by KeyListener interface
	}
	
}

class Field extends JComponent {
	
	Field(int width, int height) {
		setSize(width, height);
		setLayout(null);
		new Wall(this);
		new Wall().makeWall(new Point(20, 20), new Dimension(10, 20), this);
		Food food = new Food(this);
		Snake snake = new Snake(this);
	}
	
}

class Frame extends JFrame {
	
	private int frameWidth = 450;
	private int frameHeight = 400;
	private int frameLocationX = 300;
	private int frameLocationY = 300;
	private int fieldWidth = 400;
	private int fieldHeight = 350;
	
	Frame() {
		setBounds(frameLocationX, frameLocationY, frameWidth, frameHeight);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		setVisible(true);
		Field field = new Field(fieldWidth, fieldHeight);
		add(field);
	}
	
}


public class StartGame {
	
	public static void main(String[] args) {
		Frame frame = new Frame();
	}
	
}





















