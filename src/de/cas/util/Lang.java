package de.cas.util;

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
	

}
