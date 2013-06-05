package ggll.core.syntax.model;

import java.util.Stack;

public class GGLLStack
{
	Stack<GGLLNode> grViewStack;
	
	public GGLLStack()
	{	
		grViewStack = new Stack<GGLLNode>();
	}
	
	public void clear()
	{
		grViewStack.clear();		
	}
	
	@Override
	public GGLLStack clone()
	{
		GGLLStack newInstance = new GGLLStack();
		newInstance.grViewStack = (Stack<GGLLNode>) this.grViewStack.clone();
		return newInstance;
	}
	
	public boolean empty()
	{
		return grViewStack.empty();
	}
	
	public GGLLNode peak()
	{
		return grViewStack.peek();
	}
	
	public GGLLNode pop()
	{
		return grViewStack.pop();
	}
	
	public void push(GGLLNode item)
	{
		grViewStack.push(item);
	}
	
	public int size()
	{
		return grViewStack.size();		
	}
}
