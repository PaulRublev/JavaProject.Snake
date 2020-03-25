import javax.swing.JFrame;

class Frame extends JFrame {
	
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
	RightPanel 	panel;
	Field 		field;
	
	Frame() {
		setBounds(frameLocationX, frameLocationY, frameWidth, frameHeight);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		
		panel = new RightPanel(rightPanelX, rightPanelY, rightPanelWidth, rightPanelHeight);
		add(panel);
		
		field = new Field(fieldX, fieldY, fieldWidth, fieldHeight, panel);
		add(field);
		
		repaint();
	}
	
}