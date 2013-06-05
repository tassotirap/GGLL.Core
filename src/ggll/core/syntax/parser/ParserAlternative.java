package ggll.core.syntax.parser;

import ggll.core.syntax.model.GGLLStack;
import ggll.core.syntax.model.NTerminalStack;

public class ParserAlternative
{
	Parser parser;

	public ParserAlternative(Parser parser)
	{
		this.parser = parser;
	}

	public int findAlternative(int indexNode, NTerminalStack nTermStack, GGLLStack ggllStack)
	{
		int alternative = 0;
		alternative = parser.getParseTable().getGraphNode(indexNode).getAlternativeIndex();
		while (alternative == 0 && !nTermStack.empty())
		{
			ggllStack.pop();
			alternative = parser.getParseTable().getGraphNode(nTermStack.pop()).getAlternativeIndex();
		}
		return alternative;
	}
}
