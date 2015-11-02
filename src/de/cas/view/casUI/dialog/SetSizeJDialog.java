package de.cas.view.casUI.dialog;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 
 * @author Florian
 */
public class SetSizeJDialog extends JDialog {
	private static final long serialVersionUID = -3844798685587070198L;

	private String str_lblRows = "Number of rows";
	private String str_lblColumns = "Number of columns";
	private String str_okBtn = "Ok";
	private String str_cancelBtn = "Cancel";

	private JLabel lblRows;
	private JLabel lblColumns;
	private JTextField tfRows;
	private JTextField tfColumns;
	private JButton btnAccept;
	private JButton btnCancel;

	public String getStr_lblRows() {
		return str_lblRows;
	}

	public String getStr_lblColumns() {
		return str_lblColumns;
	}

	public String getStr_okBtn() {
		return str_okBtn;
	}

	public String getStr_cancelBtn() {
		return str_cancelBtn;
	}

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

	public SetSizeJDialog() {
		this(null);
	}

	public SetSizeJDialog(JFrame parent) {
		this(parent, "");
	}

	public SetSizeJDialog(JFrame parent, String title) {
		this(parent, title, 1, 1);
	}

	public SetSizeJDialog(JFrame parent, String title, int oldRowCnt, int oldColsCnt) {
		super(parent, title, true);
		if (parent != null) {
			Dimension parentSize = parent.getSize();
			Point p = parent.getLocation();
			setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		} else {
			this.setLocationRelativeTo(null);
		}
		GridLayout gl = new GridLayout(0, 2);
		this.setLayout(gl);

		this.lblRows = new JLabel(str_lblRows);
		this.tfRows = new JTextField(4);
		this.tfRows.setText(oldRowCnt + "");
		this.lblColumns = new JLabel(str_lblColumns);
		this.tfColumns = new JTextField(4);
		this.tfColumns.setText(oldColsCnt + "");
		this.btnAccept = new JButton(str_okBtn);
		this.btnCancel = new JButton(str_cancelBtn);

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
		System.out.println("1");
	}

	public void setOnCancelListener() {
		System.out.println("1");
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

	public static SetSizeJDialog ssjd;
	// For testing only!
	public static void main(String[] a) {

		EventQueue.invokeLater(new Runnable() {
		    @Override
		    public void run() {
		    	ssjd = new SetSizeJDialog();
		    	ssjd.setOnCancelListener();
		    }
		});
		
		
	}
}
