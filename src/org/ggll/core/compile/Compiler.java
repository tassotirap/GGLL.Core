package org.ggll.core.compile;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class Compiler
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

	public Compiler(String javaSDK)
	{
		this.javaSDK = javaSDK;
	}

	public Compiler()
	{
	}

	public boolean compile(String fileName) throws IOException
	{
		String[] fileNames = new String[]{ fileName };
		return compile(fileNames);
	}

	public boolean compile(String[] fileNames) throws IOException
	{
		System.setProperty("java.home", javaSDK);

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

		List<File> sourceFileList = new ArrayList<File>();

		for (String fileName : fileNames)
		{
			fileName = validateJavaFile(fileName);
			sourceFileList.add(new File(fileName));
		}

		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(sourceFileList);

		CompilationTask task = compiler.getTask(new OutputCompiler(), fileManager, null, null, null, compilationUnits);
		boolean result = task.call();
		fileManager.close();
		return result;
	}

	private String validateJavaFile(String fileName)
	{
		if (!fileName.endsWith(".java"))
		{
			return fileName + ".java";
		}
		return fileName;
	}
}
