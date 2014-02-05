package ggll.core.syntax.error;

import ggll.core.exceptions.LexicalException;
import ggll.core.syntax.model.GGLLStack;
import ggll.core.syntax.model.NTerminalStack;
import ggll.core.syntax.model.ParseStack;
import ggll.core.syntax.parser.GGLLTable;
import ggll.core.syntax.parser.Parser;
import ggll.core.syntax.parser.ParserToken;

public abstract class ErroStrategy
{
	protected int MAX_ITERATOR = 100;

	protected Parser parser;
	protected GGLLTable ggLLTable;
	protected GGLLStack ggLLStack;
	protected ParseStack parseStack;
	protected NTerminalStack nTerminalStack;
	protected ParserToken parseToken;	

	public ErroStrategy(Parser parser)
	{
		this.parser = parser;
		this.ggLLTable = parser.getGGLLTable();
		this.ggLLStack = parser.getParserStacks().getGGLLStack();
		this.parseStack  = parser.getParserStacks().getParseStack();
		this.nTerminalStack = parser.getParserStacks().getNTerminalStack();	
		this.parseToken = parser.getParseToken();
	}

	protected abstract int tryFix(int UI, int column, int line) throws LexicalException;

	public int execute(int index, int column, int line) throws LexicalException
	{
		return tryFix(index, column, line);
	}

	protected boolean validate(int index)
	{
		return false;
//		while (true)
//		{
//			if (index != 0)
//			{
//				final TableGraphNode currentGraphNode = analyzerTable.getGraphNode(index);
//				if (currentGraphNode.IsTerminal())
//				{
//					final TableNode currentTerminal = analyzerTable.getTermial(currentGraphNode.getNodeReference());
//					if (currentGraphNode.isLambda())
//					{
//						index = currentGraphNode.getSucessorIndex();
//					}
//					else
//					{
//						if (currentTerminal.getName().equals(parserToken.getCurrentSymbol()))
//						{
//							return true;
//						}
//						else
//						{
//							if (currentGraphNode.getAlternativeIndex() != 0)
//							{
//								index = currentGraphNode.getAlternativeIndex();
//							}
//							else
//							{
//								if (tempParserStack.getNTerminalStack().empty())
//								{
//									return false;
//								}
//								else
//								{
//									final int alternative = analyzerAlternative.findAlternative(index, tempParserStack.getNTerminalStack(), tempParserStack.getGGLLStack());
//									if (alternative != 0)
//									{
//										index = alternative;
//									}
//									else
//									{
//										return false;
//									}
//								}
//							}
//						}
//					}
//				}
//				else
//				{
//					final TableNode currentNTerminal = analyzerTable.getNTerminal(analyzerTable.getGraphNode(index).getNodeReference());
//					tempParserStack.getNTerminalStack().push(index);
//					tempParserStack.getGGLLStack().push(new GGLLNode(index, tempParserStack.getParseStack().size()));
//					index = currentNTerminal.getFirstNode();
//				}
//			}
//			else
//			{
//				if (!tempParserStack.getGGLLStack().empty())
//				{
//					final GGLLNode grViewStackNode = tempParserStack.getGGLLStack().pop();
//					ParseNode auxParseSNode = null;
//
//					while (tempParserStack.getParseStack().size() > grViewStackNode.size)
//					{
//						auxParseSNode = tempParserStack.getParseStack().pop();
//					}
//
//					if (auxParseSNode != null)
//					{
//						final TableNode currentNTerminal = analyzerTable.getNTerminal(analyzerTable.getGraphNode(grViewStackNode.index).getNodeReference());
//						tempParserStack.getParseStack().push(new ParseNode(currentNTerminal.getFlag(), currentNTerminal.getName(), auxParseSNode.getSemanticSymbol()));
//						this.parser.Output();
//					}
//
//					index = grViewStackNode.index;
//					index = analyzerTable.getGraphNode(index).getSucessorIndex();
//				}
//				else if (this.parserToken.getCurrentSymbol().equals(new String("$")))
//				{
//					return true;
//				}
//				else
//				{
//					return false;
//				}
//			}
//		}
	}
}
