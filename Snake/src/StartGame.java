import java.io.*;

import javax.swing.*;

public class StartGame {
	
	public static void main(String[] args) {
		Config config = new Config();
		
		if (new File(Config.fileName).exists()) {
			config.setFileEnabled(true);
		}
		
		if (config.isFileEnabled()) {
			config.workWithConfigFile();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				Frame frame = new Frame(config);
				frame.setTitle(config.getLang(Strings.SNAKE));
				frame.setVisible(true);
			}
		});
	}
	
}