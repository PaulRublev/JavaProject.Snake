import java.io.*;

import javax.swing.*;

public class StartGame {
	
	public static void main(String[] args) throws IOException {
		if (new File(Config.fileName).exists()) {
			Config.fileEnabled = true;
		}
		
		if (Config.fileEnabled) {
			Config.workWithConfigFile();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				Frame frame = new Frame();
				frame.setTitle(Config.getLang(Strings.SNAKE));
				frame.setVisible(true);
			}
		});
	}
	
}