package main;

import java.awt.Point;

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
			x = (int)(Math.random() * (array.length - 2) + 1);
			y = (int)(Math.random() * (array[0].length - 2) + 1);
		} while (!array[x][y].empty);
		setXY(new Point(x, y));
		array[x][y] = this;
	}
	
}

class SnakeBody extends AbstractObj {
	
	SnakeBody(Point XY) {
		super(XY);
		symbol = '*';
		empty = false;
	}
	
}

class Field {
	
	AbstractObj[][] array;
	
	Field(int width, int height) {
		array = new AbstractObj[height][width];
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
		new Food(array);
	}
	
}


public class StartGame {
	
	static int fieldWidth = 25;
	static int fieldHeight = 20;
	
	public static void main(String[] args) {
		Field field = new Field(fieldWidth, fieldHeight);
		refresh(field);
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
			for (int j = 0; j < field.array[i].length; j++) {
				System.out.print(field.array[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
}





















