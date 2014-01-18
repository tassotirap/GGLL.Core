package ggll.core.lexical;

import ggll.core.compile.ClassLoader;
import ggll.core.compile.Compiler;

import java.io.File;

public class YyFactory
{
	public void createYylex(String baseDir, String path, String scanner)
	{
		try
		{
			final File targetDir = new File(baseDir + "/" + path);
			if (!targetDir.exists())
			{
				if (!targetDir.mkdir())
				{
					return;
				}
			}
			final String[] arguments = new String[]{ "-d", baseDir + "/" + path, scanner };
			JFlex.Main.main(arguments);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	public Yylex getYylex(File path) throws Exception
	{
		try
		{
			final Compiler compile = new Compiler();
			compile.compile(path.getPath());

			final ClassLoader<Yylex> classLoader = new ClassLoader<Yylex>(path);
			return classLoader.getInstance();

		}
		catch (final Exception e)
		{
			throw e;
		}
	}

	public Yylex getYylex(String file) throws Exception
	{
		return getYylex(new File(file));
	}
}
