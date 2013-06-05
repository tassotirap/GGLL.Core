package ggll.core.compile;

import ggll.core.properties.GGLLProperties;

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
			output += String.copyValueOf(cbuf, off, len);
		}
	}	
	
	private String output = "";
	private String javaSDK = "";
	
	public Compiler()
	{
		GGLLProperties properties = new GGLLProperties();
		javaSDK = properties.getJavaSDKPath();
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
		String[] fileNames = new String[]{ fileName };
		compile(fileNames);
	}

	public void compile(String[] fileNames) throws Exception
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
		
		if(!result)
		{
			throw new Exception(output);
		}
	}
}
