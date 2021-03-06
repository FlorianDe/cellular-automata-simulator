package de.cas.util.loader;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;

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