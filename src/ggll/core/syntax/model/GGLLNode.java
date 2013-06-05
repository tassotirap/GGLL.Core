package ggll.core.syntax.model;

public class GGLLNode
{
	public int indexNode;
	public int size;

	public GGLLNode(int indexNode, int size)
	{
		this.indexNode = indexNode;
		this.size = size;
	}

	@Override
	public String toString()
	{
		return indexNode + "," + size;
	}
}
