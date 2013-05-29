package org.ggll.core.syntax.error;

import java.util.Stack;

import org.ggll.core.CoreManager;
import org.ggll.core.syntax.analyzer.AnalyzerAlternative;
import org.ggll.core.syntax.analyzer.AnalyzerStackRepository;
import org.ggll.core.syntax.analyzer.AnalyzerTable;
import org.ggll.core.syntax.analyzer.AnalyzerToken;
import org.ggll.core.syntax.model.TableNode;

public class DelimiterSearchStrategy extends IErroStrategy
{
	private AnalyzerTable analyzerTable;
	private AnalyzerToken analyzerToken;

	public DelimiterSearchStrategy(AnalyzerTable analyzerTable)
	{
		super(analyzerTable);
		this.analyzerTable = analyzerTable;
		this.analyzerStack = AnalyzerStackRepository.getInstance();
		this.analyzerAlternative = AnalyzerAlternative.getInstance();
		this.analyzerToken = AnalyzerToken.getInstance();
	}

	@Override
	public int tryFix(int UI, int column, int line)
	{
		int IX;
		IX = UI;

		int I = -1;

		Stack<Integer> pilhaNaoTerminalY = new Stack<Integer>();

		init();

		while (IX != 0 && I < 0)
		{
			if (analyzerTable.getGraphNode(IX).IsTerminal())
			{
				TableNode terminalNode = analyzerTable.getTermial(analyzerTable.getGraphNode(IX).getNodeReference());

				if (terminalNode.getName().equals(analyzerToken.getCurrentSymbol()))
				{
					CoreManager.setError("Symbol \"" + terminalNode.getName() + "\" at before column " + column + " assumed as delimiter.");
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
		}
		if (I < 0)
		{
			restore(false);
		}

		return I;
	}

}
