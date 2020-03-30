import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class OptionField extends JComponent implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private OptionListener optionListener;
	private final String confirmButtonText = "OK";
	private JCheckBox fileEnabledCheckBox;
	private JCheckBox fileToDeleteCheckBox;
	private JLabel titleLabel;
	private JLabel explanationLabel;
	
	OptionField(int width, int height, OptionListener optionListener) {
		this.optionListener = optionListener;
		setSize(width, height);
		
		titleLabel = new JLabel(Config.getView(Strings.SETTINGS));
		titleLabel.setLocation(150, 10);
		titleLabel.setSize(140, 40);
		titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
		add(titleLabel);
		
		explanationLabel = new JLabel(Config.getView(Strings.CHOOSE_VIEW));
		explanationLabel.setLocation(10, 50);
		explanationLabel.setSize(100, 20);
		add(explanationLabel);
	
		JRadioButton defaultViewButton = new JRadioButton(Views.DEFAULT.toString().toLowerCase(), true);
		defaultViewButton.setActionCommand(Views.DEFAULT.toString());
		defaultViewButton.addActionListener(this);
		defaultViewButton.setLocation(10, 80);
		defaultViewButton.setSize(100, 30);
		
		JRadioButton oneCustomViewButton = new JRadioButton(Views.ONE.toString().toLowerCase(), false);
		oneCustomViewButton.setActionCommand(Views.ONE.toString());
		oneCustomViewButton.setLocation(10, 110);
		oneCustomViewButton.setSize(100, 30);
		oneCustomViewButton.addActionListener(this);
		
		JRadioButton twoCustomViewButton = new JRadioButton(Views.TWO.toString().toLowerCase(), false);
		twoCustomViewButton.setActionCommand(Views.TWO.toString());
		twoCustomViewButton.setLocation(10, 140);
		twoCustomViewButton.setSize(100, 30);
		twoCustomViewButton.addActionListener(this);
		twoCustomViewButton.setEnabled(false);
		
		ButtonGroup viewButtonGroup = new ButtonGroup();
		viewButtonGroup.add(defaultViewButton);
		viewButtonGroup.add(oneCustomViewButton);
		viewButtonGroup.add(twoCustomViewButton);
		
		switch (Config.viewConfiguration) {
		case DEFAULT:
			defaultViewButton.setSelected(true);
			break;
		case ONE:
			oneCustomViewButton.setSelected(true);
			break;
		case TWO:
			twoCustomViewButton.setSelected(true);
			break;
		default:
			break;
		}
		
		add(defaultViewButton);
		add(oneCustomViewButton);
		add(twoCustomViewButton);
		
		fileToDeleteCheckBox = new JCheckBox(Config.getView(Strings.DEL));
		fileToDeleteCheckBox.setLocation(10, 305);
		fileToDeleteCheckBox.setSize(200, 20);
		fileToDeleteCheckBox.setEnabled(false);
		fileToDeleteCheckBox.setSelected(false);
		fileToDeleteCheckBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED) {
					fileEnabledCheckBox.setEnabled(true);
					Config.fileToDelete = false;
				} else if (e.getStateChange() == ItemEvent.SELECTED) {
					fileEnabledCheckBox.setEnabled(false);
					Config.fileToDelete = true;
				}
			}
		});
		add(fileToDeleteCheckBox);
		
		fileEnabledCheckBox = new JCheckBox(Config.getView(Strings.SAVE));
		fileEnabledCheckBox.setLocation(10, 325);
		fileEnabledCheckBox.setSize(200, 20);
		fileEnabledCheckBox.setSelected(Config.fileEnabled);
		fileEnabledCheckBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (fileEnabledCheckBox == e.getItemSelectable()) {
					if (e.getStateChange() == ItemEvent.DESELECTED) {
						Config.fileEnabled = false;
						if (Config.fileExists(Config.fileName)) {
							fileToDeleteCheckBox.setEnabled(true);
						}
					} else if (e.getStateChange() == ItemEvent.SELECTED) {
						Config.fileEnabled = true;
						fileToDeleteCheckBox.setEnabled(false);
						fileToDeleteCheckBox.setSelected(false);
					}
				}
			}
		});
		add(fileEnabledCheckBox);
		
		JButton confirmButton = new JButton(confirmButtonText);
		confirmButton.setActionCommand(confirmButtonText);
		confirmButton.setLocation(220, 310);
		confirmButton.setSize(60, 30);
		confirmButton.addActionListener(this);
		add(confirmButton);
	}
	
	public void actionPerformed(ActionEvent e) {
		String actionCommandString = e.getActionCommand();
		if (actionCommandString.equalsIgnoreCase(confirmButtonText)) {
			optionListener.confirmOptions();
			try {
				File file = new File(Config.fileName);
				if (Config.fileEnabled) {
					Config.fillConfigFile(file);
				} else if (Config.fileToDelete) {
					file.delete();
					System.out.print("?");
				}
			} catch (Exception exception) {
				System.out.println("File not found. " + exception);
			}
		} else {
			Config.changeView(e.getActionCommand());
			optionListener.workWithOptions();
			fileEnabledCheckBox.setText(Config.getView(Strings.SAVE));
			fileToDeleteCheckBox.setText(Config.getView(Strings.DEL));
			titleLabel.setText(Config.getView(Strings.SETTINGS));
			explanationLabel.setText(Config.getView(Strings.CHOOSE_VIEW));
		}
	}
	
}
