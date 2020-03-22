package main;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventObject;
import java.util.LinkedList;
import javax.swing.Timer;

class AbstractObj {
	
	Point XY;
	char symbol = ' ';
	boolean empty = true;
	
	AbstractObj() {
		
	}
	AbstractObj(Point XY) {
		setXY(XY);
	}
	
	public void setXY(Point XY) {
		this.XY = XY;
	}
	
	public int getX() {
		return this.XY.x;
	}
	
	public int getY() {
		return this.XY.y;
	}
	
	public Point getXY() {
		return this.XY;
	}
	
	@Override
	public String toString() {
		return String.valueOf(symbol);
	}
	
}

class Wall extends AbstractObj {
	
	Wall(Point XY) {
		super(XY);
		symbol = 'W';
		empty = false;
	}
	
}

class Food extends AbstractObj {
	
	AbstractObj[][] array;
	
	Food(AbstractObj[][] array) {
		super();
		this.array = array;
		symbol = 'F';
		empty = false;
		setPosition();
	}
	
	void setPosition() {
		int x;
		int y;
		do {
			y = (int)(Math.random() * (array.length - 2) + 1);
			x = (int)(Math.random() * (array[0].length - 2) + 1);
		} while (!array[y][x].empty);
		setXY(new Point(x, y));
		array[y][x] = this;
	}
	
}

class SnakeBody extends AbstractObj {
	
	Point nextStepXY;
	
	SnakeBody(Point XY, Point nextStepXY) {
		super(XY);
		symbol = '*';
		empty = false;
		this.nextStepXY = nextStepXY;
	}
	
	void setNextStep(Point XY) {
		nextStepXY = XY;
	}
	
	Point getNextStep() {
		return nextStepXY;
	}
	
}

class Snake implements Runnable {
	LinkedList<SnakeBody> snake = new LinkedList<SnakeBody>();
	AbstractObj[][] array;
	Thread thread = new Thread(this);
	
	Snake(AbstractObj[][] array) {
		this.array = array;
		Point centerPoint = new Point(array[0].length / 2, array.length / 2);
		snake.add(new SnakeBody(centerPoint, new Point(centerPoint.x, centerPoint.y - 1)));
		SnakeBody head = snake.element();
		head.symbol = 'H';
		//System.out.println(snake.indexOf(head));
		snake.add(new SnakeBody(new Point(head.getX(), head.getY() + 1), head.getXY()));
		//System.out.println(snake.indexOf(head));
		placingSnake();
		thread.start();
	}
	
	void placingSnake() {
		for (SnakeBody snakeBody : snake) {
			array[snakeBody.getY()][snakeBody.getX()] = snakeBody;
		}
	}
	
	@Override
	public void run() {
//		try {
//			thread.sleep(100);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		do {
			try {
				thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			array[snake.getLast().getY()][snake.getLast().getX()] = new AbstractObj(snake.getLast().getXY());
			for (int i = 0; i <= snake.size() - 1; i++) {
				//System.out.println(i);
				SnakeBody body = snake.get(i);
				body.setXY(body.getNextStep());
				if (i != 0) {
					body.setNextStep(snake.get(i - 1).getXY());
				} else {
					body.setNextStep(new Point(body.getX(), body.getY() - 1));
				}
			}
			placingSnake();
		} while (true);
	}
	
}

class Field {
	
	AbstractObj[][] array;
	
	Field(int width, int height) {
		array = new AbstractObj[height][width];
		initField();
		new Snake(array);
		new Food(array);
	}
	
	void initField() {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				Point XY = new Point(i, j);
				if (i == 0 || j == 0 || j == array[i].length - 1 || i == array.length - 1) {
					array[i][j] = new Wall(XY);
				} else {
					array[i][j] = new AbstractObj(XY);
				}
			}
		}
	}
	
}


public class StartGame {
	
	static int fieldWidth = 25;
	static int fieldHeight = 20;
	
	public static void main(String[] args) {
		Field field = new Field(fieldWidth, fieldHeight);
		Thread thread = Thread.currentThread();
		for (int i = 0; i < 50; i++) {
			try {
				thread.sleep(200);
			} catch (Exception e) {
				e.printStackTrace();
			}
			refresh(field);
		}
		
	}
	
	static void clear() {
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (Exception e) {
			System.out.print(e);
		}
	}
	
	public static void refresh(Field field) {
		clear();
		for (int i = 0; i < field.array.length; i++) {
			String arrayString = "";
			for (int j = 0; j < field.array[i].length; j++) {
				//System.out.print(field.array[i][j] + " ");
				arrayString += field.array[i][j] + " ";
			}
			System.out.println(arrayString);
		}
	}
	
}





















