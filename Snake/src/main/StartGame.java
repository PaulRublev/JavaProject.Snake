package main;


class AbstractObj {
	
	int x;
	int y;
	char symbol = ' ';
	
	AbstractObj(int x, int y) {
		setXY(x, y);
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	@Override
	public String toString() {
		return String.valueOf(symbol);
	}
}

class Wall extends AbstractObj {
	
	Wall(int x, int y) {
		super(x, y);
		symbol = 'W';
	}
}

class Food extends AbstractObj {
	
	Food(int x, int y) {
		super(x, y);
		symbol = 'F';
	}
}

class SnakeBody extends AbstractObj {
	
	SnakeBody(int x, int y) {
		super(x, y);
		symbol = '*';
	}
}


public class StartGame {

	public static void main(String[] args) throws Exception {
		int fieldWidth = 25;
		int fieldHeight = 20;
		AbstractObj[][] array = new AbstractObj[fieldHeight][fieldWidth];
		for (int i = 0; i < fieldHeight; i++) {
			for (int j = 0; j < fieldWidth; j++) {
				if (i == 0 || j == 0 || j == fieldWidth - 1 || i == fieldHeight - 1) {
					array[i][j] = new Wall(i, j);
				} else {
					array[i][j] = new AbstractObj(i, j);
				}
			}
		}
		clear();
		for (int i = 0; i < fieldHeight; i++) {
			for (int j = 0; j < fieldWidth; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println("");
		}
		clear();
	}
	
	static void clear() throws Exception {
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	}
}





















