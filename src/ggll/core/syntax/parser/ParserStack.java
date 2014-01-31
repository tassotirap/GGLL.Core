package ggll.core.syntax.parser;

import ggll.core.syntax.model.GGLLStack;
import ggll.core.syntax.model.NTerminalStack;
import ggll.core.syntax.model.ParseStack;

public class ParserStack implements Cloneable
{
	private ParseStack parseStack;
	private GGLLStack ggllStack;
	private NTerminalStack nTerminalStack;
	private int top;

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

	public int getTop()
	{
		return this.top;
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

	public void setNTerminalStack(NTerminalStack nTerminalStack)
	{
		this.nTerminalStack = nTerminalStack;
	}

	public void setTop(int top)
	{
		this.top = top;
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
