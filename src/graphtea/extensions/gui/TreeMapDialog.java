package graphtea.extensions.gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * This dialog enables the choose of a country to load the specific locations of
 * Vertexes.
 * 
 * @author Sebastian Glass
 * @since 04.05.2015
 * 
 * 
 *
 */
public class TreeMapDialog extends JDialog {

	private static final long serialVersionUID = 8828381029045899188L;
	private TMSettingContainer setting = new TMSettingContainer();
	private JSlider slider;
	private JLabel lblNumberOfSubtrees;
	JCheckBox checkBox;
	JCheckBox chckbxUseLogarithm;
	JSpinner spinner;
	JButton colorButton;

	
	public TreeMapDialog() {
		
		setDefaultFrameSettings();
		TreeMapListener treeActionListener = new TreeMapListener(this);
		generateComboBox(treeActionListener);
		addJButtons(treeActionListener);

		JLabel lblWhichCountryDo = new JLabel(
				"Select the country you want for mapping:");
		lblWhichCountryDo.setBounds(18, 11, 366, 14);
		getContentPane().add(lblWhichCountryDo);

		JLabel lblNoteNewCountries = new JLabel(
				"Note: new countries can be added in './maps'");
		lblNoteNewCountries.setBounds(18, 72, 366, 14);
		getContentPane().add(lblNoteNewCountries);
		
		lblNumberOfSubtrees = new JLabel("number of subtrees: k=2");
		lblNumberOfSubtrees.setBounds(18, 97, 168, 14);
		getContentPane().add(lblNumberOfSubtrees);
		
		slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				lblNumberOfSubtrees.setText("Number of Subtrees: k="+((JSlider)arg0.getSource()).getValue());
			}
		});
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setValue(2);
		slider.setBounds(164, 97, 188, 30);
		getContentPane().add(slider);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Norm options", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(22, 122, 205, 107);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		checkBox = new JCheckBox("convert in percent");
		checkBox.setActionCommand("percetCheckBox");
		checkBox.setBounds(10, 21, 149, 23);
		checkBox.addActionListener(treeActionListener);
		panel.add(checkBox);
		
		chckbxUseLogarithm = new JCheckBox("use logarithm");
		chckbxUseLogarithm.setActionCommand("logCheckBox");
		chckbxUseLogarithm.addActionListener(treeActionListener);
		chckbxUseLogarithm.setBounds(10, 47, 149, 23);
		chckbxUseLogarithm.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(chckbxUseLogarithm);
		
		JLabel label = new JLabel("Normfactor:");
		label.setBounds(10, 80, 149, 14);
		panel.add(label);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(10.0, 0.001, 100.0, 0.001));
		spinner.setBounds(136, 77, 59, 20);
		panel.add(spinner);
		
		colorButton = new JButton("Color");
		colorButton.addActionListener(treeActionListener);
		colorButton.setActionCommand("Color");
		colorButton.setBounds(237, 131, 115, 23);
		getContentPane().add(colorButton);
		
		

		setting.setMappingCode(1);
	}

	private void addJButtons(TreeMapListener treeActionListener) {
		JButton btn = new JButton("Go!");

		btn.setBounds(237, 165, 115, 25);
		btn.setActionCommand("GoButton");

		btn.addActionListener(treeActionListener);
		getContentPane().add(btn);
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setActionCommand("Cancel");
		btn.addActionListener(treeActionListener);
		btnCancel.setBounds(237, 201, 115, 25);
		getContentPane().add(btnCancel);
	}


	private void generateComboBox(ActionListener treeActionListener) {
		// Get Map List
		String[] s = new File("./maps").list();
		JComboBox<String> cbx = new JComboBox<String>();
		for (String string : s) {
			
			cbx.addItem(string);
		}
		cbx.setBounds(18, 36, 334, 25);
		cbx.setActionCommand("MapComboBox");
		cbx.addActionListener(treeActionListener);
		getContentPane().add(cbx);
		setting.setMap((String) (cbx).getSelectedItem());

	}

	private void setDefaultFrameSettings() {
		setTitle("\"Tree to Map\"-Mapper");
		setBounds(100, 100, 368, 266);
		setModal(true);
		getContentPane().setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public static TMSettingContainer showDialog() {
		TreeMapDialog tmd = new TreeMapDialog();
		tmd.setVisible(true);
		return tmd.setting;
	}

	@Override
	public void dispose() {

		setting = null;
		super.dispose();
	}

	public TMSettingContainer getSetting() {
		setting.setK(slider.getValue());
		return setting;
	}

	public void setSetting(TMSettingContainer setting) {
		this.setting = setting;
	}
}

class TreeMapListener implements ActionListener {

	private TreeMapDialog source;

	public TreeMapListener(TreeMapDialog treeMapDialog) {
		source = treeMapDialog;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		TMSettingContainer sc = source.getSetting();
		switch (e.getActionCommand()) {
		case "Color":
			Color selectedColor = JColorChooser.showDialog(source, "Pick a Color"
	                , source.colorButton.getForeground());
			source.colorButton.setForeground(selectedColor);
		case "MapComboBox":
			// Checked, but java... ._.
			sc.setMap((String) ((JComboBox<String>) e.getSource())
					.getSelectedItem());
			break;
		case "percetCheckBox":
		case "logCheckBox":
				int norm = (source.checkBox.isSelected()?1:0) + (source.chckbxUseLogarithm.isSelected()?2:0);
				sc.setNorm(norm);
			break;
		case "GoButton":
			sc.setFactor((double) source.spinner.getValue()); 
			sc.setColor(source.colorButton.getForeground());
			source.setVisible(false);
			break;
		case "Cancel":
			sc = null;
			source.setVisible(false);
			break;
		default:
			break;
		}
		source.setSetting(sc);
	}
	


}