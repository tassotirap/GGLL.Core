package ggll.core.semantics;

import ggll.core.lexical.Yytoken;
import ggll.core.syntax.model.ParseStack;

public abstract class SemanticRoutineClass
{
	private Yytoken currentToken;

	private ParseStack parseStack;

	public Yytoken getCurrentToken()
	{
		return this.currentToken;
	}

	public ParseStack getParseStack()
	{
		return this.parseStack;
	}

	public void setCurrentToken(Yytoken currentToken)
	{
		this.currentToken = currentToken;
	}

	public void setParseStack(ParseStack parseStack)
	{
		this.parseStack = parseStack;
	}
}
