package ggll.core.syntax.model;

import java.util.Stack;

import com.rits.cloning.Cloner;

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
		Cloner cloner = new Cloner();
		NTerminalStack newInstance = cloner.deepClone(this);
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
