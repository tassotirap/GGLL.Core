package org.ggll.syntax.analyzer.gsll1.error;

import java.util.Stack;

import org.ggll.core.CoreManager;
import org.ggll.syntax.analyzer.gsll1.AnalyzerAlternative;
import org.ggll.syntax.analyzer.gsll1.AnalyzerStackRepository;
import org.ggll.syntax.analyzer.gsll1.AnalyzerTableRepository;
import org.ggll.syntax.analyzer.gsll1.AnalyzerToken;
import org.ggll.syntax.model.TableNode;

public class DelimiterSearchStrategy extends IErroStrategy
{
	private AnalyzerTableRepository analyzerTable;
	private AnalyzerToken analyzerToken;

	public DelimiterSearchStrategy()
	{
		this.analyzerTable = AnalyzerTableRepository.getInstance();
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
