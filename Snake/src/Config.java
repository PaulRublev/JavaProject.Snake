import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

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

enum Views {
	DEFAULT,
	ONE,
	TWO
}

public class Config {
	
	public static final String fileName = "./config.md";
	public static boolean fileEnabled = false;
	public static Views viewConfiguration = Views.DEFAULT;
	public final static String view = "view";
	public static HashMap<String, String> configFileHashMap = new HashMap<String, String>();
	
	public static void changeView(String anotherViewString) {
		if (anotherViewString != null) {
			if (anotherViewString.equalsIgnoreCase(Views.ONE.toString())) {
				viewConfiguration = Views.ONE;
			} else if (anotherViewString.equalsIgnoreCase(Views.TWO.toString())) {
				viewConfiguration = Views.TWO;
			} else if (anotherViewString.equalsIgnoreCase(Views.DEFAULT.toString())) {
				viewConfiguration = Views.DEFAULT;
			}
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
	
	private static void createConfigFileHashMap() {
		configFileHashMap.put(view, viewConfiguration.toString());
	}
	
	public static void applyConfigFileHashMap() {
		changeView(configFileHashMap.get(view));
	}
	
	public static String getConfigurations() {
		createConfigFileHashMap();
		
		String file = "";
		ArrayList<String> keys = new ArrayList<String>();
		keys.addAll(configFileHashMap.keySet());
		for (String key : configFileHashMap.keySet()) {
			file += "<" + key + ">" + configFileHashMap.get(key) + "</" + key + ">" + "\n";
		}
		return file;
	}
	
	public static void fillConfigFile(File file) throws IOException {
		FileOutputStream fOut = new FileOutputStream(file);
		BufferedWriter bOut = new BufferedWriter(new OutputStreamWriter(fOut, "UTF-8"));
		bOut.write(Config.getConfigurations());
		bOut.close();
	}
	
	public static void workWithConfigFile() {
		try {
			String fConfigFileName = Config.fileName;
			File fConfig = new File(fConfigFileName);
			if (fConfig.createNewFile()) {
				fillConfigFile(fConfig);
			} else {
				FileInputStream fIn = new FileInputStream(fConfig);
				BufferedReader bIn = new BufferedReader(new InputStreamReader(fIn, "UTF-8"));
				String fileContent = "";
				do {
					fileContent += bIn.readLine();
				} while (bIn.ready());
				bIn.close();
				
				if (fileContent.isBlank() || fileContent.equalsIgnoreCase("null")) {
					fillConfigFile(fConfig);
				} else {
					char[] fileCharArray = new char[fileContent.length()];
					fileCharArray = fileContent.toCharArray();
					String key = "";
					String keyCheck = "";
					String value = "";
					boolean isValue = false;
					boolean isKey = false;
					boolean isKeyCheck = false;
					for (int i = 0; i < fileCharArray.length; i++) {
						if ((i + 1) < fileCharArray.length && fileCharArray[i] == '<') {
							if (fileCharArray[i + 1] == '/') {
								isKeyCheck = true;
								isValue = false;
								i++;
								continue;
							} else {
								isKey = true;
								isValue = false;
								continue;
							}
						} else if (fileCharArray[i] == '>') {
							if (isKey && !isKeyCheck) {
								isValue = true;
							} else if (key.equalsIgnoreCase(keyCheck)) {
								System.out.println(key);
								System.out.println(value);
								
								Config.configFileHashMap.put(key, value);
								Config.applyConfigFileHashMap();
								System.out.println(Config.configFileHashMap);
								key = "";
								keyCheck = "";
								value = "";
							}
							isKeyCheck = false;
							isKey = false;
							continue;
						}
						
						if (isValue) {
							value += fileCharArray[i];
						} else if (isKey) {
							key += fileCharArray[i];
						} else if (isKeyCheck) {
							keyCheck += fileCharArray[i];
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("File not found. " + e);
		}
	}
	
}
