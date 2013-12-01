package ggll.core.semantics;

import ggll.core.lexical.Yytoken;
import ggll.core.syntax.model.ParseStack;

public abstract class SemanticRoutineClass
{
	private Yytoken currentToken;

	private ParseStack parseStack;

	public Yytoken getCurrentToken()
	{
		return currentToken;
	}

	public void setCurrentToken(Yytoken currentToken)
	{
		this.currentToken = currentToken;
	}

	public ParseStack getParseStack()
	{
		return parseStack;
	}

	public void setParseStack(ParseStack parseStack)
	{
		this.parseStack = parseStack;
	}
}
