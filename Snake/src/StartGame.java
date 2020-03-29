import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

public class StartGame {
	
	public static void main(String[] args) throws IOException {
//		try {
//			String fConfigPath = "./config";
//			File fCongig = new File(fConfigPath);
//			if (fCongig.exists()) {
//				
//			}
//		} catch (FileNotFoundException e) {
//			System.out.println("File not found. " + e);
//		}
		
		Config.changeView("one");
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				Frame frame = new Frame();
				frame.setTitle(Config.getView(Strings.SNAKE));
				frame.setVisible(true);
			}
		});
	}
	
}