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
		grViewStack = new GGLLStack();
		nTerminalStack = new NTerminalStack();
		parseStack = new ParseStack();
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
