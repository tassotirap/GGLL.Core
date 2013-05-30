package org.ggll.core.lexical;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import org.ggll.core.compile.Compiler;

public class YyFactory
{

	private static YyFactory instance;
	private static URLClassLoader loader;
	private static Class cls;

	private YyFactory()
	{

	}

	public static void createYylex(String baseDir, String path, String scanner)
	{
		try
		{
			File targetDir = new File(baseDir + "/" + path);
			if (!targetDir.exists())
			{
				if (!targetDir.mkdir())
				{
					return;
				}
			}
			String[] arguments = new String[]{ "-d", baseDir + "/" + path, scanner };
			JFlex.Main.main(arguments);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static YyFactory getInstance()
	{
		if (instance == null)
		{
			instance = new YyFactory();
		}
		return instance;
	}

	public static Yylex getYylex(File path) throws Exception
	{
		try
		{
			Compiler compile = new Compiler();
			compile.compile(path.getPath());
			
			URL url = path.getParentFile().toURI().toURL();
			URL[] urls = new URL[]{ url };

			loader = new URLClassLoader(urls);
			cls = loader.loadClass(path.getName().replace(".java", ""));
			Yylex yl = (Yylex) cls.newInstance();
			return yl;
			
		}
		catch (Exception e)
		{
			throw e;
		}
	}
}
