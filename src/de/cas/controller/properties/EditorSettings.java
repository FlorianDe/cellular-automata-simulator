package de.cas.controller.properties;

import java.io.InputStream;
import java.util.ArrayList;

public class EditorSettings {
	private static final String BASICCOMPLETIONS_FILE = "editor/basiccompletions.txt";
	private static final String SHORTHANDCOMPLETIONS_FILE = "editor/shorthandcompletions.txt";
	public final static String[] basiccompletions;
	public final static ArrayList<String[]> shorthandcompletions;
	
	private static final EditorSettings instance;

	public static EditorSettings getInstance() {
		return instance;
	}
	
	static {
        instance = new EditorSettings();
        
        basiccompletions = loadAndConvertFileToString(BASICCOMPLETIONS_FILE).replace("\n", "").replace("\r", "").split(",");
        
        String[] shc = loadAndConvertFileToString(SHORTHANDCOMPLETIONS_FILE).replace("\n", "").replace("\r", "").split("::");
        shorthandcompletions = new ArrayList<>();
        for (int i = 0; i < shc.length; i++) {
        	String[] value = shc[i].split("#");
        	if(value.length>2){
        		shorthandcompletions.add(new String[]{value[0],value[1],value[2]});
        	}
		}
	}
	

	public static String loadAndConvertFileToString(String relFilePath){
        InputStream is = instance.getClass().getClassLoader().getResourceAsStream(relFilePath);
        String lines;
        if(is!=null){
        	lines = convertStreamToString(is);
		}
        else{
        	lines = "";
        	System.out.println("Cannot load automaton template!");
        }
        
        return lines;
	}

	static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}

}
