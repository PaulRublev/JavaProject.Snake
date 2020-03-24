import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

class Snake extends KeyAdapter implements KeyListener {
	
	class SnakeBody extends AnyObject {
		private static final long serialVersionUID = 1L;
	}
	class SnakeHead extends AnyObject {
		private static final long serialVersionUID = 1L;
	}
	enum Directions {
		UP,
		DOWN,
		LEFT,
		RIGHT
	}
	
	LinkedList<AnyObject> snakeBody = new LinkedList<AnyObject>();
	Dimension	bodySize = new Dimension(AnyObject.defaultThickness, AnyObject.defaultThickness);
	Border		border = BorderFactory.createLineBorder(Color.black, 1);
	JComponent 	field;
	Directions 	directions = Directions.UP;
	boolean 	isFed = false;
	
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
		if (snakeBody.size() == 0) {
			body = new SnakeHead();
		} else {
			body = new SnakeBody();
		}
		body.setLocation(location);
		body.setSize(bodySize);
		body.setBorder(border);
		snakeBody.add(body);
		field.add(body);
		field.setComponentZOrder(body, 1);	// Set the necessary priority for body
	}
	
	void move(Directions direction) {
		for (int i = snakeBody.size() - 1; i > 0; i--) {
			snakeBody.get(i).setLocation(snakeBody.get(i - 1).getLocation());
		}
		
		AnyObject head = snakeBody.peek();
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
	
}