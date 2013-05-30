package org.ggll.core.syntax.error;

import org.ggll.core.syntax.model.GrViewNode;
import org.ggll.core.syntax.model.TableGraphNode;
import org.ggll.core.syntax.model.TableNode;
import org.ggll.core.syntax.parser.Parser;

public class DeleteStrategy extends IErroStrategy
{
	public DeleteStrategy(Parser analyzer)
	{
		super(analyzer);
	}

	@Override
	public int tryFix(int UI, int column, int line) throws Exception
	{
		int I = -1;
		int IX = UI;

		init();

		analyzerToken.readNext();

		int iteration = 0;
		while (IX != 0)
		{
			if(iteration > MAX_ITERATOR)
			{
				break;
			}
			
			TableGraphNode graphNode = analyzerTable.getGraphNode(IX);
			if (analyzerTable.getGraphNode(IX).IsTerminal())
			{
				TableNode terminalNode = analyzerTable.getTermial(graphNode.getNodeReference());

				if (terminalNode.getName().equals(analyzerToken.getCurrentSymbol()))
				{
					analyzer.setError("Symbol \"" + analyzerToken.getLastToken().text + "\" was ignored.");
					I = IX;
					break;
				}
				else
				{
					int alternative = 0;
					alternative = analyzerAlternative.findAlternative(IX, analyzerStack.getNTerminalStack(), analyzerStack.getGrViewStack());
					IX = alternative;
				}
			}
			else
			{

				TableNode nTerminalNode = analyzerTable.getNTerminal(graphNode.getNodeReference());
				analyzerStack.setTop(analyzerStack.getTop() + 1);
				analyzerStack.getGrViewStack().push(new GrViewNode(IX, analyzerStack.getTop()));
				analyzerStack.getNTerminalStack().push(IX);
				IX = nTerminalNode.getFirstNode();
			}
			iteration++;
		}

		if (I < 0)
		{
			analyzerToken.setCurrentToken(analyzerToken.getLastToken());
			analyzerToken.getYylex().pushback(analyzerToken.getYylex().yylength());

			restore(true);
		}
		return I;
	}

}
