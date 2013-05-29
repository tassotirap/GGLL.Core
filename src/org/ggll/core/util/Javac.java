package org.ggll.core.util;

import java.io.File;
import java.io.IOException;

import org.ggll.core.compile.Compiler;

/**
 * A wrapper to ease the use of com.sun.tools.javac.Main.
 * 
 * @author liyang
 */
public final class Javac
{
	String bootclasspath;

	String classpath;

	String encoding;

	String extdirs;

	String outputdir;

	String sourcepath;

	String target;

	public Javac(String classpath, String outputdir)
	{
		this.classpath = classpath;
		this.outputdir = outputdir;
	}

	public void compile(File srcFiles[])
	{
		String paths[] = new String[srcFiles.length];
		for (int i = 0; i < paths.length; i++)
		{
			paths[i] = srcFiles[i].getAbsolutePath();
		}
		compile(paths);
	}

	/**
	 * Compile the given source files.
	 * 
	 * @param srcFiles
	 * @return null if success; or compilation errors
	 */
	public void compile(String srcFiles[])
	{
		try
		{
			Compiler compiler = new Compiler();
			compiler.compile(srcFiles);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public String getBootclasspath()
	{
		return bootclasspath;
	}

	public String getClasspath()
	{
		return classpath;
	}

	public String getEncoding()
	{
		return encoding;
	}

	public String getExtdirs()
	{
		return extdirs;
	}

	public String getOutputdir()
	{
		return outputdir;
	}

	public String getSourcepath()
	{
		return sourcepath;
	}

	public String getTarget()
	{
		return target;
	}

	public void setBootclasspath(String bootclasspath)
	{
		this.bootclasspath = bootclasspath;
	}

	public void setClasspath(String classpath)
	{
		this.classpath = classpath;
	}

	public void setEncoding(String encoding)
	{
		this.encoding = encoding;
	}

	public void setExtdirs(String extdirs)
	{
		this.extdirs = extdirs;
	}

	public void setOutputdir(String outputdir)
	{
		this.outputdir = outputdir;
	}

	public void setSourcepath(String sourcepath)
	{
		this.sourcepath = sourcepath;
	}

	public void setTarget(String target)
	{
		this.target = target;
	}

}
