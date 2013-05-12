package org.ggll.syntax.analyzer.gsll1;

import java.util.Iterator;

import org.ggll.syntax.model.ParseNode;
import org.ggll.syntax.model.ParseStack;

public class AnalyzerPrint
{
	private boolean firstTime;
	private boolean stepping;
	private Thread thread;
	
	private static AnalyzerPrint instance;
	
	public static AnalyzerPrint getInstance()
	{
		return instance;
	}
	
	public static AnalyzerPrint setInstance(Thread thread)
	{
		instance = new AnalyzerPrint(thread);
		return instance;
	}

	private AnalyzerPrint(Thread thread)
	{
		this.thread = thread;
		this.firstTime = true;
		this.stepping = false;
	}

	private void synchronize()
	{
		if (isStepping() && !firstTime)
		{
			synchronized (thread)
			{
				try
				{
					thread.wait();
				}
				catch (InterruptedException e)
				{
				}
			}
		}
	}

	public boolean isStepping()
	{
		return stepping;
	}
	
	public void clearStack()
	{
	}
	

	public void printStack(ParseStack parseStackNode)
	{
		synchronize();

		firstTime = false;
		
		Iterator<ParseNode> iterator = parseStackNode.iterator();
		ParseNode parseStackNodeTemp = null;
		String lineSyntax = "";
		String lineSemantic = "";
		while (iterator.hasNext())
		{
			parseStackNodeTemp = iterator.next();
			lineSyntax += "<a style=\"color: #000000; font-weight: bold;\" href=\"" + parseStackNodeTemp.getFlag() + "\">" + parseStackNodeTemp.getType() + "</a>&nbsp;";
			lineSemantic += parseStackNodeTemp.getSemanticSymbol() + "&nbsp;";
		}
	}

	public void setStepping(boolean stepping)
	{
		this.stepping = stepping;
	}
}
