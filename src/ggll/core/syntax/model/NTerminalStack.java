package ggll.core.syntax.model;

import java.util.Stack;

public class NTerminalStack
{
	Stack<Integer> nTerminalStack;

	public NTerminalStack()
	{
		this.nTerminalStack = new Stack<Integer>();
	}

	public void clear()
	{
		this.nTerminalStack.clear();
	}

	@Override
	public NTerminalStack clone()
	{
		final NTerminalStack newInstance = new NTerminalStack();
		newInstance.nTerminalStack = (Stack<Integer>) this.nTerminalStack.clone();
		return newInstance;
	}

	public boolean empty()
	{
		return this.nTerminalStack.empty();
	}

	public Integer peak()
	{
		return this.nTerminalStack.peek();
	}

	public Integer pop()
	{
		return this.nTerminalStack.pop();
	}

	public void push(Integer item)
	{
		this.nTerminalStack.push(item);
	}

	public int size()
	{
		return this.nTerminalStack.size();
	}
	
	public boolean contains(Integer item)
	{
		return this.nTerminalStack.contains(item);
	}

}
