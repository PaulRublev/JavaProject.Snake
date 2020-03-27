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
	void saveMaxScore();
}

class RightPanel extends JPanel implements ScoreListener {

	private static final long serialVersionUID = 1L;
	private JLabel scoreLabel;
	private JLabel maxScoreLabel;
	private int initialScore = 0;
	private int maxScore = 0;
	private int currentScore = 0;
	
	RightPanel() {
		
		Wall wallExample = new Wall(new Point(20, 20), 
				new Dimension(2 * AnyObject.defaultThickness, AnyObject.defaultThickness));
		add(wallExample);
		
		JLabel wallExplanationLabel = new JLabel("Wall");
		wallExplanationLabel.setBounds(20, 30, 60, 20);
		add(wallExplanationLabel);
		
		Food foodExample = new Food(new Point(20, 60));
		add(foodExample);
		
		JLabel foodExplanationLabel = new JLabel("Food");
		foodExplanationLabel.setBounds(20, 70, 60, 20);
		add(foodExplanationLabel);
		
		Snake snakeExample = new Snake(new Point(20, 100), Directions.RIGHT);
		for (AnyObject snakeParts : snakeExample.snakeBody) {
			add(snakeParts);
		}
		
		JLabel snakeExplanationLabel = new JLabel("Snake");
		snakeExplanationLabel.setBounds(20, 110, 60, 20);
		add(snakeExplanationLabel);
		
		JLabel scoreExplanationLabel = new JLabel("Score:");
		scoreExplanationLabel.setBounds(20, 150, 60, 20);
		scoreExplanationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(scoreExplanationLabel);
		
		scoreLabel = new JLabel(String.valueOf(initialScore), SwingConstants.CENTER);
		scoreLabel.setBounds(20, 170, 60, 40);
		scoreLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		scoreLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
		add(scoreLabel);
		
		JLabel maxScoreExplanationLabel = new JLabel("Record:");
		maxScoreExplanationLabel.setBounds(20, 210, 60, 20);
		maxScoreExplanationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(maxScoreExplanationLabel);
		
		maxScoreLabel = new JLabel(String.valueOf(maxScore), SwingConstants.CENTER);
		maxScoreLabel.setBounds(20, 230, 60, 40);
		maxScoreLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		maxScoreLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
		add(maxScoreLabel);
	}
	
	public void refreshScore(int score) {
		this.currentScore = score;
		scoreLabel.setText(String.valueOf(score));
	}
	
	public void saveMaxScore() {
		if (currentScore > maxScore) {
			maxScore = currentScore;
			maxScoreLabel.setText(String.valueOf(maxScore));
		}
	}
	
}
