package ggll.core.semantics;

import ggll.core.lexical.Yytoken;
import ggll.core.syntax.model.ParseNode;
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
	
	public int I(int number)
	{
		int top = this.parseStack.topIndex();
		return Integer.parseInt(this.parseStack.elementAt(top - number).getSemanticSymbol().toString());		
	}
	
	public float F(int number)
	{
		int top = this.parseStack.topIndex();
		return Float.parseFloat(this.parseStack.elementAt(top - number).getSemanticSymbol().toString());		
	}
	
	public String S(int number)
	{
		int top = this.parseStack.topIndex();
		return this.parseStack.elementAt(top - number).getSemanticSymbol().toString();		
	}
	
	public ParseNode N(int number)
	{
		int top = this.parseStack.topIndex();
		return this.parseStack.elementAt(top - number);		
	}
}
