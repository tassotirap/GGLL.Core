package ggll.core.syntax.error;

import ggll.core.syntax.model.TableGraphNode;
import ggll.core.syntax.parser.Parser;

import java.util.ArrayList;
import java.util.Stack;


public class ParserError
{
	private Parser analyzer;

	public ParserError(Parser analyzer)
	{
		this.analyzer = analyzer;
	}

	public int dealWithError(int UI, int column, int line) throws Exception
	{
		int lastIndexNode = UI;

		analyzer.setError("Error found at the symbol " + analyzer.getParseToken().getCurrentToken().text + " of line: " + line + ", column: " + column + ".");
		int IX = UI;

		Stack<TableGraphNode> nTerminalStack = new Stack<TableGraphNode>();

		while (IX != 0)
		{
			if (analyzer.getParseTable().getGraphNode(IX).IsTerminal())
			{
				analyzer.setError(analyzer.getParseTable().getTermial(analyzer.getParseTable().getGraphNode(IX).getNodeReference()).getName() + " expected.");
				IX = analyzer.getParseTable().getGraphNode(IX).getAlternativeIndex();

				if (IX == 0 && nTerminalStack.size() > 0)
				{
					IX = nTerminalStack.pop().getAlternativeIndex();
				}

			}
			else
			{
				nTerminalStack.push(analyzer.getParseTable().getGraphNode(IX));
				IX = analyzer.getParseTable().getNTerminal(analyzer.getParseTable().getGraphNode(IX).getNodeReference()).getFirstNode();
			}
		}

		ArrayList<IErroStrategy> strategyList = new ArrayList<IErroStrategy>();
		
		strategyList.add(new DeleteStrategy(analyzer));
		strategyList.add(new InsertStrategy(analyzer));		
		strategyList.add(new ChangeStrategy(analyzer));
		strategyList.add(new DelimiterSearchStrategy(analyzer));

		int I = UI;

		for (IErroStrategy errorStrategy : strategyList)
		{
			I = errorStrategy.tryFix(lastIndexNode,  column, line);
			if (I >= 0)
			{
				return I;
			}
		}

		if (I < 0)
		{			
			analyzer.getParseToken().readNext();
			if (analyzer.getParseToken().getCurrentToken().text.equals("$"))
			{
				return I;
			}
			else
			{
				I = dealWithError(lastIndexNode, analyzer.getParseToken().getCurrentToken().charBegin + 1, analyzer.getParseToken().getCurrentToken().line + 1);
			}
		}
		return I;
	}
}
