package org.ggll.core.syntax.parser;

import org.ggll.core.syntax.model.GrViewStack;
import org.ggll.core.syntax.model.NTerminalStack;

public class ParserAlternative
{
	Parser analyzer;

	public ParserAlternative(Parser analyzer)
	{
		this.analyzer = analyzer;
	}

	public int findAlternative(int indexNode, NTerminalStack nTermStack, GrViewStack grViewStack)
	{
		int alternative = 0;
		alternative = analyzer.getParseTable().getGraphNode(indexNode).getAlternativeIndex();
		while (alternative == 0 && !nTermStack.empty())
		{
			grViewStack.pop();
			alternative = analyzer.getParseTable().getGraphNode(nTermStack.pop()).getAlternativeIndex();
		}
		return alternative;
	}
}
