import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RightPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
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
	}
	
}
