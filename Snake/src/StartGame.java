import javax.swing.*;

public class StartGame {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
				Frame frame = new Frame();
				frame.setTitle("Snake");
				frame.setVisible(true);
			}
		});
	}
	
}