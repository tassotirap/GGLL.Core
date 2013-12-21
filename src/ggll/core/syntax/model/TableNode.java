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
		return this.firstNode;
	}

	public String getFlag()
	{
		return this.flag;
	}

	public String getName()
	{
		return this.name;
	}

	public void setFirstNode(int nodePrim)
	{
		this.firstNode = nodePrim;
	}

	public void setFlag(String flag)
	{
		this.flag = flag;
	}

	public void setName(String nodeName)
	{
		this.name = nodeName;
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}
