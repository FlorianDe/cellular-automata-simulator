package de.cas.controller.listener.population;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import de.cas.controller.IAutomatonController;
import de.cas.model.AutomatonData;
import de.cas.model.Cell;
import de.cas.util.loader.CstmClassloader;
import de.cas.view.casUI.dialog.CASFileChooser;
import de.cas.view.casUI.util.CASFileFilter;

public class DeserializePopulationListener implements ActionListener {
	protected IAutomatonController controller;
	protected FileNameExtensionFilter fileFilter;

	public DeserializePopulationListener(IAutomatonController controller, FileNameExtensionFilter fileFilter) {
		this.controller = controller;
		this.fileFilter = fileFilter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CASFileChooser cfc = new CASFileChooser(this.controller, CstmClassloader.getPopulationFolder(), false, null, fileFilter);

		int result = cfc.showOpenDialog(this.controller.getView());
		switch (result) {
		case JFileChooser.APPROVE_OPTION:
			File file = cfc.getSelectedFile();
			FileInputStream fs = null;
			ObjectInputStream is = null;
			try {
				if(CASFileFilter.serializedSuffixFilter.equals(this.fileFilter)){
					fs = new FileInputStream(file);
					is = new ObjectInputStream(fs);

					//String loadedName = is.readUTF();
					int loadedNumberOfStates = is.readInt();
					if(this.controller.getAutomatonModel().getStates().getNumberOfStates()>=loadedNumberOfStates){
						this.controller.getAutomatonModel().setPopulation((Cell[][])is.readObject());
					} else {
						JOptionPane.showMessageDialog(cfc, "Failed to set population due to smaller state count","Title", JOptionPane.ERROR_MESSAGE);
					}
				} else if(CASFileFilter.xmlSuffixFilter.equals(this.fileFilter)) {
					deserializePopulation_XML_JAXB(cfc, file);
					//deserializePopulation_XML_JAXP(cfc, file);
				} else{
					JOptionPane.showMessageDialog(cfc, "This file extension is not supported yet!");
				}

			} catch (IOException | ClassNotFoundException exc) {
				exc.printStackTrace();
			} finally {
				try {
					if (fs != null)
						fs.close();
					if (is != null)
						is.close();
				} catch (IOException exc) {
					//ignore
				}
			}

			break;
		case JFileChooser.CANCEL_OPTION:
			//System.out.println("Cancel or the close-dialog icon was clicked");
			break;
		case JFileChooser.ERROR_OPTION:
			System.out.println("Error");
			break;
		}
	}

	//Deserialize with XML Method 1 --> JAXB
	private void deserializePopulation_XML_JAXB(CASFileChooser cfc, File file) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(AutomatonData.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			AutomatonData automatonData = (AutomatonData) jaxbUnmarshaller.unmarshal(file);
			if(this.controller.getAutomatonModel().getStates().getNumberOfStates()>=automatonData.getNumberOfStates()){
				this.controller.getAutomatonModel().setPopulation(automatonData.getPopulation());
			} else {
				JOptionPane.showMessageDialog(cfc, "Failed to set population due to smaller state count" ,"Title", JOptionPane.ERROR_MESSAGE);
			}
		} catch (JAXBException jaxbexc) {
			jaxbexc.printStackTrace();
			JOptionPane.showMessageDialog(cfc, jaxbexc.getMessage(), "Title", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//Deserialize with XML Method 2 --> JAXP || DOM
	private void deserializePopulation_XML_JAXP(CASFileChooser cfc, File file) {
		int elementsNotPopulationCount = 2;
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			dbf.setValidating(false);
			dbf.setIgnoringComments(true);

			DocumentBuilder builder = dbf.newDocumentBuilder();

			Document document = builder.parse(file);
			AutomatonData automatonData = new AutomatonData();
			Element documentRootElement = document.getDocumentElement();
			NodeList children = documentRootElement.getChildNodes();
			
			automatonData.setAutomatonName(documentRootElement.getAttribute(SerializePopulationListener.automatonNameStr));
			int maxNumberOfColumns = 0;
			int row_index = 0;
			Cell[][] cells  = new Cell[(children.getLength()-1-elementsNotPopulationCount)/2][];
			
			for (int i = 0; i < children.getLength(); ++i) {
				Node currentChild = children.item(i);
				switch (currentChild.getNodeType()) {
				case Node.TEXT_NODE:
					break;
				case Node.ELEMENT_NODE:
					if(currentChild.getNodeName().equals(SerializePopulationListener.numberOfStatesStr)) {
						automatonData.setNumberOfStates(Integer.parseInt(currentChild.getTextContent()));
					}
					if (currentChild.getNodeName().equals(SerializePopulationListener.populationStr)) {
						NodeList childrenPopulation = currentChild.getChildNodes();
						int numberOfColumnsInCurRow = childrenPopulation.getLength();
						cells[row_index] = new Cell[(numberOfColumnsInCurRow-1)/2];
						int column_index = 0;
						for(int p = 0; p < numberOfColumnsInCurRow; p++) {
							Node node = childrenPopulation.item(p);
							if (node.getNodeName().equals(SerializePopulationListener.itemStr)) {
								NodeList states = node.getChildNodes();
								for (int s = 0; s < states.getLength(); ++s) {
									Node stateChildNode = states.item(s);
									if(stateChildNode.getNodeType() == Node.ELEMENT_NODE){
										cells[row_index][column_index] = new Cell(Integer.parseInt(stateChildNode.getTextContent()));
										column_index++;
									}
								}
							}
						}
						row_index++;
						if(numberOfColumnsInCurRow>maxNumberOfColumns){
							maxNumberOfColumns = numberOfColumnsInCurRow;
						}
					}
					break;
				default:
					break;
				}
			}
			
			if(this.controller.getAutomatonModel().getStates().getNumberOfStates()>=automatonData.getNumberOfStates()){
				this.controller.getAutomatonModel().setPopulation(cells);
			} else {
				JOptionPane.showMessageDialog(cfc, "Failed to set population due to smaller state count","Title", JOptionPane.ERROR_MESSAGE);
			}
			
		}catch (SAXException | ParserConfigurationException | IOException e ) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(cfc, e.getMessage(), "Title", JOptionPane.ERROR_MESSAGE);
		}
	}
}
