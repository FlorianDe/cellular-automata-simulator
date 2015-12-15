package de.cas.util.loader;
import javax.tools.*;
import javax.tools.JavaFileObject.Kind;

public class MemJavaFileManager extends
ForwardingJavaFileManager<StandardJavaFileManager>
{
	private final CstmClassloader classLoader;

	public MemJavaFileManager(StandardJavaFileManager man, CstmClassloader classLoader) {
		super(man);
		this.classLoader = classLoader;
	}

	@Override
	public JavaFileObject getJavaFileForOutput( Location location,
			String className,
			Kind kind,
			FileObject sibling )
	{
		MemJavaFileObject fileObject = new MemJavaFileObject( className );
		classLoader.addClassFile( fileObject );
		return fileObject;
	}
}