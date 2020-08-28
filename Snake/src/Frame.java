import java.awt.Color;
import java.awt.event.*;
import java.io.*;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;

interface OptionListener {
	void repaintRightPanel();
	void repaintField();
}

class Frame extends JFrame implements OptionListener {
	
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
	private Config config;
	
	Frame(Config config) {
		this.config = config;
		setBounds(frameLocationX, frameLocationY, frameWidth, frameHeight);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				panel.saveMaxScore();
				try {
					if (Config.fileExists(Config.fileName) && config.isFileEnabled()) {
						File file = new File(Config.fileName);
						config.fillConfigFile(file);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.exit(0);
			}
		});
		setLayout(null);
		setResizable(false);
		
		panel = makeRightPanel();
		add(panel);
		
		field = makeOptionField();
		add(field);
	}
	
	private OptionField makeOptionField() {
		OptionField field = new OptionField(fieldWidth, fieldHeight, this, config);
		field.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		field.setLocation(fieldX, fieldY);
		return field;
	}
	
	private Field makeField() {
		Field field = new Field(fieldWidth, fieldHeight, panel, config);
		field.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		field.setLocation(fieldX, fieldY);
		field.setLayout(null);
		return field;
	}
	
	private RightPanel makeRightPanel() {
		RightPanel panel = new RightPanel(config);
		panel.setSize(rightPanelWidth, rightPanelHeight);
		panel.setLocation(rightPanelX, rightPanelY);
		panel.setLayout(null);
		panel.setFocusable(false);
		panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		panel.resetButton.addActionListener(event -> {
			this.panel.saveMaxScore();
			this.panel.refreshScore(0);
			remove(field);
			field = makeField();
			add(field);
			repaint();
			field.requestFocusInWindow();
		});
		return panel;
	}
	
	public void repaintRightPanel() {
		setTitle(config.getLang(Strings.SNAKE));
		remove(panel);
		panel = makeRightPanel();
		add(panel);
		repaint();
	}
	
	public void repaintField() {
		remove(field);
		field = makeField();
		add(field);
		field.requestFocusInWindow();
		panel.resetButton.setEnabled(true);
		repaint();
	}
	
}