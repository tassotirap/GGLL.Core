package ggll.core.syntax.error;

import ggll.core.exceptions.ErrorRecoveryException;
import ggll.core.syntax.model.GGLLNode;
import ggll.core.syntax.model.ParseNode;
import ggll.core.syntax.model.TableNode;
import ggll.core.syntax.parser.Parser;

public class InsertStrategy extends ErroStrategy
{
	public InsertStrategy(Parser analyzer)
	{
		super(analyzer);
	}

	private int getNextTerminal(int Index)
	{
		while (Index > 0 && !this.analyzerTable.getGraphNode(Index).IsTerminal())
		{
			this.parserStack.getGGLLStack().push(new GGLLNode(Index, this.parserStack.getTop() + 1));
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
		int I = -1;
		final int prevTableGraphNode = getNextTerminal(UI);
		final TableNode prevNode = this.analyzerTable.getTermial(this.analyzerTable.getGraphNode(prevTableGraphNode).getNodeReference());
		final int currentTableGraphNode = getNextTerminal(this.analyzerTable.getGraphNode(prevTableGraphNode).getSucessorIndex());

		if (currentTableGraphNode > 0)
		{
			final TableNode currentNode = this.analyzerTable.getTermial(this.analyzerTable.getGraphNode(currentTableGraphNode).getNodeReference());
			if (currentNode.getName().equals(this.parserToken.getCurrentSymbol()))
			{
				I = currentTableGraphNode;
				this.parser.setError(new ErrorRecoveryException("Symbol \"" + prevNode.getName() + "\" inserted before column " + column + "."));
				this.parserStack.getParseStack().push(new ParseNode(prevNode.getFlag(), prevNode.getName(), prevNode.getName()));
				this.parserStack.setTop(this.parserStack.getTop() + 1);
				this.semanticRoutines.setCurrentToken(this.parserToken.getLastToken());
				this.semanticRoutines.execFunction(this.analyzerTable.getGraphNode(prevTableGraphNode).getSemanticRoutine());
			}
		}
		else if (currentTableGraphNode == 0)
		{
			I = currentTableGraphNode;
			this.parser.setError(new ErrorRecoveryException("Symbol \"" + prevNode.getName() + "\" inserted before column " + column + "."));
			this.parserStack.getParseStack().push(new ParseNode(prevNode.getFlag(), prevNode.getName(), prevNode.getName()));
			this.parserStack.setTop(this.parserStack.getTop() + 1);
			this.semanticRoutines.setCurrentToken(this.parserToken.getLastToken());
			this.semanticRoutines.execFunction(this.analyzerTable.getGraphNode(prevTableGraphNode).getSemanticRoutine());
		}

		return I;
	}
}
