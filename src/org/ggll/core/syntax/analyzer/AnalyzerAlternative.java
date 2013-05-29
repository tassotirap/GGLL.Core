package org.ggll.core.syntax.analyzer;

import org.ggll.core.syntax.model.GrViewStack;
import org.ggll.core.syntax.model.NTerminalStack;

public class AnalyzerAlternative
{
	AnalyzerTable analyzerTabs;

	private static AnalyzerAlternative instance;

	public static AnalyzerAlternative getInstance()
	{
		return instance;
	}

	public static AnalyzerAlternative setInstance(AnalyzerTable analyzerTabs)
	{
		instance = new AnalyzerAlternative(analyzerTabs);
		return instance;
	}

	private AnalyzerAlternative(AnalyzerTable analyzerTabs)
	{
		this.analyzerTabs = analyzerTabs;
	}

	public int findAlternative(int indexNode, NTerminalStack nTermStack, GrViewStack grViewStack)
	{
		int alternative = 0;
		alternative = analyzerTabs.getGraphNode(indexNode).getAlternativeIndex();
		while (alternative == 0 && !nTermStack.empty())
		{
			grViewStack.pop();
			alternative = analyzerTabs.getGraphNode(nTermStack.pop()).getAlternativeIndex();
		}
		return alternative;
	}
}
