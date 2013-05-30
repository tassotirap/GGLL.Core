package org.ggll.core.syntax.model;

import java.util.Stack;

public class GrViewStack
{
	Stack<GrViewNode> grViewStack;
	
	public GrViewStack()
	{	
		grViewStack = new Stack<GrViewNode>();
	}
	
	public void clear()
	{
		grViewStack.clear();		
	}
	
	@Override
	public GrViewStack clone()
	{
		GrViewStack newInstance = new GrViewStack();
		newInstance.grViewStack = (Stack<GrViewNode>) this.grViewStack.clone();
		return newInstance;
	}
	
	public boolean empty()
	{
		return grViewStack.empty();
	}
	
	public GrViewNode peak()
	{
		return grViewStack.peek();
	}
	
	public GrViewNode pop()
	{
		return grViewStack.pop();
	}
	
	public void push(GrViewNode item)
	{
		grViewStack.push(item);
	}
	
	public int size()
	{
		return grViewStack.size();		
	}
}
