package de.cas.view.casUI.dialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.cas.controller.IAutomatonController;
import de.cas.controller.properties.CASLanguageBundle;
import de.cas.view.casUI.component.CASJButton;
import de.cas.view.casUI.component.CASJLabel;

/**
 * 
 * @author Florian
 */
public class SetSizeJDialog extends JDialog {
	private static final long serialVersionUID = -3844798685587070198L;


	private CASJLabel lblRows;
	private CASJLabel lblColumns;
	private JTextField tfRows;
	private JTextField tfColumns;
	private CASJButton btnAccept;
	private CASJButton btnCancel;
	
	IAutomatonController controller;

	public JLabel getLblRows() {
		return lblRows;
	}

	public JLabel getLblColumns() {
		return lblColumns;
	}

	public JTextField getTfRows() {
		return tfRows;
	}

	public JTextField getTfColumns() {
		return tfColumns;
	}

	public JButton getBtnAccept() {
		return btnAccept;
	}

	public JButton getBtnCancel() {
		return btnCancel;
	}

	public SetSizeJDialog(IAutomatonController controller) {
		this(controller, null);
	}

	public SetSizeJDialog(IAutomatonController controller, JFrame parent) {
		this(controller, parent, "");
	}

	public SetSizeJDialog(IAutomatonController controller, JFrame parent, String title) {
		this(controller, parent, title, 1, 1);
	}

	public SetSizeJDialog(IAutomatonController controller, JFrame parent, String title, int oldRowCnt, int oldColsCnt) {
		super(parent, title, true);
		this.controller = controller;
		if (parent != null) {
			Dimension parentSize = parent.getSize();
			Point p = parent.getLocation();
			setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		} else {
			this.setLocationRelativeTo(null);
		}
		GridLayout gl = new GridLayout(0, 2);
		this.setLayout(gl);
		
		this.lblRows = new CASJLabel(controller, CASLanguageBundle.Property.SETSIZEJDIALOG_LBL_ROWS_TEXT);
		this.tfRows = new JTextField(4);
		this.tfRows.setText(oldRowCnt + "");
		this.lblColumns = new CASJLabel(controller, CASLanguageBundle.Property.SETSIZEJDIALOG_LBL_COLUMNS_TEXT );
		this.tfColumns = new JTextField(4);
		this.tfColumns.setText(oldColsCnt + "");
		this.btnAccept = new CASJButton(controller, CASLanguageBundle.Property.ACCEPT);
		this.btnCancel = new CASJButton(controller, CASLanguageBundle.Property.CANCEL);

		add(this.lblRows);
		add(this.tfRows);
		add(this.lblColumns);
		add(this.tfColumns);
		add(this.btnAccept);
		add(this.btnCancel);

		pack();
		//		setVisible(true);
	}

	public void setOnAcceptListener(ActionListener list) {
		this.btnAccept.addActionListener(list);
	}

	public void setOnCancelListener() {
		this.btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
	}

	public void closeAction() {
		setVisible(false);
		dispose();
	}

	public int getRows() {
		return getIntFromJTextField(this.tfRows);
	}

	public int getColumns() {
		return getIntFromJTextField(this.tfColumns);
	}

	private int getIntFromJTextField(JTextField jTextField) {
		int value = 0;
		try {
			value = Integer.parseInt(jTextField.getText());
		} catch (NumberFormatException e) {
		}
		return value;
	}
}
