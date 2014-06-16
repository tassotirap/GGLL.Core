package ggll.core.syntax.error;

import ggll.core.exceptions.ErrorRecoveryException;
import ggll.core.exceptions.LexicalException;
import ggll.core.syntax.model.GGLLNode;
import ggll.core.syntax.model.GGLLStack;
import ggll.core.syntax.model.NTerminalStack;
import ggll.core.syntax.model.ParseNode;
import ggll.core.syntax.model.ParseStack;
import ggll.core.syntax.model.TableNode;
import ggll.core.syntax.parser.Parser;
import ggll.core.syntax.parser.ParserToken;

public class DeleteStrategy extends ErroStrategy
{
	public DeleteStrategy(Parser analyzer)
	{
		super(analyzer);
	}

	@Override
	protected int tryFix(int Index, int column, int line) throws LexicalException
	{
		int I = -1;
		final NTerminalStack nTerminalStackClone = nTerminalStack.clone();
		final GGLLStack ggLLStackClone = ggLLStack.clone();
		final ParseStack parseStackClone = parseStack.clone();

		final ParserToken parserTokenClone = (ParserToken)parseToken.clone();
		parseToken.readNext();

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
							parser.setError(new ErrorRecoveryException("Symbol \"" + this.parseToken.getLastToken().text + "\" was ignored."));
							parser.getParserStacks().setParseStack(parseStackClone);
							parser.getParserStacks().setGGLLStack(ggLLStackClone);
							I = Index;
						}
						else
						{
							Index = parser.getParseAlternative().find(Index, nTerminalStackClone, ggLLStackClone);
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

		}
		while (Index != 0 && I < 0);

		if(I < 0)
		{
			parser.setParseToken(parserTokenClone);
		}

		return I;
	}

}
