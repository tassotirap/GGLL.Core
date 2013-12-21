package ggll.core.syntax.parser;

import ggll.core.syntax.model.GGLLStack;
import ggll.core.syntax.model.NTerminalStack;
import ggll.core.syntax.model.ParseStack;

public class ParserStack
{
	private ParseStack parseStack;
	private GGLLStack grViewStack;
	private NTerminalStack nTerminalStack;
	private int top;

	public ParserStack()
	{

	}

	public GGLLStack getGGLLStack()
	{
		return this.grViewStack;
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
		this.grViewStack = new GGLLStack();
		this.nTerminalStack = new NTerminalStack();
		this.parseStack = new ParseStack();
	}

	public void setGGLLStack(GGLLStack grViewStack)
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
