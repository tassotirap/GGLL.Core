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

	@Override
	public int tryFix(int UI, int column, int line) throws Exception
	{
		int I = -1;
		init();
		int prevTableGraphNode = getNextTerminal(UI);
		TableNode prevNode = analyzerTable.getTermial(analyzerTable.getGraphNode(prevTableGraphNode).getNodeReference());
		int currentTableGraphNode = getNextTerminal(analyzerTable.getGraphNode(prevTableGraphNode).getSucessorIndex());

		if (currentTableGraphNode > 0)
		{
			TableNode currentNode = analyzerTable.getTermial(analyzerTable.getGraphNode(currentTableGraphNode).getNodeReference());
			if (currentNode.getName().equals(analyzerToken.getCurrentSymbol()))
			{
				I = currentTableGraphNode;
				analyzer.setError(new ErrorRecoveryException("Symbol \"" + prevNode.getName() + "\" inserted before column " + column + "."));
				analyzerStack.getParseStack().push(new ParseNode(prevNode.getFlag(), prevNode.getName(), prevNode.getName()));
				analyzerStack.setTop(analyzerStack.getTop() + 1);
				semanticRoutines.setCurrentToken(analyzerToken.getLastToken());
				semanticRoutines.execFunction(analyzerTable.getGraphNode(prevTableGraphNode).getSemanticRoutine());
			}
		}
		else if (currentTableGraphNode == 0)
		{
			I = currentTableGraphNode;
			analyzer.setError(new ErrorRecoveryException("Symbol \"" + prevNode.getName() + "\" inserted before column " + column + "."));
			analyzerStack.getParseStack().push(new ParseNode(prevNode.getFlag(), prevNode.getName(), prevNode.getName()));
			analyzerStack.setTop(analyzerStack.getTop() + 1);
			semanticRoutines.setCurrentToken(analyzerToken.getLastToken());
			semanticRoutines.execFunction(analyzerTable.getGraphNode(prevTableGraphNode).getSemanticRoutine());
		}

		if (I < 0)
		{
			restore(false);
		}
		return I;
	}

	private int getNextTerminal(int Index)
	{
		while (Index > 0 && !analyzerTable.getGraphNode(Index).IsTerminal())
		{
			analyzerStack.getGGLLStack().push(new GGLLNode(Index, analyzerStack.getTop() + 1));
			analyzerStack.getNTerminalStack().push(Index);
			Index = analyzerTable.getNTerminal(analyzerTable.getGraphNode(Index).getNodeReference()).getFirstNode();
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
}
