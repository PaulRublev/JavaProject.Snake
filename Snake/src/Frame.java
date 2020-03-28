import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

class Frame extends JFrame implements ActionListener{
	
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
	private Field field;
	
	Frame() {
		setBounds(frameLocationX, frameLocationY, frameWidth, frameHeight);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		
		panel = new RightPanel();
		panel.setSize(rightPanelWidth, rightPanelHeight);
		panel.setLocation(rightPanelX, rightPanelY);
		panel.setLayout(null);
		panel.setFocusable(false);
		panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		panel.resetButton.addActionListener(this);
		add(panel);
		
		field = makeField();
		add(field);
	}
	
	private Field makeField() {
		Field field = new Field(fieldWidth, fieldHeight, panel);
		field.setLocation(fieldX, fieldY);
		field.setLayout(null);
		return field;
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
	
}