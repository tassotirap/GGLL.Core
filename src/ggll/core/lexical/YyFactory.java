package ggll.core.lexical;

import ggll.core.compile.ClassLoader;
import ggll.core.compile.Compiler;

import java.io.File;
import java.security.Permission;

public class YyFactory
{
	protected static class ExitException extends SecurityException
    {
        public final int status;
        public ExitException(int status)
        {
            super("Error.");
            this.status = status;
        }
    }

    private static class NoExitSecurityManager extends SecurityManager
    {
        @Override
        public void checkPermission(Permission perm)
        {
        }
        @Override
        public void checkPermission(Permission perm, Object context)
        {
        }
        @Override
        public void checkExit(int status)
        {
            super.checkExit(status);
            throw new ExitException(status);
        }
    }

	public void createYylex(String path, String scanner)
	{
		try
		{
			final File targetDir = new File(path);
			if (!targetDir.exists())
			{
				if (!targetDir.mkdir())
				{
					return;
				}
			}
			final String[] arguments = new String[]{ "-d", path, scanner };
			System.setSecurityManager(new NoExitSecurityManager());
			JFlex.Main.main(arguments);
			System.setSecurityManager(null);
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

	public Yylex getYylex(String path) throws Exception
	{
		return getYylex(new File(path));
	}
}
