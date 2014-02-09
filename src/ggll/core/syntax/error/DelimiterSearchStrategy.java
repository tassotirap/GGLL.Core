package ggll.core.syntax.error;

import ggll.core.exceptions.ErrorRecoveryException;
import ggll.core.syntax.model.ParseNode;
import ggll.core.syntax.model.ParseStack;
import ggll.core.syntax.model.TableNode;
import ggll.core.syntax.parser.Parser;

public class DelimiterSearchStrategy extends ErroStrategy
{
	public DelimiterSearchStrategy(Parser analyzer)
	{
		super(analyzer);
	}

	@Override
	protected int tryFix(int Index, int column, int line)
	{
		int I = -1;

		final ParseStack parseStackClone = this.parseStack.clone();

		while (Index > 0 && ggLLTable.getGraphNode(Index).IsTerminal())
		{
			Index = ggLLTable.getGraphNode(Index).getAlternativeIndex();
		}

		if (Index > 0)
		{
			TableNode currentNTerminal = ggLLTable.getNTerminal(ggLLTable.getGraphNode(Index).getNodeReference());
			parseStackClone.push(new ParseNode(currentNTerminal.getFlag(), currentNTerminal.getName(), ""));
			Index = ggLLTable.getGraphNode(Index).getSucessorIndex();

			while (Index > 0 && I < 0)
			{
				if (ggLLTable.getGraphNode(Index).IsTerminal())
				{
					TableNode terminalNode = ggLLTable.getTermial(ggLLTable.getGraphNode(Index).getNodeReference());
					if (terminalNode.getName().equals(parseToken.getCurrentSymbol()))
					{
						this.parser.setError(new ErrorRecoveryException("Symbol \"" + terminalNode.getName() + "\" at before column " + column + " assumed as delimiter."));
						parser.getParserStacks().setParseStack(parseStackClone);
						I = Index;
					}
					else
					{
						if (ggLLTable.getGraphNode(Index).getSucessorIndex() != 0)
						{
							Index = ggLLTable.getGraphNode(Index).getSucessorIndex();

						}
						else
						{
							Index = ggLLTable.getGraphNode(Index).getAlternativeIndex();
						}
					}
				}
				else
				{
					currentNTerminal = ggLLTable.getNTerminal(ggLLTable.getGraphNode(Index).getNodeReference());
					parseStackClone.push(new ParseNode(currentNTerminal.getFlag(), currentNTerminal.getName(), currentNTerminal.getName()));
					Index = ggLLTable.getGraphNode(Index).getSucessorIndex();
				}
			}
		}

		return I;
	}
}
