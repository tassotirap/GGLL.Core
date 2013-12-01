package ggll.core.syntax.error;

import ggll.core.exceptions.ErrorRecoveryException;
import ggll.core.syntax.model.TableNode;
import ggll.core.syntax.parser.Parser;

import java.util.Stack;

public class DelimiterSearchStrategy extends IErroStrategy
{
	public DelimiterSearchStrategy(Parser analyzer)
	{
		super(analyzer);
	}

	@Override
	public int tryFix(int UI, int column, int line) throws Exception
	{
		int IX;
		IX = UI;

		int I = -1;

		Stack<Integer> pilhaNaoTerminalY = new Stack<Integer>();

		init();

		int iteration = 0;
		while (IX != 0 && I < 0)
		{
			if (iteration > MAX_ITERATOR)
			{
				break;
			}
			if (analyzerTable.getGraphNode(IX).IsTerminal())
			{
				TableNode terminalNode = analyzerTable.getTermial(analyzerTable.getGraphNode(IX).getNodeReference());

				if (terminalNode.getName().equals(analyzerToken.getCurrentSymbol()))
				{
					analyzer.setError(new ErrorRecoveryException("Symbol \"" + terminalNode.getName() + "\" at before column " + column + " assumed as delimiter."));
					I = IX;
				}
				else
				{
					if (analyzerTable.getGraphNode(IX).getSucessorIndex() != 0)
					{
						IX = analyzerTable.getGraphNode(IX).getSucessorIndex();

					}
					else
					{
						IX = analyzerTable.getGraphNode(IX).getAlternativeIndex();
					}
				}
			}
			else
			{
				if (!pilhaNaoTerminalY.empty() && pilhaNaoTerminalY.contains(IX))
				{
					IX = analyzerTable.getGraphNode(IX).getSucessorIndex();
					if (IX != 0 && !pilhaNaoTerminalY.empty())
					{
						analyzerTable.getGraphNode(pilhaNaoTerminalY.pop()).getAlternativeIndex();
					}
					else
					{
						IX = 0;
					}
				}
				else
				{
					pilhaNaoTerminalY.push(IX);
					IX = analyzerTable.getNTerminal(analyzerTable.getGraphNode(IX).getNodeReference()).getFirstNode();
				}
			}
			iteration++;
		}
		if (I < 0)
		{
			restore(false);
		}

		return I;
	}

}
