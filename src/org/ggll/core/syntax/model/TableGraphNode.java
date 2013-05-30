package org.ggll.core.syntax.model;

import java.io.Serializable;


/*
 * Created on 11/08/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TableGraphNode implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int alternativeIndex;
	
	private String semanticRoutine;
	
	private int nodeReference;
	
	private int sucessorIndex;
	
	private boolean isTerminal;

	public TableGraphNode()
	{
	}

	public int getAlternativeIndex()
	{
		return alternativeIndex;
	}

	public int getNodeReference()
	{
		return nodeReference;
	}

	public String getSemanticRoutine()
	{
		return semanticRoutine;
	}

	public int getSucessorIndex()
	{
		return sucessorIndex;
	}

	public boolean isLambda()
	{
		return nodeReference == 0;
	}

	public boolean IsTerminal()
	{
		return isTerminal;
	}

	public void setAlternativeIndex(int node)
	{
		alternativeIndex = node;
	}

	public void setIsTerminal(boolean bool)
	{
		isTerminal = bool;
	}

	public void setNodeReference(int node)
	{
		nodeReference = node;
	}

	public void setSemanticRoutine(String routine)
	{
		semanticRoutine = routine;
	}

	public void setSucessorIndex(int node)
	{
		sucessorIndex = node;
	}

	@Override
	public String toString()
	{
		return isTerminal + " " + nodeReference + " " + alternativeIndex + " " + sucessorIndex + " " + semanticRoutine;
	}

}
