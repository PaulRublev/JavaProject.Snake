import java.awt.Color;
import java.io.*;
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
	RESET,
	SETTINGS,
	CHOOSE_VIEW,
	CHOOSE_LANG,
	DEL,
	SAVE
}

enum Views {
	DEFAULT,
	ONE,
	TWO
}

enum Languages {
	EN,
	RU
}

public class Config {
	
	public static final String fileName = "./config.md";
	public static boolean fileToDelete = false;
	public static boolean fileEnabled = false;
	public static Views viewConfiguration = Views.DEFAULT;
	public static Languages langConfiguration = Languages.EN;
	public static int maxScore = 0;
	public final static String view = "view";
	public final static String lang = "lang";
	public final static String score = "score";
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
	
	public static void changeLang(String anotherLangString) {
		if (anotherLangString != null) {
			if (anotherLangString.equalsIgnoreCase(Languages.EN.toString())) {
				langConfiguration = Languages.EN;
			} else if (anotherLangString.equalsIgnoreCase(Languages.RU.toString())) {
				langConfiguration = Languages.RU;
			}
		}
	}
	
	public static void changeMaxScore(String scoreString) {
		if (scoreString != null) {
			try {
				int score = Integer.parseInt(scoreString);
				maxScore = score;
			} catch (NumberFormatException e) {
				System.out.print(e);
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
	
	public static String getLang(Strings string) {
		switch (langConfiguration) {
		case EN:
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
			case CHOOSE_VIEW:
				return "View:";
			case CHOOSE_LANG:
				return "Language:";
			case SETTINGS:
				return "Settings";
			case DEL:
				return "Delete \"" + Config.fileName + "\"";
			case SAVE:
				return "Save to \"" + Config.fileName + "\"";

			default:
				return "?";
			}
		case RU:
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
			case CHOOSE_VIEW:
				return "Вид:";
			case CHOOSE_LANG:
				return "Язык:";
			case SETTINGS:
				return "Настройки";
			case DEL:
				return "Удалить \"" + Config.fileName + "\"";
			case SAVE:
				return "Сохранить в \"" + Config.fileName + "\"";

			default:
				return "?";
			}
		default:
			return "?";
		}
	}
	
	private static void createConfigFileHashMap() {
		configFileHashMap.put(view, viewConfiguration.toString());
		configFileHashMap.put(lang, langConfiguration.toString());
		configFileHashMap.put(score, String.valueOf(maxScore));
	}
	
	public static void applyConfigFileHashMap() {
		changeView(configFileHashMap.get(view));
		changeLang(configFileHashMap.get(lang));
		changeMaxScore(configFileHashMap.get(score));
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
	
	public static boolean fileExists(String fileName) {
		boolean exists = false;
		try {
			exists = new File(fileName).exists();
		} catch (Exception e) {
			System.out.println(e);
		}
		return exists;
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
								Config.configFileHashMap.put(key, value);
								Config.applyConfigFileHashMap();
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
