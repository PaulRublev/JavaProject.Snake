import javax.swing.JFrame;

class Frame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private int frameWidth = 450;
	private int frameHeight = 400;
	private int frameLocationX = 300;
	private int frameLocationY = 300;
	private int fieldWidth = 400;
	private int fieldHeight = 350;
	
	Frame() {
		setBounds(frameLocationX, frameLocationY, frameWidth, frameHeight);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		
		Field field = new Field(fieldWidth, fieldHeight);
		add(field);
		repaint();
	}
	
}