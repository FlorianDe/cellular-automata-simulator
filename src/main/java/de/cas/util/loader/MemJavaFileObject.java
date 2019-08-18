package de.cas.util.loader;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

public class MemJavaFileObject extends SimpleJavaFileObject {
	private final ByteArrayOutputStream baos = new ByteArrayOutputStream(8192);
	private final String className;

	MemJavaFileObject(String className) {
		super(URI.create("string:///" + className.replace('.', '/')
				+ Kind.CLASS.extension), Kind.CLASS);
		this.className = className;
	}

	@Override
	public OutputStream openOutputStream() {
		return this.baos;
	}

	String getClassName() {
		return this.className;
	}

	byte[] getClassBytes() {
		return this.baos.toByteArray();
	}
}
