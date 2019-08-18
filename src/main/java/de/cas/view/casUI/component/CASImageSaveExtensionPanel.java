package de.cas.view.casUI.component;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CASImageSaveExtensionPanel extends JPanel {
	private static final long serialVersionUID = 2220732871568118181L;
	
	private JCheckBox cbxAutomatonName;
	private JCheckBox cbxTimeStamp;
	private JTextField tbFontSize;

	public CASImageSaveExtensionPanel(int fontHeight){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new EmptyBorder(5,5,5,5));
		JLabel extensionPanelLabel = new JLabel("Paint settings:");
		this.cbxAutomatonName = new JCheckBox("Automaton name");
		this.cbxAutomatonName.setSelected(true);
		this.cbxTimeStamp = new JCheckBox("Timestamp");
		this.cbxTimeStamp.setSelected(true);
		JLabel fontSizeLabel = new JLabel("Font Size");
		this.tbFontSize = new JTextField(3);
		this.tbFontSize.setText(""+fontHeight);
		
		this.add(extensionPanelLabel);
		this.add(cbxAutomatonName);
		this.add(cbxTimeStamp);
		this.add(fontSizeLabel);
		this.add(tbFontSize);
		
	}

	public JCheckBox getCbxAutomatonName() {
		return cbxAutomatonName;
	}

	public JCheckBox getCbxTimeStamp() {
		return cbxTimeStamp;
	}

	public JTextField getTbFontSize() {
		return tbFontSize;
	}
	
	
}
