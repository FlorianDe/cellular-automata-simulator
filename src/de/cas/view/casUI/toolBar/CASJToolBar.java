package de.cas.view.casUI.toolBar;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;

import de.cas.controller.IAutomatonController;
import de.cas.controller.listener.population.ClearPopulationListener;
import de.cas.controller.listener.population.RandomPopulationListener;
import de.cas.controller.listener.population.SetSizeListener;
import de.cas.controller.listener.population.TorusListener;
import de.cas.controller.listener.population.ZoomInListener;
import de.cas.controller.listener.population.ZoomOutListener;
import de.cas.controller.listener.simulation.OneStepListener;
import de.cas.controller.listener.simulation.PlayListener;
import de.cas.controller.listener.simulation.StopListener;
import de.cas.util.CstmObservable;
import de.cas.util.CstmObserver;
import de.cas.view.casUI.component.CASJSliderPanel;

public class CASJToolBar extends JToolBar implements CstmObserver{

	private static final long serialVersionUID = -4836559168569081649L;
	private JButton btnNew;
	private JButton btnOpen;
	private JButton btnSize;
	private JButton btnDelete;
	private JButton btnRandom;
	private JToggleButton btnTorus;
	private JButton btnPrint;
	private JButton btnZoomIn;
	private JButton btnZoomOut;
	private JButton btnOneStep;
	private JButton btnPlay;
	private JButton btnStop;
	
	private CASJSliderPanel speedSlider;
	public CASJSliderPanel getSpeedSlider() {
		return speedSlider;
	}


	IAutomatonController controller;
	
	public CASJToolBar(IAutomatonController controller){
		this.controller = controller;
		this.controller.getSimulationModel().addObserver(this);
		this.controller.getAutomatonModel().addObserver(this);
		
		//1. Button
		this.btnNew = (JButton) createToolbarButton(new JButton(), "img/New24.gif");
		this.add(this.btnNew);
	    
	    //2. Button
		this.btnOpen = (JButton) createToolbarButton(new JButton(), "img/Open24.gif");
		this.add(this.btnOpen);
	    this.addSeparator();
	    
	    //3. Button
		this.btnSize = (JButton) createToolbarButton(new JButton(), "img/Size24.gif");
		this.btnSize.addActionListener(new SetSizeListener(controller));
		this.add(this.btnSize);
	    
	    //4. Button
		this.btnDelete = (JButton) createToolbarButton(new JButton(), "img/Delete24.gif");
		this.btnDelete.addActionListener(new ClearPopulationListener(controller));
		this.add(this.btnDelete);
	    
	    //5. Button
		this.btnRandom = (JButton) createToolbarButton(new JButton(), "img/Random24.gif");
		this.btnRandom.addActionListener(new RandomPopulationListener(controller));
		this.add(this.btnRandom);
	    
	    //6. Button
		this.btnTorus = (JToggleButton) createToolbarButton(new JToggleButton(), "img/Torus24.gif");
		this.btnTorus.addActionListener(new TorusListener(controller));
		this.add(this.btnTorus);
	    
	    //7. Button
		this.btnPrint = (JButton) createToolbarButton(new JButton(), "img/Print24.gif");
		this.add(this.btnPrint);
	    this.addSeparator();
	    
	    //8. Button
		this.btnZoomIn = (JButton) createToolbarButton(new JButton(), "img/ZoomIn24.gif");
		this.btnZoomIn.addActionListener(new ZoomInListener(controller));
		this.add(this.btnZoomIn);
	    
	    //9. Button
		this.btnZoomOut = (JButton) createToolbarButton(new JButton(), "img/ZoomOut24.gif");
		this.btnZoomOut.addActionListener(new ZoomOutListener(controller));
		this.add(this.btnZoomOut);
		
	    this.addSeparator();
	    
	    //10. Button
		this.btnOneStep = (JButton) createToolbarButton(new JButton(), "img/StepForward24.gif");
		this.btnOneStep.addActionListener(new OneStepListener(controller));

		this.add(this.btnOneStep);
	    this.addSeparator();
	    
	    //11. Button
		this.btnPlay = (JButton) createToolbarButton(new JButton(), "img/Run24.gif");
		this.btnPlay.addActionListener(new PlayListener(controller));
		this.add(this.btnPlay);
	    
	    //12. Button
		this.btnStop = (JButton) createToolbarButton(new JButton(), "img/Stop24.gif");
		this.btnStop.addActionListener(new StopListener(controller));
		this.add(this.btnStop);
		
		//13. Slider
		speedSlider = new CASJSliderPanel(controller);
		//slider.setPaintLabels(true);
		//slider.setLabelTable(slider.createStandardLabels(50));
	    this.add(speedSlider);
	    
	    update(null, this);
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
	public void update(CstmObservable arg0, Object arg1) {
		this.btnPlay.setEnabled(!this.controller.getSimulationModel().isRunning());
		this.btnStop.setEnabled(this.controller.getSimulationModel().isRunning());
		this.btnTorus.setSelected(this.controller.getAutomatonModel().isTorus());
		this.btnZoomOut.setEnabled(!this.controller.getPopulationModel().isMinimum());
		this.revalidate();
		this.repaint();
	}
}
