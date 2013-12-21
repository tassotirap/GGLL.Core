package ggll.core.syntax.error;

import ggll.core.exceptions.ErrorRecoveryException;
import ggll.core.syntax.model.GGLLNode;
import ggll.core.syntax.model.TableGraphNode;
import ggll.core.syntax.model.TableNode;
import ggll.core.syntax.parser.Parser;

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

		this.analyzerToken.readNext();

		int iteration = 0;
		while (IX != 0)
		{
			if (iteration > this.MAX_ITERATOR)
			{
				break;
			}

			final TableGraphNode graphNode = this.analyzerTable.getGraphNode(IX);
			if (this.analyzerTable.getGraphNode(IX).IsTerminal())
			{
				final TableNode terminalNode = this.analyzerTable.getTermial(graphNode.getNodeReference());

				if (terminalNode.getName().equals(this.analyzerToken.getCurrentSymbol()))
				{
					this.analyzer.setError(new ErrorRecoveryException("Symbol \"" + this.analyzerToken.getLastToken().text + "\" was ignored."));
					I = IX;
					break;
				}
				else
				{
					int alternative = 0;
					alternative = this.analyzerAlternative.findAlternative(IX, this.analyzerStack.getNTerminalStack(), this.analyzerStack.getGGLLStack());
					IX = alternative;
				}
			}
			else
			{

				final TableNode nTerminalNode = this.analyzerTable.getNTerminal(graphNode.getNodeReference());
				this.analyzerStack.setTop(this.analyzerStack.getTop() + 1);
				this.analyzerStack.getGGLLStack().push(new GGLLNode(IX, this.analyzerStack.getTop()));
				this.analyzerStack.getNTerminalStack().push(IX);
				IX = nTerminalNode.getFirstNode();
			}
			iteration++;
		}

		if (I < 0)
		{
			this.analyzerToken.setCurrentToken(this.analyzerToken.getLastToken());
			this.analyzerToken.getYylex().pushback(this.analyzerToken.getYylex().yylength());

			restore(true);
		}
		return I;
	}

}
