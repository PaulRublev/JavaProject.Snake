import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

enum Strings {
	WALL,
	FOOD,
	SNAKE,
	SCORE,
	RECORD,
	RESET
}

public class Config {
	
	enum Views {
		DEFAULT,
		ONE,
		TWO
	}
	
	private static Views viewConfiguration = Views.DEFAULT;
	
	public static void changeView(String anotherViewString) {
		if (anotherViewString.equalsIgnoreCase(Views.ONE.toString())) {
			viewConfiguration = Views.ONE;
		} else if (anotherViewString.equalsIgnoreCase(Views.TWO.toString())) {
			viewConfiguration = Views.TWO;
		}
	}
	
	public static void getView(Wall wall) {
		switch (viewConfiguration) {
		case DEFAULT:
			wall.setOpaque(true);
			wall.setBackground(Color.BLACK);
			break;
		case ONE:
			wall.setOpaque(true);
			wall.setBackground(Color.LIGHT_GRAY);
			wall.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			break;
		case TWO:
			
			break;
		}
	}
	
	public static void getView(Food food) {
		switch (viewConfiguration) {
		case DEFAULT:
			food.setBorder(BorderFactory.createLineBorder(Color.black, 2));
			break;
		case ONE:
			food.setOpaque(true);
			food.setBackground(Color.WHITE);
			food.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			break;
		case TWO:
			
			break;
		}
	}
	
	public static void getView(Snake.SnakeBody body) {
		switch (viewConfiguration) {
		case DEFAULT:
			body.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			break;
		case ONE:
			body.setOpaque(true);
			body.setBackground(Color.LIGHT_GRAY);
			body.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			break;
		case TWO:
			
			break;
		}
	}
	
	public static void getView(Snake.SnakeHead head) {
		switch (viewConfiguration) {
		case DEFAULT:
			head.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			break;
		case ONE:
			head.setOpaque(true);
			head.setBackground(Color.GRAY);
			head.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			break;
		case TWO:
			
			break;
		}
	}
	
	public static String getView(Strings string) {
		switch (viewConfiguration) {
		case DEFAULT:
			switch (string) {
			case WALL:
				return "Wall";
			case FOOD:
				return "Food";
			case SNAKE:
				return "Snake";
			case SCORE:
				return "Score:";
			case RECORD:
				return "Record:";
			case RESET:
				return "RESET";

			default:
				return "?";
			}
		case ONE:
			switch (string) {
			case WALL:
				return "Стена";
			case FOOD:
				return "Еда";
			case SNAKE:
				return "Змейка";
			case SCORE:
				return "Счет:";
			case RECORD:
				return "Рекорд:";
			case RESET:
				return "СБРОС";

			default:
				return "?";
			}
			
		case TWO:
			break;
		}
		return null;
	}
	
}
