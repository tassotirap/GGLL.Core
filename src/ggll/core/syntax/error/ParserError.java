package ggll.core.syntax.error;

import ggll.core.exceptions.ErrorRecoveryException;
import ggll.core.exceptions.SintaticException;
import ggll.core.list.ExtendedList;
import ggll.core.syntax.model.TableGraphNode;
import ggll.core.syntax.parser.Parser;

import java.util.Stack;

public class ParserError
{
	private final Parser analyzer;

	public ParserError(Parser analyzer)
	{
		this.analyzer = analyzer;
	}

	public int dealWithError(int UI, int column, int line) throws Exception
	{
		final int lastIndexNode = UI;

		this.analyzer.setError(new SintaticException(this.analyzer.getParseToken().getCurrentToken().text, line, column));
		int IX = UI;

		final Stack<TableGraphNode> nTerminalStack = new Stack<TableGraphNode>();

		while (IX != 0)
		{
			if (this.analyzer.getParseTable().getGraphNode(IX).IsTerminal())
			{
				this.analyzer.setError(new ErrorRecoveryException(this.analyzer.getParseTable().getTermial(this.analyzer.getParseTable().getGraphNode(IX).getNodeReference()).getName() + " expected."));
				IX = this.analyzer.getParseTable().getGraphNode(IX).getAlternativeIndex();

				if (IX == 0 && nTerminalStack.size() > 0)
				{
					IX = nTerminalStack.pop().getAlternativeIndex();
				}

			}
			else
			{
				nTerminalStack.push(this.analyzer.getParseTable().getGraphNode(IX));
				IX = this.analyzer.getParseTable().getNTerminal(this.analyzer.getParseTable().getGraphNode(IX).getNodeReference()).getFirstNode();
			}
		}

		final ExtendedList<IErroStrategy> strategyList = new ExtendedList<IErroStrategy>();

		strategyList.append(new DeleteStrategy(this.analyzer));
		strategyList.append(new InsertStrategy(this.analyzer));
		strategyList.append(new ChangeStrategy(this.analyzer));
		strategyList.append(new DelimiterSearchStrategy(this.analyzer));

		int I = UI;

		for (final IErroStrategy errorStrategy : strategyList.getAll())
		{
			I = errorStrategy.tryFix(lastIndexNode, column, line);
			if (I >= 0)
			{
				return I;
			}
		}

		if (I < 0)
		{
			this.analyzer.getParseToken().readNext();
			if (this.analyzer.getParseToken().getCurrentToken().text.equals("$"))
			{
				return I;
			}
			else
			{
				I = dealWithError(lastIndexNode, this.analyzer.getParseToken().getCurrentToken().column + 1, this.analyzer.getParseToken().getCurrentToken().line + 1);
			}
		}
		return I;
	}
}
