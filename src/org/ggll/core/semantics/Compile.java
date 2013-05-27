package org.ggll.core.semantics;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

public class Compile
{
	public class OutputCompiler extends Writer
	{
		@Override
		public void write(char[] cbuf, int off, int len) throws IOException
		{
		}

		@Override
		public void flush() throws IOException
		{
		}

		@Override
		public void close() throws IOException
		{
		}
	}

	private String javaSDK = "C:\\Program Files\\Java\\jdk1.7.0_05";
	private String sourceFile;
	private String className;

	public Compile(String javaSDK, String sourceFile, String className)
	{
		this.javaSDK = javaSDK;
		this.sourceFile = sourceFile;
		this.className = className;
	}

	public Compile(String sourceFile, String className)
	{
		this.sourceFile = sourceFile;
		this.className = className;
	}

	public void run() throws IOException
	{
		System.setProperty("java.home", javaSDK);
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		List<File> sourceFileList = new ArrayList<File>();
		sourceFileList.add(new File(sourceFile + className + ".java"));
		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(sourceFileList);

		CompilationTask task = compiler.getTask(new OutputCompiler(), fileManager, null, null, null, compilationUnits);
		boolean result = task.call();
		if (result)
		{
			System.out.println("Compilation was successful");
		}
		else
		{
			System.out.println("Compilation failed");
		}
		fileManager.close();
	}
}
