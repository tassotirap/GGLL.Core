package ggll.core.compile;

import ggll.core.list.ExtendedList;
import ggll.core.properties.GGLLProperties;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class Compiler
{
	protected class OutputCompiler extends Writer
	{
		@Override
		public void close() throws IOException
		{
		}

		@Override
		public void flush() throws IOException
		{
		}

		@Override
		public void write(char[] cbuf, int off, int len) throws IOException
		{
			Compiler.this.output += String.copyValueOf(cbuf, off, len);
		}
	}

	private String output = "";
	private String javaSDK = "";

	public Compiler()
	{
		final GGLLProperties properties = new GGLLProperties();
		this.javaSDK = properties.getJavaSDKPath();
	}

	private String validateJavaFile(String fileName)
	{
		if (!fileName.endsWith(".java"))
		{
			return fileName + ".java";
		}
		return fileName;
	}

	public void compile(String fileName) throws Exception
	{
		final String[] fileNames = new String[]{ fileName };
		compile(fileNames);
	}

	public void compile(String[] fileNames) throws Exception
	{
		System.setProperty("java.home", this.javaSDK);

		final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		final StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

		final ExtendedList<File> sourceFileList = new ExtendedList<File>();

		for (String fileName : fileNames)
		{
			fileName = validateJavaFile(fileName);
			sourceFileList.append(new File(fileName));
		}

		final Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(sourceFileList.getAll());

		final CompilationTask task = compiler.getTask(new OutputCompiler(), fileManager, null, null, null, compilationUnits);

		final boolean result = task.call();
		fileManager.close();

		if (!result)
		{
			throw new Exception(this.output);
		}
	}
}
