package de.cas.view.casUI.casToolBar;

import java.awt.Insets;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;

public class CASToolbar extends JToolBar {

	private static final long serialVersionUID = -4836559168569081649L;
	private JButton btn;
	
	public CASToolbar(){

		//1. Button
		this.btn = createToolbarButton("img/New24.gif");
		this.add(this.btn);
	    
	    //2. Button
		this.btn = createToolbarButton("img/Open24.gif");
		this.add(this.btn);
	    this.addSeparator();
	    
	    //3. Button
		this.btn = createToolbarButton("img/Size24.gif");
		this.add(this.btn);
	    
	    //4. Button
		this.btn = createToolbarButton("img/Delete24.gif");
		this.add(this.btn);
	    
	    //5. Button
		this.btn = createToolbarButton("img/Random24.gif");
		this.add(this.btn);
	    
	    //6. Button
		this.btn = createToolbarButton("img/Torus24.gif");
		this.add(this.btn);
	    
	    //7. Button
		this.btn = createToolbarButton("img/Print24.gif");
		this.add(this.btn);
	    this.addSeparator();
	    
	    //8. Button
		this.btn = createToolbarButton("img/ZoomIn24.gif");
		this.add(this.btn);
	    
	    //9. Button
		this.btn = createToolbarButton("img/ZoomOut24.gif");
		this.add(this.btn);
	    this.addSeparator();
	    
	    //10. Button
		this.btn = createToolbarButton("img/StepForward24.gif");
		this.add(this.btn);
	    this.addSeparator();
	    
	    //11. Button
		this.btn = createToolbarButton("img/Run24.gif");
		this.add(this.btn);
	    
	    //12. Button
		this.btn = createToolbarButton("img/Stop24.gif");
		this.add(this.btn);
		
		//13. Slider
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
	    slider.setMinorTickSpacing(5);
		slider.setMajorTickSpacing(50);
		slider.setPaintTicks(true);
		//slider.setPaintLabels(true);
		//slider.setLabelTable(slider.createStandardLabels(50));
	    this.add(slider);
	}
	
	public JButton createToolbarButton(String filePath){
		JButton btn = new JButton();
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
}
