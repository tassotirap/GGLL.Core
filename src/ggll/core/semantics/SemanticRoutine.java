package ggll.core.semantics;

import ggll.core.compile.ClassLoader;
import ggll.core.lexical.Yytoken;
import ggll.core.syntax.model.ParseStack;

public class SemanticRoutine
{
	private ClassLoader<SemanticRoutineClass> classLoader;

	public SemanticRoutine(SemanticRoutineClass semanticRoutineClass)
	{
		try
		{
			this.classLoader = new ClassLoader<SemanticRoutineClass>(semanticRoutineClass);
		}
		catch (final Exception e)
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
				this.classLoader.execFunction(function);
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	public void setCurrentToken(Yytoken cToken)
	{
		this.classLoader.getInstance().setCurrentToken(cToken);
	}

	public void setParseStack(ParseStack parseStack)
	{
		this.classLoader.getInstance().setParseStack(parseStack);
	}
}
