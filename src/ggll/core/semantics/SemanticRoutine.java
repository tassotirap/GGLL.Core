package ggll.core.semantics;

import ggll.core.compile.ClassLoader;
import ggll.core.lexical.Yytoken;
import ggll.core.syntax.model.ParseStack;

public class SemanticRoutine
{
	private ClassLoader<SemanticRoutineClass> classLoader;

	public SemanticRoutine(SemanticRoutineClass semanticRoutineClass) throws Exception
	{
		try
		{
			this.classLoader = new ClassLoader<SemanticRoutineClass>(semanticRoutineClass);
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
				classLoader.execFunction(function);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void setCurrentToken(Yytoken cToken)
	{
		classLoader.getInstance().setCurrentToken(cToken);
	}

	public void setParseStack(ParseStack parseStack)
	{
		classLoader.getInstance().setParseStack(parseStack);
	}
}
