package ggll.core.syntax.error;

import ggll.core.exceptions.ErrorRecoveryException;
import ggll.core.syntax.model.GGLLNode;
import ggll.core.syntax.model.GGLLStack;
import ggll.core.syntax.model.NTerminalStack;
import ggll.core.syntax.model.ParseNode;
import ggll.core.syntax.model.ParseStack;
import ggll.core.syntax.model.TableNode;
import ggll.core.syntax.parser.Parser;

public class InsertStrategy extends ErroStrategy
{
	public InsertStrategy(Parser analyzer)
	{
		super(analyzer);
	}

	@Override
	protected int tryFix(int Index, int column, int line)
	{
		int I = -1;
		final NTerminalStack nTerminalStackClone = this.nTerminalStack.clone();
		final GGLLStack ggLLStackClone = this.ggLLStack.clone();
		final ParseStack parseStackClone = this.parseStack.clone();

		do
		{
			if (Index > 0)
			{
				if (ggLLTable.getGraphNode(Index).IsTerminal())
				{
					final TableNode terminalNode = ggLLTable.getTermial(ggLLTable.getGraphNode(Index).getNodeReference());
					I = InnerLoop(ggLLTable.getGraphNode(Index).getSucessorIndex(), terminalNode, column, nTerminalStackClone, ggLLStackClone, parseStackClone);

					if (I < 0)
					{
						Index = parser.getParseAlternative().findAlternative(Index, nTerminalStackClone, ggLLStackClone);
					}

				}
				else
				{
					ggLLStackClone.push(new GGLLNode(Index, parseStackClone.size()));
					nTerminalStackClone.push(Index);
					Index = ggLLTable.getNTerminal(ggLLTable.getGraphNode(Index).getNodeReference()).getFirstNode();
				}
			}

			while (Index == 0 && !ggLLStackClone.empty())
			{
				final GGLLNode grViewStackNode = ggLLStackClone.pop();
				ParseNode auxParseSNode = null;

				while (parseStackClone.size() > grViewStackNode.size)
				{
					auxParseSNode = parseStackClone.pop();
				}

				if (auxParseSNode != null)
				{
					final TableNode currentNTerminal = ggLLTable.getNTerminal(ggLLTable.getGraphNode(grViewStackNode.index).getNodeReference());
					parseStackClone.push(new ParseNode(currentNTerminal.getFlag(), currentNTerminal.getName(), auxParseSNode.getSemanticSymbol()));
				}
				Index = ggLLTable.getGraphNode(grViewStackNode.index).getSucessorIndex();
			}

		}
		while (Index != 0 && I < 0);

		return I;
	}

	private int InnerLoop(int Index, TableNode terminalNode, int column, NTerminalStack nTerminalStack, GGLLStack ggLLStack, ParseStack parseStack)
	{
		final NTerminalStack nTerminalStackClone = nTerminalStack.clone();
		final GGLLStack ggLLStackClone = ggLLStack.clone();
		final ParseStack parseStackClone = parseStack.clone();

		do
		{
			if (Index > 0)
			{
				if (ggLLTable.getGraphNode(Index).IsTerminal())
				{
					if (ggLLTable.getGraphNode(Index).getNodeReference() == 0)
					{
						Index = ggLLTable.getGraphNode(Index).getSucessorIndex();
					}
					else
					{
						final String temp = ggLLTable.getTermial(ggLLTable.getGraphNode(Index).getNodeReference()).getName();
						if (temp.equals(parseToken.getCurrentSymbol()))
						{
							parser.setError(new ErrorRecoveryException("Symbol \"" + terminalNode.getName() + "\" inserted before column " + column + "."));
							parseStackClone.push(new ParseNode(terminalNode.getFlag(), terminalNode.getName(), terminalNode.getName()));
							parser.getParserStacks().setParseStack(parseStackClone);
							parser.getParserStacks().setGGLLStack(ggLLStackClone);
							parser.getParserStacks().getNTerminalStack().clear();
							return Index;
						}
						else
						{
							Index = parser.getParseAlternative().findAlternative(Index, nTerminalStackClone, ggLLStackClone);
						}
					}
				}
				else
				{
					ggLLStackClone.push(new GGLLNode(Index, ggLLStackClone.size()));
					nTerminalStackClone.push(Index);
					Index = ggLLTable.getNTerminal(ggLLTable.getGraphNode(Index).getNodeReference()).getFirstNode();
				}
			}

			while (Index == 0 && !ggLLStackClone.empty())
			{
				final GGLLNode grViewStackNode = ggLLStackClone.pop();
				ParseNode auxParseSNode = null;

				while (parseStackClone.size() > grViewStackNode.size)
				{
					auxParseSNode = parseStackClone.pop();
				}

				if (auxParseSNode != null)
				{
					final TableNode currentNTerminal = ggLLTable.getNTerminal(ggLLTable.getGraphNode(grViewStackNode.index).getNodeReference());
					parseStackClone.push(new ParseNode(currentNTerminal.getFlag(), currentNTerminal.getName(), auxParseSNode.getSemanticSymbol()));
				}
				Index = ggLLTable.getGraphNode(grViewStackNode.index).getSucessorIndex();
			}

			if (ggLLStackClone.empty() && Index == 0 && parseToken.getCurrentSymbol().equals("$"))
			{
				parser.setError(new ErrorRecoveryException("Symbol \"" + terminalNode.getName() + "\" inserted before column " + column + "."));
				parseStackClone.push(new ParseNode(terminalNode.getFlag(), terminalNode.getName(), terminalNode.getName()));
				parser.getParserStacks().setParseStack(parseStackClone);
				return 0;
			}

		}
		while (Index != 0);

		return -1;
	}
}
