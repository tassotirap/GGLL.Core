package ggll.core.syntax.error;

import ggll.core.exceptions.ErrorRecoveryException;
import ggll.core.syntax.model.GGLLNode;
import ggll.core.syntax.model.NTerminalStack;
import ggll.core.syntax.model.ParseNode;
import ggll.core.syntax.model.TableNode;
import ggll.core.syntax.parser.Parser;

public class ChangeStrategy extends IErroStrategy
{
	public ChangeStrategy(Parser analyzer)
	{
		super(analyzer);
	}

	@Override
	public int tryFix(int UI, int column, int line) throws Exception
	{
		int IX, IY;
		int I = -1;

		IX = UI;

		init();

		this.analyzerToken.readNext();

		int iteration = 0;
		while (IX != 0 && I < 0)
		{
			if (iteration > this.MAX_ITERATOR)
			{
				break;
			}
			if (this.analyzerTable.getGraphNode(IX).IsTerminal())
			{
				final NTerminalStack pilhaNaoTerminalY = new NTerminalStack();

				final TableNode terminalNode = this.analyzerTable.getTermial(this.analyzerTable.getGraphNode(IX).getNodeReference());
				IY = this.analyzerTable.getGraphNode(IX).getSucessorIndex();

				while (IY != 0 && I < 0)
				{
					if (this.analyzerTable.getGraphNode(IY).IsTerminal())
					{
						if (this.analyzerTable.getGraphNode(IY).getNodeReference() == 0)
						{
							IY = this.analyzerTable.getGraphNode(IY).getSucessorIndex();
						}
						else
						{
							final String temp = this.analyzerTable.getTermial(this.analyzerTable.getGraphNode(IY).getNodeReference()).getName();
							if (temp.equals(this.analyzerToken.getCurrentSymbol()))
							{

								this.analyzer.setError(new ErrorRecoveryException("Symbol \"" + this.analyzerToken.getLastToken().text + "\" has been replaced by \"" + terminalNode.getName() + "\""));
								this.analyzerStack.getParseStack().push(new ParseNode(this.analyzerTable.getTermial(this.analyzerTable.getGraphNode(IX).getNodeReference()).getFlag(), terminalNode.getName(), terminalNode.getName()));
								this.analyzerStack.setTop(this.analyzerStack.getTop() + 1);

								this.semanticRoutines.setCurrentToken(this.analyzerToken.getLastToken());
								this.semanticRoutines.execFunction(this.analyzerTable.getGraphNode(IX).getSemanticRoutine());
								this.analyzerStack.getNTerminalStack().clear();
								I = IY;
							}
							else
							{
								IY = this.analyzerAlternative.findAlternative(IY, pilhaNaoTerminalY, this.analyzerStack.getGGLLStack());
							}
						}
					}
					else
					{
						this.analyzerStack.getGGLLStack().push(new GGLLNode(IY, this.analyzerStack.getTop() + 2));
						pilhaNaoTerminalY.push(IY);
						IY = this.analyzerTable.getNTerminal(this.analyzerTable.getGraphNode(IY).getNodeReference()).getFirstNode();
					}
				}
				if (I < 0)
				{
					IX = this.analyzerAlternative.findAlternative(IX, this.analyzerStack.getNTerminalStack(), this.analyzerStack.getGGLLStack());
				}
			}
			else
			{
				this.analyzerStack.getGGLLStack().push(new GGLLNode(IX, this.analyzerStack.getTop() + 1));
				this.analyzerStack.getNTerminalStack().push(IX);
				IX = this.analyzerTable.getNTerminal(this.analyzerTable.getGraphNode(IX).getNodeReference()).getFirstNode();
			}
			iteration++;
		}

		if (I < 0)
		{
			restore(true);
		}
		return I;
	}
}
