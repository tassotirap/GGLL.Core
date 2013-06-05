package ggll.core.compile;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassLoader<T>
{
	private T instance;
	

	public ClassLoader(File file)
	{
		try
		{
			URL url = file.getParentFile().toURI().toURL();
			URL[] urls = new URL[]{ url };
			URLClassLoader loader = new URLClassLoader(urls);

			Class clazz = loader.loadClass(getClassName(file.getName()));
			instance = (T) clazz.newInstance();
			loader.close();
		}
		catch (ClassNotFoundException ex)
		{
			System.out.print("Class " + getClassName(file.getName()) + " not found.");
		}
		catch (Exception ex)
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

	public T getInstance()
	{
		return instance;
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
				Class clazz = instance.getClass();
				Method mtd = clazz.getMethod(function);
				mtd.invoke(instance);
			}
		}
		catch (NoSuchMethodException ex)
		{
			System.out.println("Method " + function + " not found.");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
