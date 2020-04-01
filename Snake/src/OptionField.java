import java.awt.Font;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

public class OptionField extends JComponent implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private OptionListener optionListener;
	private final String confirmButtonText = "OK";
	private JCheckBox fileEnabledCheckBox;
	private JCheckBox fileToDeleteCheckBox;
	private JLabel titleLabel;
	private JLabel viewExplanationLabel;
	private JLabel langExplanationLabel;
	
	OptionField(int width, int height, OptionListener optionListener) {
		this.optionListener = optionListener;
		setSize(width, height);
		
		titleLabel = new JLabel(Config.getLang(Strings.SETTINGS));
		titleLabel.setLocation(150, 10);
		titleLabel.setSize(140, 40);
		titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
		add(titleLabel);
		
		viewExplanationLabel = new JLabel(Config.getLang(Strings.CHOOSE_VIEW));
		viewExplanationLabel.setLocation(10, 60);
		viewExplanationLabel.setSize(100, 20);
		add(viewExplanationLabel);
	
		JRadioButton defaultViewButton = new JRadioButton(Views.DEFAULT.toString().toLowerCase(), true);
		defaultViewButton.setActionCommand(Views.DEFAULT.toString());
		defaultViewButton.addActionListener(this);
		defaultViewButton.setLocation(10, 80);
		defaultViewButton.setSize(100, 20);
		
		JRadioButton oneCustomViewButton = new JRadioButton(Views.ONE.toString().toLowerCase(), false);
		oneCustomViewButton.setActionCommand(Views.ONE.toString());
		oneCustomViewButton.setLocation(10, 100);
		oneCustomViewButton.setSize(100, 20);
		oneCustomViewButton.addActionListener(this);
		
		JRadioButton twoCustomViewButton = new JRadioButton(Views.TWO.toString().toLowerCase(), false);
		twoCustomViewButton.setActionCommand(Views.TWO.toString());
		twoCustomViewButton.setLocation(10, 120);
		twoCustomViewButton.setSize(100, 20);
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
		
		langExplanationLabel = new JLabel(Config.getLang(Strings.CHOOSE_LANG));
		langExplanationLabel.setLocation(140, 60);
		langExplanationLabel.setSize(140, 20);
		add(langExplanationLabel);
		
		ActionListener langListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Config.changeLang(e.getActionCommand());
				optionListener.workWithOptions();
				fileEnabledCheckBox.setText(Config.getLang(Strings.SAVE));
				fileToDeleteCheckBox.setText(Config.getLang(Strings.DEL));
				titleLabel.setText(Config.getLang(Strings.SETTINGS));
				viewExplanationLabel.setText(Config.getLang(Strings.CHOOSE_VIEW));
				langExplanationLabel.setText(Config.getLang(Strings.CHOOSE_LANG));
			}
		};
		
		JRadioButton enRadioButton = new JRadioButton(Languages.EN.toString().toLowerCase(), true);
		enRadioButton.setActionCommand(Languages.EN.toString());
		enRadioButton.setLocation(140, 80);
		enRadioButton.setSize(100, 20);
		enRadioButton.addActionListener(langListener);
		
		JRadioButton ruRadioButton = new JRadioButton(Languages.RU.toString().toLowerCase(), false);
		ruRadioButton.setActionCommand(Languages.RU.toString());
		ruRadioButton.setLocation(140, 100);
		ruRadioButton.setSize(100, 20);
		ruRadioButton.addActionListener(langListener);
		
		ButtonGroup langButtonGroup = new ButtonGroup();
		langButtonGroup.add(enRadioButton);
		langButtonGroup.add(ruRadioButton);
		
		switch (Config.langConfiguration) {
		case EN:
			enRadioButton.setSelected(true);
			break;
		case RU:
			ruRadioButton.setSelected(true);
			break;
		default:
			break;
		}
		
		add(ruRadioButton);
		add(enRadioButton);
		
		fileToDeleteCheckBox = new JCheckBox(Config.getLang(Strings.DEL));
		fileToDeleteCheckBox.setLocation(10, 305);
		fileToDeleteCheckBox.setSize(240, 20);
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
		
		fileEnabledCheckBox = new JCheckBox(Config.getLang(Strings.SAVE));
		fileEnabledCheckBox.setLocation(10, 325);
		fileEnabledCheckBox.setSize(240, 20);
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
		confirmButton.setLocation(320, 310);
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
				}
			} catch (Exception exception) {
				System.out.println("File not found. " + exception);
			}
		} else {
			Config.changeView(e.getActionCommand());
			optionListener.workWithOptions();
		}
	}
	
}
