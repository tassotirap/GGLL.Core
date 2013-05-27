/*
 * Created on 12/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.ggll.core.semantics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Set;

import org.ggll.core.CoreManager;
import org.ggll.core.lexical.Yytoken;
import org.ggll.core.syntax.model.ParseStack;

public class SemanticRoutinesHelper
{
	public static final String BEGIN_ROUTINE = "/* BEGIN ROUTINE: ";

	public static final String BEGIN_SEMANTIC_ROUTINES = "/* BEGIN SEMANTIC ROUTINES */";

	public static final String END_ROUTINE = "/* END ROUTINE: ";

	public static final String END_SEMANTIC_ROUTINES = "/* END SEMANTIC ROUTINES */";

	private SemanticRoutine semanticRoutine;
	private Class cls;

	private static SemanticRoutinesHelper instance;

	private ClassLoader loader;

	public static SemanticRoutinesHelper getInstance()
	{
		return instance;
	}

	public static Set<String> getRegRoutines()
	{
		return getRoutineCode().keySet();
	}

	public static SemanticRoutinesHelper setInstance(ParseStack parseStack, File sourceFile)
	{
		try
		{
			instance = new SemanticRoutinesHelper(parseStack, sourceFile.getParent() + "\\", sourceFile.getName().replace(".java", ""));
			return instance;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	private static String getRoutineName(String line)
	{
		return line.substring(line.indexOf(BEGIN_ROUTINE) + BEGIN_ROUTINE.length()).replace("*/", "").trim();
	}

	public static String getCode(String routineName)
	{
		if (routineName != null)
		{
			HashMap<String, String> routineCode = getRoutineCode();
			if (routineCode.containsKey(routineName))
			{
				return routineCode.get(routineName);
			}
		}
		return null;
	}

	public static HashMap<String, String> getRoutineCode()
	{
		HashMap<String, String> routineCode = new HashMap<String, String>();
		try
		{
			FileInputStream fileInputStream = new FileInputStream(CoreManager.getSemanticFile());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
			String line = bufferedReader.readLine();
			while (line != null)
			{
				if (line.equals(BEGIN_SEMANTIC_ROUTINES))
				{
					line = bufferedReader.readLine();
					while (line != null)
					{
						if (line.contains(BEGIN_ROUTINE))
						{
							String name = getRoutineName(line);
							String code = "";
							line = bufferedReader.readLine();
							while (line != null)
							{
								if (line.contains((END_ROUTINE + name)))
								{
									break;
								}
								else
								{
									code += line + "\n";
									line = bufferedReader.readLine();
								}
							}
							routineCode.put(name, code);
						}
						if (line.equals(END_SEMANTIC_ROUTINES))
						{
							break;
						}
						line = bufferedReader.readLine();
					}
				}

				line = bufferedReader.readLine();
			}
			bufferedReader.close();

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return routineCode;
	}

	public SemanticRoutinesHelper(ParseStack parseStack, String sourceFile, String className)
	{
		try
		{
			Compile compine = new Compile(sourceFile, className);
			compine.run();

			File file = new File(sourceFile);
			URL url = file.toURI().toURL();
			URL[] urls = new URL[]{ url };

			loader = new URLClassLoader(urls);
			cls = loader.loadClass(className);
			semanticRoutine = (SemanticRoutine) cls.newInstance();
			semanticRoutine.parseStack = parseStack;

		}
		catch (Exception e)
		{
			e.printStackTrace();
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
