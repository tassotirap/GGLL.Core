package ggll.core.semantics;

import ggll.core.compile.ClassLoader;
import ggll.core.lexical.Yytoken;
import ggll.core.syntax.model.ParseStack;
import ggll.core.syntax.parser.Parser;


public class SemanticRoutine
{
	private Parser parser;
	private ClassLoader<SemanticRoutineClass> classLoader;

	public SemanticRoutine(Parser parser, SemanticRoutineClass semanticRoutineClass) throws Exception
	{
		try
		{
			this.parser = parser;
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
				classLoader.getInstance().errorList = parser.getErrorList();
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
		classLoader.getInstance().currentToken = cToken;
	}
	
	public void setParseStack(ParseStack parseStack)
	{
		classLoader.getInstance().parseStack = parseStack;
	}
}
