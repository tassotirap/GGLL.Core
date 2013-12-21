package ggll.core.syntax.error;

import ggll.core.exceptions.ErrorRecoveryException;
import ggll.core.syntax.model.GGLLNode;
import ggll.core.syntax.model.ParseNode;
import ggll.core.syntax.model.TableNode;
import ggll.core.syntax.parser.Parser;

public class InsertStrategy extends IErroStrategy
{
	public InsertStrategy(Parser analyzer)
	{
		super(analyzer);
	}

	private int getNextTerminal(int Index)
	{
		while (Index > 0 && !this.analyzerTable.getGraphNode(Index).IsTerminal())
		{
			this.analyzerStack.getGGLLStack().push(new GGLLNode(Index, this.analyzerStack.getTop() + 1));
			this.analyzerStack.getNTerminalStack().push(Index);
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
	public int tryFix(int UI, int column, int line) throws Exception
	{
		int I = -1;
		init();
		final int prevTableGraphNode = getNextTerminal(UI);
		final TableNode prevNode = this.analyzerTable.getTermial(this.analyzerTable.getGraphNode(prevTableGraphNode).getNodeReference());
		final int currentTableGraphNode = getNextTerminal(this.analyzerTable.getGraphNode(prevTableGraphNode).getSucessorIndex());

		if (currentTableGraphNode > 0)
		{
			final TableNode currentNode = this.analyzerTable.getTermial(this.analyzerTable.getGraphNode(currentTableGraphNode).getNodeReference());
			if (currentNode.getName().equals(this.analyzerToken.getCurrentSymbol()))
			{
				I = currentTableGraphNode;
				this.analyzer.setError(new ErrorRecoveryException("Symbol \"" + prevNode.getName() + "\" inserted before column " + column + "."));
				this.analyzerStack.getParseStack().push(new ParseNode(prevNode.getFlag(), prevNode.getName(), prevNode.getName()));
				this.analyzerStack.setTop(this.analyzerStack.getTop() + 1);
				this.semanticRoutines.setCurrentToken(this.analyzerToken.getLastToken());
				this.semanticRoutines.execFunction(this.analyzerTable.getGraphNode(prevTableGraphNode).getSemanticRoutine());
			}
		}
		else if (currentTableGraphNode == 0)
		{
			I = currentTableGraphNode;
			this.analyzer.setError(new ErrorRecoveryException("Symbol \"" + prevNode.getName() + "\" inserted before column " + column + "."));
			this.analyzerStack.getParseStack().push(new ParseNode(prevNode.getFlag(), prevNode.getName(), prevNode.getName()));
			this.analyzerStack.setTop(this.analyzerStack.getTop() + 1);
			this.semanticRoutines.setCurrentToken(this.analyzerToken.getLastToken());
			this.semanticRoutines.execFunction(this.analyzerTable.getGraphNode(prevTableGraphNode).getSemanticRoutine());
		}

		if (I < 0)
		{
			restore(false);
		}
		return I;
	}
}
