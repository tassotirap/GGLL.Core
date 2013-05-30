/*
 * Created on 12/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.ggll.core.semantics;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.ggll.core.compile.Compiler;
import org.ggll.core.lexical.Yytoken;
import org.ggll.core.syntax.model.ParseStack;

public class SemanticRoutine
{
	private SemanticRoutineClass semanticRoutine;
	private Class cls;
	private ClassLoader loader;

	public SemanticRoutine(ParseStack parseStack, String sourceFile, String className) throws Exception
	{
		try
		{
			Compiler compine = new Compiler();
			compine.compile(sourceFile + className);

			File file = new File(sourceFile);
			URL url = file.toURI().toURL();
			URL[] urls = new URL[]{ url };

			loader = new URLClassLoader(urls);
			cls = loader.loadClass(className);
			semanticRoutine = (SemanticRoutineClass) cls.newInstance();
			semanticRoutine.parseStack = parseStack;

		}
		catch (Exception e)
		{
			throw e;
		}
	}

	public void execFunction(String function)
	{
		try
		{
			if (function != null && function.compareToIgnoreCase("-1") != 0)
			{
				Method mtd = cls.getMethod(function);
				mtd.invoke(semanticRoutine);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void setCurrentToken(Yytoken cToken)
	{
		semanticRoutine.currentToken = cToken;
	}
}
