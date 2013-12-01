package ggll.core.syntax.model;

public class GGLLNode
{
	public int index;
	public int size;

	public GGLLNode(int index, int size)
	{
		this.index = index;
		this.size = size;
	}

	@Override
	public String toString()
	{
		return index + "," + size;
	}
}
