package ggll.core.syntax.error;

import ggll.core.exceptions.ErrorRecoveryException;
import ggll.core.syntax.model.ParseNode;
import ggll.core.syntax.model.TableGraphNode;
import ggll.core.syntax.model.TableNode;
import ggll.core.syntax.parser.Parser;

public class InsertStrategy extends ErroStrategy
{
	public InsertStrategy(Parser analyzer)
	{
		super(analyzer);
	}

	private int getNextTerminalIndex(int Index)
	{
		while (Index > 0 && !this.analyzerTable.getGraphNode(Index).IsTerminal())
		{
			this.parserStack.getNTerminalStack().push(Index);
			Index = this.analyzerTable.getNTerminal(this.analyzerTable.getGraphNode(Index).getNodeReference()).getFirstNode();
		}
		if (Index > 0)
		{
			return Index;
		}
		else
		{
			return 0;
		}
	}

	@Override
	protected int tryFix(int UI, int column, int line)
	{
		final int insertedIndex = getNextTerminalIndex(UI);
		int nextIndex = this.analyzerTable.getGraphNode(insertedIndex).getSucessorIndex();
		if (nextIndex == 0 && this.parserStack.getNTerminalStack().size() > 0)
		{
			nextIndex = this.analyzerTable.getGraphNode(this.parserStack.getNTerminalStack().pop()).getSucessorIndex();
		}
		if (insertedIndex > 0)
		{
			TableGraphNode insertedGraphNode = this.analyzerTable.getGraphNode(insertedIndex);
			TableNode insertedNode = this.analyzerTable.getTermial(insertedGraphNode.getNodeReference());
			this.parserStack.getParseStack().push(new ParseNode(insertedNode.getFlag(), insertedNode.getName(), insertedNode.getName()));
			this.parserStack.setTop(this.parserStack.getTop() + 1);
			this.parserStack.getNTerminalStack().clear();
			if (validate(nextIndex))
			{
				
				this.parser.setError(new ErrorRecoveryException("Symbol \"" + insertedNode.getName() + "\" inserted before column " + column + "."));
				return nextIndex;
			}
		}
		return -1;
	}	
}
