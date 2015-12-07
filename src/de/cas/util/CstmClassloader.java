package de.cas.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class CstmClassloader extends ClassLoader {
	public static final String AUTOMATON_PACKAGE = "de.cas.model.internalautomata.";
	public static CstmClassloader instance;
	static{
		instance = new CstmClassloader();
	}
	public static CstmClassloader getClassLoader(){
		return instance;
	}
	
	public CstmClassloader(){
		super(CstmClassloader.class.getClassLoader());
	}


	public synchronized Class<?> loadClass(File file){
		return loadClass(file, false);
	}
	public synchronized Class<?> loadClass(File file, boolean resolve){
		Class<?> loadedClass = null;
		if(file!=null){
			String className = file.getName().replace(".class", "");
			loadedClass = this.findLoadedClass(className);
			if(loadedClass==null){
				try {
					loadedClass = this.getParent().loadClass(className);
				} catch (ClassNotFoundException e) {
					//ignore
				}
				if(loadedClass==null){
					URLClassLoader urlcl = null;
					try {
						URL[] urls = new URL[]{file.toURI().toURL()};
						urlcl = new URLClassLoader(urls);
						loadedClass = urlcl.loadClass(AUTOMATON_PACKAGE+className);
						
					} catch (ClassNotFoundException e) {
						//System.err.println("ClassNotFoundException:"+file.getAbsolutePath());
					} catch (MalformedURLException e) {
						//ignore
					} finally{
						if(urlcl!=null){
							try {
								urlcl.close();
							} catch (IOException e) {
								//ignore
							}
						}
					}
				}
			}
		}
		if(resolve && loadedClass!=null){
			this.resolveClass(loadedClass);
		}
		return loadedClass;
	}
}
