package ggll.core.syntax.model;

import java.io.Serializable;


public class TableNode implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String flag;		
	private String name;
	private int firstNode;
	
	public TableNode()
	{
		
	}
	
	public TableNode(String flag, String nodeName)
	{
		this.flag = flag;
		setName(nodeName);
		setFirstNode(-1);
	}

	public TableNode(String flag, String nodeName, int firstNode)
	{
		this.flag = flag;
		setName(nodeName);
		setFirstNode(firstNode);
	}

	public int getFirstNode()
	{
		return firstNode;
	}

	public String getFlag()
	{
		return flag;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String nodeName)
	{ 
		name = nodeName;
	}

	public void setFirstNode(int nodePrim)
	{
		firstNode = nodePrim;
	}


	public void setFlag(String flag)
	{
		this.flag = flag;
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}