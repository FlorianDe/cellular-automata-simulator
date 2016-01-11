package de.cas.util.loader;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import de.cas.controller.properties.CASSettings;
import de.cas.controller.properties.CASSettings.Property;
import de.cas.util.Lang;

/**
 * Created by Florian on 13.11.2015.
 */
public class AutomatonLoader {

    public static final AutomatonLoader instance;
    public static final String automatonTemplateLines;
    public static AutomatonLoader getInstance() {
		return instance;
	}

	static {
        instance = new AutomatonLoader();
        String templatePath = CASSettings.getInstance().getProperty(Property.AUTOMATON_TEMPLATE_PATH);
        automatonTemplateLines = Lang.readResourceFileToString(templatePath);
        if(automatonTemplateLines.isEmpty()){
        	System.out.println("Cannot load automaton template!");
        }
	}


	
	
    /**
     * Compute the absolute file path to the jar file.
     * The framework is based on http://stackoverflow.com/a/12733172/1614775
     * But that gets it right for only one of the four cases.
     * 
     * @param aclass A class residing in the required jar.
     * 
     * @return A File object for the directory in which the jar file resides.
     * During testing with NetBeans, the result is ./build/classes/,
     * which is the directory containing what will be in the jar.
     * 
     * @author: http://stackoverflow.com/questions/15359702/get-location-of-jar-file
     */
    public File getJarDir(Class<?> aclass) {
        URL url;
        String extURL;      //  url.toExternalForm();

        // get an url
        try {
            url = aclass.getProtectionDomain().getCodeSource().getLocation();
              // url is in one of two forms
              //        ./build/classes/   NetBeans test
              //        jardir/JarName.jar  froma jar
        } catch (SecurityException ex) {
            url = aclass.getResource(aclass.getSimpleName() + ".class");
            // url is in one of two forms, both ending "/com/physpics/tools/ui/PropNode.class"
            //          file:/U:/Fred/java/Tools/UI/build/classes
            //          jar:file:/U:/Fred/java/Tools/UI/dist/UI.jar!
        }

        // convert to external form
        extURL = url.toExternalForm();

        // prune for various cases
        if (extURL.endsWith(".jar"))   // from getCodeSource
            extURL = extURL.substring(0, extURL.lastIndexOf("/"));
        else {  // from getResource
            String suffix = "/"+(aclass.getName()).replace(".", "/")+".class";
            extURL = extURL.replace(suffix, "");
            if (extURL.startsWith("jar:") && extURL.endsWith(".jar!"))
                extURL = extURL.substring(4, extURL.lastIndexOf("/"));
        }

        // convert back to url
        try {
            url = new URL(extURL);
        } catch (MalformedURLException mux) {
            // leave url unchanged; probably does not happen
        }

        // convert url to File
        try {
            return new File(url.toURI());
        } catch(URISyntaxException ex) {
            return new File(url.getPath());
        }
    }

    //TODO CHECK WHETHER JAR OR NOT!
    public URL loadFile(String filePath){
        return instance.getClass().getClassLoader().getResource(filePath);
    }
    
	public static boolean classFileSuffixFilter(Path path) {
		return helperFileSuffixFiler(path, ".class");
	}
	public static boolean javaFileSuffixFilter(Path path) {
		return helperFileSuffixFiler(path, ".java");
	}
	
	private static boolean helperFileSuffixFiler(Path path, String suffix){
		return path.toFile().getName().endsWith(suffix);
	}
}
