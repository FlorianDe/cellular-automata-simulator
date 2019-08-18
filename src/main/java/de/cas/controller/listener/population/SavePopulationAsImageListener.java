package de.cas.controller.listener.population;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.cas.controller.IAutomatonController;
import de.cas.controller.properties.CASSettings;
import de.cas.controller.properties.CASSettings.Property;
import de.cas.view.casUI.component.CASImageSaveExtensionPanel;
import de.cas.view.casUI.dialog.CASFileChooser;
import de.cas.view.casUI.util.CASFileFilter;

public class SavePopulationAsImageListener implements ActionListener {
	protected IAutomatonController controller;

	public SavePopulationAsImageListener(IAutomatonController controller) {
		this.controller = controller;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		savePopulationAsImage();
	}

	public void savePopulationAsImage() {
		int fontHeight = 10;
		
		File imgExportDir = new File(CASSettings.getInstance().getProperty(Property.POPULATION_IMAGE_EXPORT_PATH));
		CASImageSaveExtensionPanel isep = new CASImageSaveExtensionPanel(fontHeight);
		CASFileChooser filechooser = new CASFileChooser(controller, imgExportDir, false, isep, CASFileFilter.gifSuffixFilter, CASFileFilter.pngSuffixFilter);
		int returnval = filechooser.showSaveDialog(this.controller.getView());
		if (returnval == JFileChooser.APPROVE_OPTION) {
			Dimension size = this.controller.getView().getPopulationPanel().getPreferredSize();
			try {
				int value = Integer.parseInt(isep.getTbFontSize().getText());
				if(value>0){
					fontHeight = value;
				}
			} catch (NumberFormatException e) {}
			int yOffsetBottom = 0;
			int yOffsetTop = 0;
			int margin = this.controller.getPopulationModel().getMargin();
			int populationSize = (int)(size.getHeight() - 2*margin);
			int minFontSpacingTopBottom = 1;
			int offset = Math.max(fontHeight+2*minFontSpacingTopBottom, margin);
			
			
			if(isep.getCbxAutomatonName().isSelected()){
				yOffsetTop += offset;
			}
			if(isep.getCbxTimeStamp().isSelected()){
				yOffsetBottom += offset;
			}

			BufferedImage img = new BufferedImage((int) size.getWidth(), (int)(populationSize+yOffsetTop+yOffsetBottom), BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D)img.getGraphics();
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
			this.controller.getView().getPopulationPanel().paintFullPopulation(g2d, offset-margin);

			g2d.setColor(Color.BLACK);
			Font currentFont = g2d.getFont();
			Font newFont = currentFont.deriveFont((float)fontHeight);
			g2d.setFont(newFont);
			if(isep.getCbxAutomatonName().isSelected()){
				g2d.drawString(this.controller.getAutomatonModel().getClass().getSimpleName(), margin, yOffsetTop-((offset-fontHeight)/2));
			}
			if(isep.getCbxTimeStamp().isSelected()){
				g2d.drawString(new Date().toString(), margin, (int) (populationSize+yOffsetTop+yOffsetBottom-((offset-fontHeight)/2)));
			}
			g2d.dispose();

			String extension = "";
			File toFile = filechooser.getSelectedFile();
		    if (filechooser.getFileFilter() instanceof FileNameExtensionFilter) {
		        String[] exts = ((FileNameExtensionFilter)filechooser.getFileFilter()).getExtensions();
		        String nameLower = toFile.getName().toLowerCase();
		        boolean extensionAllreadyAppended = false;
		        for (String ext : exts) { // check if it already has a valid extension
		            if (nameLower.endsWith('.' + ext.toLowerCase())) {
		            	extension = ext.toLowerCase();
		            	extensionAllreadyAppended = true;
		            	break;
		            }
		        }
		        if(!extensionAllreadyAppended){
		        	toFile = new File(toFile.toString() + '.' + exts[0]);
		        	extension = exts[0];
		        }
		    }
			
			try {
				ImageIO.write(img, extension, toFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
