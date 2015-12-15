package de.cas.util.loader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;

import de.cas.Main;
import de.cas.controller.properties.CASSettings;
import de.cas.controller.properties.CASSettings.Property;
import de.cas.model.Automaton;

import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class CstmClassloader extends ClassLoader {
	public static final String AUTOMATON_PACKAGE = "de.cas.model.internalautomata.";
	private String lastCompilationErrors = "";
	private final Map<String, MemJavaFileObject> classFiles = new HashMap<String, MemJavaFileObject>();

	public CstmClassloader(){
		super(CstmClassloader.class.getClassLoader());
	}
	
	public String getLastCompilationErrors() {
		return lastCompilationErrors;
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
	
	@Override
	public Class<?> findClass(String name) throws ClassNotFoundException {
		MemJavaFileObject fileObject = this.classFiles.get(name);
		if (fileObject != null) {
			byte[] bytes = fileObject.getClassBytes();
			return this.defineClass(name, bytes, 0, bytes.length);
		}
		return super.findClass(name);
	}
	
	public void addClassFile(MemJavaFileObject memJavaFileObject){
		this.classFiles.put(memJavaFileObject.getClassName(), memJavaFileObject);
	}

	public Map<String, MemJavaFileObject> getClassFiles() {
		return classFiles;
	}
	
	public Automaton getAutomatonInstance(Path path){
//		System.out.println("path1:"+path);
		String src;
		String automatonPackage = CASSettings.getInstance().getProperty(Property.AUTOMATON_PACKAGE);
		String automatonEnding = CASSettings.getInstance().getProperty(Property.AUTOMATON_FILES_ENDING);
		if(!path.toAbsolutePath().toString().endsWith(automatonEnding)){
			path = path.resolveSibling(path.getFileName()+automatonEnding);
		}
//		System.out.println("path2:"+path);
		try {
//			System.out.println("Read bytes from path:"+path.toString());
			src = new String(Files.readAllBytes(path));
			String fileName = path.getFileName().toString().replace(".class", "").replace(".java", "").replace(automatonEnding, "");

			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			if(compiler!=null){

				CstmClassloader classLoader = new CstmClassloader();

				// Ein DiagnosticListener wird immer genau dann benachrichtigt, wenn der
				// Compiler auf ein Problem stoesst.
				// Ein DiagnosticCollector ist ein vordefinierter DiagnosticListener,
				// der alle Meldungen sammelt und spaeter zugaenglich macht.
				DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

				StandardJavaFileManager sjfm = compiler.getStandardFileManager(null, null, null);


				JavaFileManager fileManager = new MemJavaFileManager(sjfm, classLoader);
				JavaFileObject javaFile = new StringJavaFileObject(fileName, src);
				Iterable<? extends JavaFileObject> units = Arrays.asList(javaFile);

				CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, units);
				boolean success = task.call();
				fileManager.close();
				if(success){
					lastCompilationErrors = "";
					if(!automatonPackage.endsWith(".")){
						automatonPackage+=".";
					}
					Class<?> cls = Class.forName(automatonPackage+fileName, true, classLoader);
					if(cls!=null){
						Object obj = cls.newInstance();
						if (obj instanceof Automaton) {
							Automaton automaton = (Automaton)obj;
							//setPackageField(automaton);
							return automaton;
							//new Main().startCAS((Automaton)obj);
						}
					}
				}else{
					StringBuilder sb = new StringBuilder();
					for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
						sb.append(String.format("Kind: %s%n", diagnostic.getKind()));
						sb.append(String.format("Quelle: %s%n", diagnostic.getSource()));
						sb.append(String.format("Code und Nachricht: %s: %s%n", diagnostic.getCode(), diagnostic.getMessage(null)));
						sb.append(String.format("Zeile: %s%n", diagnostic.getLineNumber()));
						sb.append(String.format("Position/Spalte: %s/%s%n",diagnostic.getPosition(), diagnostic.getColumnNumber()));
						sb.append(String.format("Startpostion/Endposition: %s/%s%n",diagnostic.getStartPosition(), diagnostic.getEndPosition()));
						sb.append("\n");
					}
					lastCompilationErrors = sb.toString();
				}

			}else{
				System.out.println("Cannot load compiler!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// ignore
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return null;
	}
	
	public static File getDefaultAutomaton(){
		return new File(getAutomataFolder(),CASSettings.getInstance().getProperty(Property.AUTOMATON_DEFAULT));
	}
	
	public static File getAutomataFolder(){
        return new File(AutomatonLoader.getInstance().getJarDir(CstmClassloader.class),CASSettings.getInstance().getProperty(Property.AUTOMATON_PACKAGE));
    }
	
	
	
	private static void setPackageField(Automaton automaton){
		
		for(Field field : (new Main()).getClass().getClass().getDeclaredFields())
		{
			//automaton.getClass().getPackage();
			field.setAccessible(true);
			if(field.getName().equals("package")){

			}
			try {
				System.out.println("Field Name-->"+field.getName()+"\t" 
						+"Field Type-->"+ field.getType().getName()+"\t");
						//+"Field Value-->"+ field.get(automaton));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
