package ggll.core.syntax.error;

import ggll.core.exceptions.ErrorRecoveryException;
import ggll.core.exceptions.LexicalException;
import ggll.core.syntax.model.GGLLNode;
import ggll.core.syntax.model.NTerminalStack;
import ggll.core.syntax.model.ParseNode;
import ggll.core.syntax.model.TableNode;
import ggll.core.syntax.parser.Parser;

public class ChangeStrategy extends ErroStrategy
{
	public ChangeStrategy(Parser analyzer)
	{
		super(analyzer);
	}

	@Override
	protected int tryFix(int UI, int column, int line) throws LexicalException
	{
		int IX, IY;
		int I = -1;

		IX = UI;

		this.parserToken.readNext();

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

				if(IY == 0 && this.parserToken.getCurrentToken().type.equals("EOF"))
				{
					this.parser.setError(new ErrorRecoveryException("Symbol \"" + this.parserToken.getLastToken().text + "\" has been replaced by \"" + terminalNode.getName() + "\""));
					I = IY;
				}				
				
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
							if (temp.equals(this.parserToken.getCurrentSymbol()))
							{

								this.parser.setError(new ErrorRecoveryException("Symbol \"" + this.parserToken.getLastToken().text + "\" has been replaced by \"" + terminalNode.getName() + "\""));
								this.parserStack.getParseStack().push(new ParseNode(this.analyzerTable.getTermial(this.analyzerTable.getGraphNode(IX).getNodeReference()).getFlag(), terminalNode.getName(), terminalNode.getName()));
								this.parserStack.setTop(this.parserStack.getTop() + 1);

								this.semanticRoutines.setCurrentToken(this.parserToken.getLastToken());
								this.semanticRoutines.execFunction(this.analyzerTable.getGraphNode(IX).getSemanticRoutine());
								this.parserStack.getNTerminalStack().clear();
								I = IY;
							}
							else
							{
								IY = this.analyzerAlternative.findAlternative(IY, pilhaNaoTerminalY, this.parserStack.getGGLLStack());
							}
						}
					}
					else
					{
						this.parserStack.getGGLLStack().push(new GGLLNode(IY, this.parserStack.getTop() + 2));
						pilhaNaoTerminalY.push(IY);
						IY = this.analyzerTable.getNTerminal(this.analyzerTable.getGraphNode(IY).getNodeReference()).getFirstNode();
					}
				}
				if (I < 0)
				{
					IX = this.analyzerAlternative.findAlternative(IX, this.parserStack.getNTerminalStack(), this.parserStack.getGGLLStack());
				}
			}
			else
			{
				this.parserStack.getGGLLStack().push(new GGLLNode(IX, this.parserStack.getTop() + 1));
				this.parserStack.getNTerminalStack().push(IX);
				IX = this.analyzerTable.getNTerminal(this.analyzerTable.getGraphNode(IX).getNodeReference()).getFirstNode();
			}
			iteration++;
		}

		return I;
	}
}
