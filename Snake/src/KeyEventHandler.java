import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class KeyEventHandler extends KeyAdapter {
	
	private MoveListener moveListener;
	
	KeyEventHandler(MoveListener moveListener) {
		this.moveListener = moveListener;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		Directions direction;
		switch (e.getExtendedKeyCode()) {
		case 37: // Left
			direction = Directions.LEFT;
			moveListener.snakeMove(direction);
			break;
		case 38: // Up
			direction = Directions.UP;
			moveListener.snakeMove(direction);
			break;
		case 39: // Right
			direction = Directions.RIGHT;
			moveListener.snakeMove(direction);
			break;
		case 40: // Down
			direction = Directions.DOWN;
			moveListener.snakeMove(direction);
			break;
		}
	}
	
}
