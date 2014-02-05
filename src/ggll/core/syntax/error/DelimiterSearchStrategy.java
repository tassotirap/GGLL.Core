package ggll.core.syntax.error;

import ggll.core.exceptions.ErrorRecoveryException;
import ggll.core.syntax.model.TableNode;
import ggll.core.syntax.parser.Parser;

import java.util.Stack;

public class DelimiterSearchStrategy extends ErroStrategy
{
	public DelimiterSearchStrategy(Parser analyzer)
	{
		super(analyzer);
	}

	@Override
	protected int tryFix(int UI, int column, int line)
	{
		return -1;
//		int IX;
//		IX = UI;
//
//		int I = -1;
//
//		final Stack<Integer> pilhaNaoTerminalY = new Stack<Integer>();
//
//		int iteration = 0;
//		while (IX != 0 && I < 0)
//		{
//			if (iteration > this.MAX_ITERATOR)
//			{
//				break;
//			}
//			if (this.analyzerTable.getGraphNode(IX).IsTerminal())
//			{
//				final TableNode terminalNode = this.analyzerTable.getTermial(this.analyzerTable.getGraphNode(IX).getNodeReference());
//
//				if (terminalNode.getName().equals(this.parserToken.getCurrentSymbol()))
//				{
//					this.parser.setError(new ErrorRecoveryException("Symbol \"" + terminalNode.getName() + "\" at before column " + column + " assumed as delimiter."));
//					I = IX;
//				}
//				else
//				{
//					if (this.analyzerTable.getGraphNode(IX).getSucessorIndex() != 0)
//					{
//						IX = this.analyzerTable.getGraphNode(IX).getSucessorIndex();
//
//					}
//					else
//					{
//						IX = this.analyzerTable.getGraphNode(IX).getAlternativeIndex();
//					}
//				}
//			}
//			else
//			{
//				if (!pilhaNaoTerminalY.empty() && pilhaNaoTerminalY.contains(IX))
//				{
//					IX = this.analyzerTable.getGraphNode(IX).getSucessorIndex();
//					if (IX != 0 && !pilhaNaoTerminalY.empty())
//					{
//						this.analyzerTable.getGraphNode(pilhaNaoTerminalY.pop()).getAlternativeIndex();
//					}
//					else
//					{
//						IX = 0;
//					}
//				}
//				else
//				{
//					pilhaNaoTerminalY.push(IX);
//					IX = this.analyzerTable.getNTerminal(this.analyzerTable.getGraphNode(IX).getNodeReference()).getFirstNode();
//				}
//			}
//			iteration++;
//		}
//
//		return I;
	}

}
