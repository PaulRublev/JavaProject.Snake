import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

interface ScoreListener {
	void refreshScore(int score);
}

public class RightPanel extends JPanel implements ScoreListener {

	private static final long serialVersionUID = 1L;
	private JLabel scoreLabel;
	private int initialScore = 0;
	
	RightPanel(int x, int y, int width, int height) {
		setSize(width, height);
		setLocation(x, y);
		setFocusable(false);
		setLayout(null);
		setBorder(BorderFactory.createLineBorder(Color.black, 1));
		setVisible(true);
		
		new Wall().makeWall(new Point(20, 20), new Dimension(2 * AnyObject.defaultThickness,
				AnyObject.defaultThickness), this);
		JLabel explanationLabelWall = new JLabel("Wall");
		explanationLabelWall.setBounds(20, 30, 60, 20);
		add(explanationLabelWall);
		
		new Food(this, new Point(20, 60));
		JLabel explanationLabelFood = new JLabel("Food");
		explanationLabelFood.setBounds(20, 70, 60, 20);
		add(explanationLabelFood);
		
		new Snake(this, new Point(20, 100));
		JLabel explanationLabelSnake = new JLabel("Snake");
		explanationLabelSnake.setBounds(20, 110, 60, 20);
		add(explanationLabelSnake);
		
		JLabel explanationLabelScore = new JLabel("Score:");
		explanationLabelScore.setBounds(20, 150, 60, 20);
		explanationLabelScore.setHorizontalAlignment(SwingConstants.CENTER);
		add(explanationLabelScore);
		
		scoreLabel = new JLabel(String.valueOf(initialScore), SwingConstants.CENTER);
		scoreLabel.setBounds(20, 170, 60, 40);
		scoreLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		scoreLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
		add(scoreLabel);
	}
	
	public void refreshScore(int score) {
		scoreLabel.setText(String.valueOf(score));
	}
	
}
