package ggll.core.syntax.model;

import java.io.Serializable;

public class TableGraphNode implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int alternativeIndex;
	private String semanticRoutine;
	private int nodeReference;
	private int sucessorIndex;
	private boolean isTerminal;

	public TableGraphNode()
	{

	}

	public TableGraphNode(int alternativeIndex, boolean isTerminal, int nodeReference, String semanticRoutine, int sucessorIndex)
	{
		this.alternativeIndex = alternativeIndex;
		this.isTerminal = isTerminal;
		this.nodeReference = nodeReference;
		this.semanticRoutine = semanticRoutine;
		this.sucessorIndex = sucessorIndex;
	}

	public int getAlternativeIndex()
	{
		return this.alternativeIndex;
	}

	public int getNodeReference()
	{
		return this.nodeReference;
	}

	public String getSemanticRoutine()
	{
		return this.semanticRoutine;
	}

	public int getSucessorIndex()
	{
		return this.sucessorIndex;
	}

	public boolean isLambda()
	{
		return this.nodeReference == 0;
	}

	public boolean IsTerminal()
	{
		return this.isTerminal;
	}

	public void setAlternativeIndex(int node)
	{
		this.alternativeIndex = node;
	}

	public void setIsTerminal(boolean bool)
	{
		this.isTerminal = bool;
	}

	public void setNodeReference(int node)
	{
		this.nodeReference = node;
	}

	public void setSemanticRoutine(String routine)
	{
		this.semanticRoutine = routine;
	}

	public void setSucessorIndex(int node)
	{
		this.sucessorIndex = node;
	}

	@Override
	public String toString()
	{
		return this.isTerminal + " " + this.nodeReference + " " + this.alternativeIndex + " " + this.sucessorIndex + " " + this.semanticRoutine;
	}

}
