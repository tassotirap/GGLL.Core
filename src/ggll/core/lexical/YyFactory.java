package ggll.core.lexical;

import ggll.core.compile.ClassLoader;
import ggll.core.compile.Compiler;

import java.io.File;


public class YyFactory
{
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

	public static Yylex getYylex(File path) throws Exception
	{
		try
		{
			Compiler compile = new Compiler();
			compile.compile(path.getPath());
			
			ClassLoader<Yylex> classLoader = new ClassLoader<Yylex>(path);
			return classLoader.getInstance();
			
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public static Yylex getYylex(String file) throws Exception
	{
		return getYylex(new File(file));
	}
}
