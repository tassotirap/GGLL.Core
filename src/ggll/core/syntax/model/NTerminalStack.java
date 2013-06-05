package ggll.core.syntax.model;

import java.util.Stack;

public class NTerminalStack
{
	Stack<Integer> nTerminalStack;
	
	public NTerminalStack()
	{	
		nTerminalStack = new Stack<Integer>();
	}
	
	public void clear()
	{
		nTerminalStack.clear();		
	}
	
	@Override
	public NTerminalStack clone()
	{
		NTerminalStack newInstance = new NTerminalStack();
		newInstance.nTerminalStack = (Stack<Integer>) this.nTerminalStack.clone();
		return newInstance;
	}
	
	public boolean empty()
	{
		return nTerminalStack.empty();
	}
	
	public Integer peak()
	{
		return nTerminalStack.peek();
	}
	
	public Integer pop()
	{
		return nTerminalStack.pop();
	}
	
	public void push(Integer item)
	{
		nTerminalStack.push(item);
	}
	
	public int size()
	{
		return nTerminalStack.size();		
	}

}
