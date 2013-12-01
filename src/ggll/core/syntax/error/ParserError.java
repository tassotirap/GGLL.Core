package ggll.core.syntax.error;

import ggll.core.exceptions.ErrorRecoveryException;
import ggll.core.exceptions.SintaticException;
import ggll.core.list.ExtendedList;
import ggll.core.syntax.model.TableGraphNode;
import ggll.core.syntax.parser.Parser;

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

		analyzer.setError(new SintaticException(analyzer.getParseToken().getCurrentToken().text, line, column));
		int IX = UI;

		Stack<TableGraphNode> nTerminalStack = new Stack<TableGraphNode>();

		while (IX != 0)
		{
			if (analyzer.getParseTable().getGraphNode(IX).IsTerminal())
			{
				analyzer.setError(new ErrorRecoveryException(analyzer.getParseTable().getTermial(analyzer.getParseTable().getGraphNode(IX).getNodeReference()).getName() + " expected."));
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

		ExtendedList<IErroStrategy> strategyList = new ExtendedList<IErroStrategy>();

		strategyList.append(new DeleteStrategy(analyzer));
		strategyList.append(new InsertStrategy(analyzer));
		strategyList.append(new ChangeStrategy(analyzer));
		strategyList.append(new DelimiterSearchStrategy(analyzer));

		int I = UI;

		for (IErroStrategy errorStrategy : strategyList.getAll())
		{
			I = errorStrategy.tryFix(lastIndexNode, column, line);
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
				I = dealWithError(lastIndexNode, analyzer.getParseToken().getCurrentToken().column + 1, analyzer.getParseToken().getCurrentToken().line + 1);
			}
		}
		return I;
	}
}
