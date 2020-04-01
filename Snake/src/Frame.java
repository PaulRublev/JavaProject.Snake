import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;

interface OptionListener {
	void workWithOptions();
	void confirmOptions();
}

class Frame extends JFrame implements ActionListener, OptionListener {
	
	private static final long serialVersionUID = 1L;
	private int frameWidth = 535;
	private int frameHeight = 400;
	private int frameLocationX = 300;
	private int frameLocationY = 300;
	private int fieldWidth = 400;
	private int fieldHeight = 350;
	private int fieldX = 10;
	private int fieldY = 10;
	private int rightPanelWidth = 100;
	private int rightPanelHeight = 350;
	private int rightPanelX = fieldX + fieldWidth + 5;
	private int rightPanelY = 10;
	private RightPanel panel;
	private JComponent field;
	
	Frame() {
		setBounds(frameLocationX, frameLocationY, frameWidth, frameHeight);
		
		WindowListener closeListener = new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// required by WindowListener interface, left empty as not used
			}
			@Override
			public void windowIconified(WindowEvent e) {
				// required by WindowListener interface, left empty as not used
			}
			@Override
			public void windowDeiconified(WindowEvent e) {
				// required by WindowListener interface, left empty as not used
			}
			@Override
			public void windowDeactivated(WindowEvent e) {
				// required by WindowListener interface, left empty as not used
			}
			@Override
			public void windowClosed(WindowEvent e) {
				// required by WindowListener interface, left empty as not used
			}
			@Override
			public void windowActivated(WindowEvent e) {
				// required by WindowListener interface, left empty as not used
			}
			@Override
			public void windowClosing(WindowEvent e) {
				panel.saveMaxScore();
				try {
					if (Config.fileExists(Config.fileName) && Config.fileEnabled) {
						File file = new File(Config.fileName);
						Config.fillConfigFile(file);
					}
				} catch (Exception exception) {
					System.out.println("File not found. " + exception);
				}
				System.exit(0);
			}
		};
		addWindowListener(closeListener);
		setLayout(null);
		setResizable(false);
		
		panel = makeRightPanel();
		add(panel);
		
		field = makeOptionField();
		add(field);
	}
	
	private OptionField makeOptionField() {
		OptionField field = new OptionField(fieldWidth, fieldHeight, this);
		field.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		field.setLocation(fieldX, fieldY);
		return field;
	}
	
	private Field makeField() {
		Field field = new Field(fieldWidth, fieldHeight, panel);
		field.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		field.setLocation(fieldX, fieldY);
		field.setLayout(null);
		return field;
	}
	
	private RightPanel makeRightPanel() {
		RightPanel panel = new RightPanel();
		panel.setSize(rightPanelWidth, rightPanelHeight);
		panel.setLocation(rightPanelX, rightPanelY);
		panel.setLayout(null);
		panel.setFocusable(false);
		panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		panel.resetButton.addActionListener(this);
		return panel;
	}
	
	public void actionPerformed(ActionEvent e) {
		panel.saveMaxScore();
		panel.refreshScore(0);
		remove(field);
		field = makeField();
		add(field);
		repaint();
		field.requestFocusInWindow();
	}
	
	public void workWithOptions() {
		setTitle(Config.getLang(Strings.SNAKE));
		remove(panel);
		panel = makeRightPanel();
		add(panel);
		repaint();
	}
	
	public void confirmOptions() {
		remove(field);
		field = makeField();
		add(field);
		field.requestFocusInWindow();
		panel.resetButton.setEnabled(true);
		repaint();
	}
	
}