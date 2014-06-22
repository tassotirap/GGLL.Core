package ggll.core.syntax.model;

import java.util.Stack;

import com.rits.cloning.Cloner;
import com.rits.cloning.ObjenesisInstantiationStrategy;

public class GGLLStack
{
	Stack<GGLLNode> ggllStack;

	public GGLLStack()
	{
		this.ggllStack = new Stack<GGLLNode>();
	}

	public void clear()
	{
		this.ggllStack.clear();
	}

	public GGLLStack clone()
	{
		Cloner cloner = new Cloner(new ObjenesisInstantiationStrategy());
		GGLLStack newInstance = cloner.deepClone(this);
		return newInstance;
	}

	public boolean empty()
	{
		return this.ggllStack.empty();
	}

	public GGLLNode peak()
	{
		return this.ggllStack.peek();
	}

	public GGLLNode pop()
	{
		return this.ggllStack.pop();
	}

	public void push(GGLLNode item)
	{
		this.ggllStack.push(item);
	}

	public int size()
	{
		return this.ggllStack.size();
	}
}
