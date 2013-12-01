package ggll.core.syntax.model;

import java.util.Stack;

public class GGLLStack
{
	Stack<GGLLNode> ggllStack;

	public GGLLStack()
	{
		ggllStack = new Stack<GGLLNode>();
	}

	public void clear()
	{
		ggllStack.clear();
	}

	@Override
	public GGLLStack clone()
	{
		GGLLStack newInstance = new GGLLStack();
		newInstance.ggllStack = (Stack<GGLLNode>) this.ggllStack.clone();
		return newInstance;
	}

	public boolean empty()
	{
		return ggllStack.empty();
	}

	public GGLLNode peak()
	{
		return ggllStack.peek();
	}

	public GGLLNode pop()
	{
		return ggllStack.pop();
	}

	public void push(GGLLNode item)
	{
		ggllStack.push(item);
	}

	public int size()
	{
		return ggllStack.size();
	}
}
