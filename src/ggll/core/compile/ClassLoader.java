package ggll.core.compile;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 
 * @author Tasso Tirapani Silva Pinto Class to load other class
 * @param <T>
 */
public class ClassLoader<T>
{
	private T instance;

	public ClassLoader(File file)
	{
		try
		{
			final URL url = file.getParentFile().toURI().toURL();
			final URL[] urls = new URL[]{ url };
			final URLClassLoader loader = new URLClassLoader(urls);

			final Class clazz = loader.loadClass(getClassName(file.getName()));
			this.instance = (T) clazz.newInstance();
			loader.close();
		}
		catch (final ClassNotFoundException ex)
		{
			System.out.print("Class " + getClassName(file.getName()) + " not found.");
		}
		catch (final Exception ex)
		{
			ex.printStackTrace();
		}

	}

	public ClassLoader(String file) throws Exception
	{
		this(new File(file));
	}

	public ClassLoader(T instance)
	{
		this.instance = instance;
	}

	private String getClassName(String fileName)
	{
		if (fileName.endsWith(".java"))
		{
			return fileName.replace(".java", "");
		}
		return fileName;
	}

	public void execFunction(String function)
	{
		try
		{
			if (function != null)
			{
				final Class clazz = this.instance.getClass();
				final Method mtd = clazz.getMethod(function);
				mtd.invoke(this.instance);
			}
		}
		catch (final NoSuchMethodException ex)
		{
			System.out.println("Method " + function + " not found.");
		}
		catch (final Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public T getInstance()
	{
		return this.instance;
	}
}
