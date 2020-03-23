package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventObject;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;



//class SnakeBody extends AbstractObj {
//	
//	Point nextStepXY;
//	
//	SnakeBody(Point XY, Point nextStepXY) {
//		super(XY);
//		symbol = '*';
//		empty = false;
//		this.nextStepXY = nextStepXY;
//	}
//	
//	void setNextStep(Point XY) {
//		nextStepXY = XY;
//	}
//	
//	Point getNextStep() {
//		return nextStepXY;
//	}
//	
//}
//
//class Snake implements Runnable {
//	LinkedList<SnakeBody> snake = new LinkedList<SnakeBody>();
//	AbstractObj[][] array;
//	Thread thread = new Thread(this);
//	
//	Snake(AbstractObj[][] array) {
//		this.array = array;
//		Point centerPoint = new Point(array[0].length / 2, array.length / 2);
//		snake.add(new SnakeBody(centerPoint, new Point(centerPoint.x, centerPoint.y - 1)));
//		SnakeBody head = snake.element();
//		head.symbol = 'H';
//		snake.add(new SnakeBody(new Point(head.getX(), head.getY() + 1), head.getXY()));
//		placingSnake();
//		thread.start();
//	}
//	
//	void placingSnake() {
//		for (SnakeBody snakeBody : snake) {
//			array[snakeBody.getY()][snakeBody.getX()] = snakeBody;
//		}
//	}
//	
//	@Override
//	public void run() {
//		do {
//			try {
//				thread.sleep(1000);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			array[snake.getLast().getY()][snake.getLast().getX()] = new AbstractObj(snake.getLast().getXY());
//			for (int i = 0; i <= snake.size() - 1; i++) {
//				SnakeBody body = snake.get(i);
//				body.setXY(body.getNextStep());
//				if (i != 0) {
//					body.setNextStep(snake.get(i - 1).getXY());
//				} else {
//					body.setNextStep(new Point(body.getX(), body.getY() - 1));
//				}
//			}
//			placingSnake();
//		} while (true);
//	}
//	
//}

class AnyObject extends JLabel {
	
	final static int defaultThickness = 10;
	
}

class Wall {
	
	AnyObject upWall;
	AnyObject downWall;
	AnyObject leftWall;
	AnyObject rightWall;
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

	AnyObject makeWall(Point coordinates, Dimension dimension, JComponent field) {
		AnyObject someWall = new AnyObject();
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
		do {
			x = ((int)(Math.random() * (field.getWidth() - 2 * AnyObject.defaultThickness) + AnyObject.defaultThickness) / 
					AnyObject.defaultThickness) * AnyObject.defaultThickness;
			y = ((int)(Math.random() * (field.getHeight() - 2 * AnyObject.defaultThickness) + AnyObject.defaultThickness) / 
					AnyObject.defaultThickness) * AnyObject.defaultThickness;
		} while (false);
		//System.out.print(x + ", " + y);
		Border border = BorderFactory.createLineBorder(Color.black, 2);
		Point foodLocation = new Point(x, y);
		Dimension foodSize = new Dimension(AnyObject.defaultThickness, AnyObject.defaultThickness);
		setBorder(border);
		setLocation(foodLocation);
		setSize(foodSize);
	}
	
}

class Snake {
	
	
	
}

class Field extends JComponent {
	
	Field(int width, int height) {
		setSize(width, height);
		setLayout(null);
		new Wall(this);
		new Wall().makeWall(new Point(20, 20), new Dimension(10, 20), this);
		Food food = new Food(this);
		//food.makeFood(this);
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





















