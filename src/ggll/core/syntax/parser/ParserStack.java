package ggll.core.syntax.parser;

import ggll.core.syntax.model.GGLLStack;
import ggll.core.syntax.model.NTerminalStack;
import ggll.core.syntax.model.ParseStack;

public class ParserStack implements Cloneable
{
	private ParseStack parseStack;
	private GGLLStack ggllStack;
	private NTerminalStack nTerminalStack;

	public ParserStack()
	{

	}

	public GGLLStack getGGLLStack()
	{
		return this.ggllStack;
	}

	public NTerminalStack getNTerminalStack()
	{
		return this.nTerminalStack;
	}

	public ParseStack getParseStack()
	{
		return this.parseStack;
	}

	public void init()
	{
		this.ggllStack = new GGLLStack();
		this.nTerminalStack = new NTerminalStack();
		this.parseStack = new ParseStack();
	}

	public void setGGLLStack(GGLLStack grViewStack)
	{
		this.ggllStack = grViewStack;
	}
	
	public void setParseStack(ParseStack parseStack)
	{
		this.parseStack = parseStack;
	}

	public void setNTerminalStack(NTerminalStack nTerminalStack)
	{
		this.nTerminalStack = nTerminalStack;
	}

	@Override
	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
