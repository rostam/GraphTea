package graphtea.extensions.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

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
		lblNoteNewCountries.setBounds(18, 113, 366, 14);
		getContentPane().add(lblNoteNewCountries);
		
		lblNumberOfSubtrees = new JLabel("Number of Subtrees: k=2");
		lblNumberOfSubtrees.setBounds(18, 72, 168, 14);
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
		slider.setBounds(196, 72, 188, 30);
		getContentPane().add(slider);
		
		

		setting.setMappingCode(1);
	}

	private void addJButtons(TreeMapListener treeActionListener) {
		JButton btn = new JButton("Go!");

		btn.setBounds(281, 138, 100, 25);
		btn.setActionCommand("GoButton");

		btn.addActionListener(treeActionListener);
		getContentPane().add(btn);
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setActionCommand("Cancel");
		btn.addActionListener(treeActionListener);
		btnCancel.setBounds(171, 138, 100, 25);
		getContentPane().add(btnCancel);
	}


	private void generateComboBox(ActionListener treeActionListener) {
		// Get Map List
		String[] s = new File("./maps").list();
		JComboBox<String> cbx = new JComboBox<String>();
		for (String string : s) {
			
			cbx.addItem(string);
		}
		cbx.setBounds(18, 36, 366, 25);
		cbx.setActionCommand("MapComboBox");
		cbx.addActionListener(treeActionListener);
		getContentPane().add(cbx);
		setting.setMap((String) (cbx).getSelectedItem());

	}

	private void setDefaultFrameSettings() {
		setTitle("\"Tree to Map\"-Mapper");
		setBounds(100, 100, 400, 202);
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
		case "MapComboBox":
			// Checked, but java... ._.
			sc.setMap((String) ((JComboBox<String>) e.getSource())
					.getSelectedItem());
			break;
		case "GoButton":
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