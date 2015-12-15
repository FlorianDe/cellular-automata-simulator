package de.cas.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import de.cas.model.Automaton;

public class Lang {
	public static boolean printAutomatonCount;

	public static boolean isPrintAutomatonCount() {
		return printAutomatonCount;
	}

	public static void setPrintAutomatonCount(boolean printAutomatonCount) {
		Lang.printAutomatonCount = printAutomatonCount;
	}
	
	public static void println(Automaton automton, String text, Object... args){
		String message = printAutomatonCount?"["+Automaton.getRunningAutomatons().get(automton)+"] ":"";
		message += text+"\n";
		System.out.printf(message, args);
	}
	
	public static void println(String text, Object... args){
		System.out.printf(text+"\n", args);
	}
	
	
	public static String readFileToString(String path){
		return readFileToString(new File(path));
	}
	public static String readFileToString(File path){
		return readFileToString(path.toPath());
	}
	public static String readFileToString(Path path){
		try {
			return new String(Files.readAllBytes(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	
	public static void saveFile(String path, String content){
		saveFile(new File(path), content);
	}
	public static void saveFile(File path, String content){
		saveFile(path.toPath(), content);
	}
	public static void saveFile(Path path, String content){
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(path.toAbsolutePath().toString()));
			bw.write(content);
			bw.flush();
			
			if(bw!=null)
				bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String readResourceFileToString(String path){
		String fileLines = "";
	    InputStream is = Lang.class.getClassLoader().getResourceAsStream(path);
	    if(is!=null){
	    	fileLines = Lang.convertStreamToString(is);
		}
	    return fileLines;
	}

	
	public static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}

}
