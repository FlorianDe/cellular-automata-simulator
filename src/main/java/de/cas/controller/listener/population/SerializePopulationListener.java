package de.cas.controller.listener.population;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import de.cas.controller.IAutomatonController;
import de.cas.model.Automaton;
import de.cas.model.AutomatonData;
import de.cas.model.Cell;
import de.cas.util.loader.CstmClassloader;
import de.cas.view.casUI.dialog.CASFileChooser;
import de.cas.view.casUI.util.CASFileFilter;

public class SerializePopulationListener implements ActionListener {
	public final static String numberOfStatesStr = "numberOfStates";
	public final static String populationStr = "population";
	public final static String itemStr = "item";
	public final static String stateStr = "state";
	public final static String automatonNameStr = "automatonName";
	
	protected IAutomatonController controller;
	protected FileNameExtensionFilter fileFilter;

	public SerializePopulationListener(IAutomatonController controller, FileNameExtensionFilter fileFilter) {
		this.controller = controller;
		this.fileFilter = fileFilter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CASFileChooser cfc = new CASFileChooser(this.controller, CstmClassloader.getPopulationFolder(), false, null, fileFilter);

		int result = cfc.showSaveDialog(this.controller.getView());
		switch (result) {
		case JFileChooser.APPROVE_OPTION:
			ObjectOutputStream os = null;
			FileOutputStream fs = null;
			try {
				File file = cfc.getSelectedFile();
				String fDir = file.getParent();
				String fName = file.getName();
				if (!fName.endsWith(this.fileFilter.getExtensions()[0])) {
					fName += ("."+this.fileFilter.getExtensions()[0]);
					file = new File(fDir + File.separator + fName);
				}
				if(CASFileFilter.serializedSuffixFilter.equals(this.fileFilter)){
					fs = new FileOutputStream(file);
					os = new ObjectOutputStream(fs);
					os.writeUTF(this.controller.getAutomatonModel().getClass().getClass().getSimpleName());
					os.writeInt(this.controller.getAutomatonModel().getStates().getNumberOfStates());
					os.writeObject(this.controller.getAutomatonModel().getPopulation());
					os.flush();
				} else if(CASFileFilter.xmlSuffixFilter.equals(this.fileFilter)) {
					serializePopulation_XML_JAXB(cfc, file, e);
					//serializePopulation_XML_JAXP(cfc, file, e);
				} else {
					JOptionPane.showMessageDialog(cfc, "This file extension is not supported yet!", "Title", JOptionPane.ERROR_MESSAGE);
				}
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(cfc, "Failed to save serialized!", "Title", JOptionPane.ERROR_MESSAGE);
				exc.printStackTrace();
			} finally {
				try {
					if (fs != null)
						fs.close();
				} catch (IOException exc) {
					exc.printStackTrace();
				}
				try {
					if (os != null)
						os.close();
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

	//Serialize with XML Method 1 --> JAXB
	public void serializePopulation_XML_JAXB(CASFileChooser cfc, File file,  ActionEvent e){
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(AutomatonData.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(new AutomatonData(this.controller.getAutomatonModel()), file);
		} catch (JAXBException jaxbexc) {
			JOptionPane.showMessageDialog(cfc, jaxbexc.getMessage(), "Title", JOptionPane.ERROR_MESSAGE);
		}
	}

	//Serialize with XML Method 2 --> JAXP || StAX
	public void serializePopulation_XML_JAXP(CASFileChooser cfc, File file,  ActionEvent e){
		XMLStreamWriter writer = null;
		try {
			Automaton automaton = this.controller.getAutomatonModel();
			String utf8 = StandardCharsets.UTF_8.name();
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			writer = factory.createXMLStreamWriter(new FileOutputStream(file), utf8);
			
			// Create XML-Header
			//<?xml version="1.0" encoding="UTF-8"?>
			writer.writeStartDocument(utf8, "1.0"); 
			writer.writeCharacters("\n");

			//<automatonData automatonName="XXX">
			writer.writeStartElement(AutomatonData.class.getSimpleName());
			writer.writeAttribute(SerializePopulationListener.automatonNameStr, "" + automaton.getClass().getSimpleName());
			writer.writeCharacters("\n\t");

				//<numberOfStates>XXX</numberOfStates>
				writer.writeStartElement(SerializePopulationListener.numberOfStatesStr);
				writer.writeCharacters(automaton.getStates().getNumberOfStates()+"");
				writer.writeEndElement();
				writer.writeCharacters("\n\t");
	
				Cell[][] population = automaton.getPopulation();
				for (int y = 0; y < automaton.getNumberOfColumns(); y++) {
					//<population>
					writer.writeStartElement(SerializePopulationListener.populationStr);
					writer.writeCharacters("\n\t\t");
					for (int x = 0; x < automaton.getNumberOfRows(); x++) {
						//<item>
						writer.writeStartElement(SerializePopulationListener.itemStr);
						writer.writeCharacters("\n\t\t\t");
							//<state>XXX</state>
							writer.writeStartElement(SerializePopulationListener.stateStr);
							writer.writeCharacters(population[y][x].getState()+"");
							writer.writeEndElement();
							writer.writeCharacters("\n\t\t");
						//</item>
						writer.writeEndElement();
						writer.writeCharacters("\n\t\t");
					}
					//</population>
					writer.writeEndElement();
					writer.writeCharacters("\n");
				}

			//</automatonData>	
			writer.writeEndElement();
			writer.writeCharacters("\n");
			
			writer.writeEndDocument();

		} catch (Throwable exc) {
			exc.printStackTrace();
			JOptionPane.showMessageDialog(cfc, exc.getMessage(), "Title", JOptionPane.ERROR_MESSAGE);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (XMLStreamException ef) {
					ef.printStackTrace();
				}
			}
		}
	}
}
