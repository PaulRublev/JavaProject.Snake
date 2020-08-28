import java.io.*;
import java.util.HashMap;

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
	private boolean fileToDelete = false;
	private boolean fileEnabled = false;
	private Views viewConfiguration = Views.DEFAULT;
	private Languages langConfiguration = Languages.EN;
	private int maxScore = 0;
	private final static String view = "view";
	private final static String lang = "lang";
	private final static String score = "score";
	private HashMap<String, String> configFileHashMap = new HashMap<>();
	
	Config() {
		createConfigFileHashMap();
	}
	
	public void changeView(String anotherViewString) {
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
	
	public void changeLang(String anotherLangString) {
		if (anotherLangString != null) {
			if (anotherLangString.equalsIgnoreCase(Languages.EN.toString())) {
				langConfiguration = Languages.EN;
			} else if (anotherLangString.equalsIgnoreCase(Languages.RU.toString())) {
				langConfiguration = Languages.RU;
			}
		}
	}
	
	public void changeMaxScore(String scoreString) {
		if (scoreString != null) {
			try {
				int score = Integer.parseInt(scoreString);
				maxScore = score;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getLang(Strings string) {
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
				return "Delete \"" + fileName + "\"";
			case SAVE:
				return "Save to \"" + fileName + "\"";

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
				return "Удалить \"" + fileName + "\"";
			case SAVE:
				return "Сохранить в \"" + fileName + "\"";

			default:
				return "?";
			}
		default:
			return "?";
		}
	}
	
	private void createConfigFileHashMap() {
		configFileHashMap.put(view, viewConfiguration.toString());
		configFileHashMap.put(lang, langConfiguration.toString());
		configFileHashMap.put(score, String.valueOf(maxScore));
	}
	
	private void applyConfigFileHashMap() {
		changeView(configFileHashMap.get(view));
		changeLang(configFileHashMap.get(lang));
		changeMaxScore(configFileHashMap.get(score));
	}
	
	private String getConfigurations() {
		createConfigFileHashMap();
		
		String file = "";
		for (String key : configFileHashMap.keySet()) {
			file += "<" + key + ">" + configFileHashMap.get(key) + "</" + key + ">" + "\n";
		}
		return file;
	}
	
	public static boolean fileExists(String fileName) {
		return new File(fileName).exists();
	}
	
	public void fillConfigFile(File file) throws IOException {
		FileOutputStream fOut = new FileOutputStream(file);
		BufferedWriter bOut = new BufferedWriter(new OutputStreamWriter(fOut, "UTF-8"));
		bOut.write(getConfigurations());
		bOut.close();
	}
	
	public void workWithConfigFile() {
		try {
			File fConfig = new File(fileName);
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
								configFileHashMap.put(key, value);
								applyConfigFileHashMap();
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setFileToDelete(boolean state) {
		fileToDelete = state;
	}
	
	public boolean isFileToDelete() {
		return fileToDelete;
	}
	
	public void setFileEnabled(boolean state) {
		fileEnabled = state;
	}
	
	public boolean isFileEnabled() {
		return fileEnabled;
	}
	
	public void setViewConfiguration(Views view) {
		viewConfiguration = view;
	}
	
	public Views getViewConfiguration() {
		return viewConfiguration;
	}
	
	public void setLangConfiguration(Languages lang) {
		langConfiguration = lang;
	}
	
	public Languages getLangConfiguration() {
		return langConfiguration;
	}
	
	public void setMaxScore(int score) {
		maxScore = score;
	}
	
	public int getMaxScore() {
		return maxScore;
	}
	
}
