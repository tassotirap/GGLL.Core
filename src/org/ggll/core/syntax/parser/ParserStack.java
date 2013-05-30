package org.ggll.core.syntax.parser;

import org.ggll.core.syntax.model.GrViewStack;
import org.ggll.core.syntax.model.NTerminalStack;
import org.ggll.core.syntax.model.ParseStack;

public class ParserStack
{
	private ParseStack parseStack;
	private GrViewStack grViewStack;
	private NTerminalStack nTerminalStack;
	private int top;
	

	public ParserStack()
	{
		
	}
	
	public GrViewStack getGrViewStack()
	{
		return grViewStack;
	}

	public NTerminalStack getNTerminalStack()
	{
		return nTerminalStack;
	}

	public ParseStack getParseStack()
	{
		return parseStack;
	}

	public int getTop()
	{
		return top;
	}

	public void init()
	{
		grViewStack = new GrViewStack();
		nTerminalStack = new NTerminalStack();
		parseStack = new ParseStack();
	}

	public void setGrViewStack(GrViewStack grViewStack)
	{
		this.grViewStack = grViewStack;
	}

	public void setNTerminalStack(NTerminalStack nTerminalStack)
	{
		this.nTerminalStack = nTerminalStack;		
	}

	public void setTop(int top)
	{
		this.top = top;
	}
}
