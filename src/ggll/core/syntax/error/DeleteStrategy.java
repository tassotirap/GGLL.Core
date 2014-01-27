package ggll.core.syntax.error;

import ggll.core.exceptions.ErrorRecoveryException;
import ggll.core.exceptions.LexicalException;
import ggll.core.syntax.model.GGLLNode;
import ggll.core.syntax.model.TableGraphNode;
import ggll.core.syntax.model.TableNode;
import ggll.core.syntax.parser.Parser;

public class DeleteStrategy extends ErroStrategy
{
	public DeleteStrategy(Parser analyzer)
	{
		super(analyzer);
	}

	@Override
	protected int tryFix(int currentIndex, int column, int line) throws LexicalException
	{
		int returnIndex = -1;
		int tempIndex = currentIndex;

		this.parserToken.readNext();
		
		//End of file in stater node
		if(tempIndex == 0 && this.parserToken.getCurrentToken().type.equals("EOF"))
		{
			this.parser.setError(new ErrorRecoveryException("Symbol \"" + this.parserToken.getLastToken().text + "\" was ignored."));
			returnIndex = tempIndex;
		}

		int iteration = 0;
		while (tempIndex != 0 && iteration < this.MAX_ITERATOR)
		{
			final TableGraphNode graphNode = this.analyzerTable.getGraphNode(tempIndex);
			if (this.analyzerTable.getGraphNode(tempIndex).IsTerminal())
			{
				final TableNode terminalNode = this.analyzerTable.getTermial(graphNode.getNodeReference());
				if (terminalNode.getName().equals(this.parserToken.getCurrentSymbol()))
				{
					this.parser.setError(new ErrorRecoveryException("Symbol \"" + this.parserToken.getLastToken().text + "\" was ignored."));
					returnIndex = tempIndex;
					break;
				}
				else
				{
					int alternative = this.analyzerAlternative.findAlternative(tempIndex, this.parserStack.getNTerminalStack(), this.parserStack.getGGLLStack());
					tempIndex = alternative;
				}
			}
			else
			{

				final TableNode nTerminalNode = this.analyzerTable.getNTerminal(graphNode.getNodeReference());
				this.parserStack.setTop(this.parserStack.getTop() + 1);
				this.parserStack.getGGLLStack().push(new GGLLNode(tempIndex, this.parserStack.getTop()));
				this.parserStack.getNTerminalStack().push(tempIndex);
				tempIndex = nTerminalNode.getFirstNode();
			}
			iteration++;
		}		

		return returnIndex;
	}

}
