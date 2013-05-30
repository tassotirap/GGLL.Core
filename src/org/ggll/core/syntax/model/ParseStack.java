package org.ggll.core.syntax.model;

import java.util.Iterator;
import java.util.Stack;

public class ParseStack
{
	Stack<ParseNode> parseNode;
	
	public ParseStack()
	{	
		parseNode = new Stack<ParseNode>();
	}
	
	public void clear()
	{
		parseNode.clear();		
	}
	
	@Override
	public ParseStack clone()
	{
		return (ParseStack)this.clone();
	}
	
	public ParseNode elementAt(int index)
	{
		return parseNode.elementAt(index);
	}
	
	public boolean empty()
	{
		return parseNode.empty();
	}
	
	public Iterator<ParseNode> iterator()
	{
		return parseNode.iterator();
	}
	
	public ParseNode peek()
	{
		return parseNode.peek();
	}
	
	public ParseNode pop()
	{
		return parseNode.pop();
	}
	
	public void push(ParseNode item)
	{
		parseNode.push(item);
	}
	
	public int size()
	{
		return parseNode.size();		
	}
	
	public int topIndex()
	{
		return parseNode.size() - 1;
	}
}
