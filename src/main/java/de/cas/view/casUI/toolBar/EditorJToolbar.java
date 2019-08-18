package de.cas.view.casUI.toolBar;

import java.awt.Insets;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;

import de.cas.controller.IAutomatonController;
import de.cas.controller.listener.editor.CompileFileListener;
import de.cas.util.CstmObservable;
import de.cas.util.CstmObserver;

public class EditorJToolbar  extends JToolBar implements CstmObserver{

	private static final long serialVersionUID = 8730026987000261325L;
	private JButton btnCompile;
	IAutomatonController controller;
	
	public EditorJToolbar(IAutomatonController controller){
		this.controller = controller;
		this.addToObserverable();
		
		//1. Button
		this.btnCompile = (JButton) createToolbarButton(new JButton(), "img/Compile24.gif");
		this.btnCompile.addActionListener(new CompileFileListener(controller));
		
		this.add(this.btnCompile);
	}
	
	
	public AbstractButton createToolbarButton(AbstractButton btn, String filePath){
		try {	
			//ImageIcon imageIcon = new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResource(filePath)));
			//Image image = imageIcon.getImage(); // transform it 
			//Image newimg = image.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			//imageIcon = new ImageIcon(newimg);  // transform it back
			ImageIcon imageIcon = new ImageIcon(ImageIO.read(this.getClass().getClassLoader().getResource(filePath)));
			btn.setIcon(imageIcon);

		} catch (Exception e) {
			String str = filePath.replace("img/", "");
			btn.setText(str.substring(0, str.indexOf(".")).toUpperCase());
		}
		finally{
			btn.setMargin(new Insets(0, 0, 0, 0));
			//btn.setPreferredSize(new Dimension(25,25));
			//btn.setBackground(Color.LIGHT_GRAY);
			btn.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		}
		return btn;
	}
	
	@Override
	public void update(CstmObservable o, Object arg) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeFromObserverable() {
		this.controller.getSimulationModel().deleteObserver(this);
		this.controller.getAutomatonModel().deleteObserver(this);
	}


	@Override
	public void addToObserverable() {
		this.controller.getView().getObservers().add(this);
		this.controller.getSimulationModel().addObserver(this);
		this.controller.getAutomatonModel().addObserver(this);	
	}
}
