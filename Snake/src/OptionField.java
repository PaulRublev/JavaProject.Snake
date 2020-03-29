import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JRadioButton;

public class OptionField extends JComponent implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	OptionListener optionListener;
	private final String confirmButtonText = "OK";
	
	OptionField(int width, int height, OptionListener optionListener) {
		this.optionListener = optionListener;
		setSize(width, height);
	
		JRadioButton defaultViewButton = new JRadioButton(Views.DEFAULT.toString().toLowerCase(), true);
		defaultViewButton.setActionCommand(Views.DEFAULT.toString());
		defaultViewButton.addActionListener(this);
		defaultViewButton.setLocation(10, 40);
		defaultViewButton.setSize(100, 30);
		
		JRadioButton oneCustomViewButton = new JRadioButton(Views.ONE.toString().toLowerCase(), true);
		oneCustomViewButton.setActionCommand(Views.ONE.toString());
		oneCustomViewButton.setLocation(10, 70);
		oneCustomViewButton.setSize(100, 30);
		oneCustomViewButton.addActionListener(this);
		
		JRadioButton twoCustomViewButton = new JRadioButton(Views.TWO.toString().toLowerCase(), true);
		twoCustomViewButton.setActionCommand(Views.TWO.toString());
		twoCustomViewButton.setLocation(10, 100);
		twoCustomViewButton.setSize(100, 30);
		twoCustomViewButton.addActionListener(this);
		twoCustomViewButton.setEnabled(false);
		
		ButtonGroup viewButtonGroup = new ButtonGroup();
		viewButtonGroup.add(defaultViewButton);
		viewButtonGroup.add(oneCustomViewButton);
		viewButtonGroup.add(twoCustomViewButton);
		
		add(defaultViewButton);
		add(oneCustomViewButton);
		add(twoCustomViewButton);
		
		JButton confirmButton = new JButton(confirmButtonText);
		confirmButton.setActionCommand(confirmButtonText);
		confirmButton.setLocation(170, 310);
		confirmButton.setSize(60, 30);
		confirmButton.addActionListener(this);
		add(confirmButton);
	}
	
	public void actionPerformed(ActionEvent e) {
		String actionCommandString = e.getActionCommand();
		if (actionCommandString.equalsIgnoreCase(confirmButtonText)) {
			optionListener.confirmOptions();
		} else {
			Config.changeView(e.getActionCommand());
			optionListener.workWithOptions();
		}
	}
	
}
