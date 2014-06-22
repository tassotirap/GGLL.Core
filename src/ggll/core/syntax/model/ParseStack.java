package ggll.core.syntax.model;

import java.util.Iterator;
import java.util.Stack;

import com.rits.cloning.Cloner;
import com.rits.cloning.ObjenesisInstantiationStrategy;

public class ParseStack
{
	Stack<ParseNode> parseNode;

	public ParseStack()
	{
		this.parseNode = new Stack<ParseNode>();
	}

	public void clear()
	{
		this.parseNode.clear();
	}

	public ParseStack clone()
	{
		Cloner cloner = new Cloner(new ObjenesisInstantiationStrategy());
		ParseStack newInstance = cloner.deepClone(this);
		return newInstance;
	}

	public ParseNode elementAt(int index)
	{
		return this.parseNode.elementAt(index);
	}

	public boolean empty()
	{
		return this.parseNode.empty();
	}

	public Iterator<ParseNode> iterator()
	{
		return this.parseNode.iterator();
	}

	public ParseNode peek()
	{
		return this.parseNode.peek();
	}

	public ParseNode pop()
	{
		return this.parseNode.pop();
	}

	public void push(ParseNode item)
	{
		this.parseNode.push(item);
	}

	public int size()
	{
		return this.parseNode.size();
	}

	public int topIndex()
	{
		return this.parseNode.size() - 1;
	}
}
