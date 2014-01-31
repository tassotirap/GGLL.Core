package ggll.core.syntax.error;

import ggll.core.exceptions.LexicalException;
import ggll.core.syntax.model.GGLLNode;
import ggll.core.syntax.model.ParseNode;
import ggll.core.syntax.model.TableGraphNode;
import ggll.core.syntax.model.TableNode;
import ggll.core.syntax.parser.GGLLTable;
import ggll.core.syntax.parser.Parser;
import ggll.core.syntax.parser.ParserAlternative;
import ggll.core.syntax.parser.ParserStack;
import ggll.core.syntax.parser.ParserToken;

import com.rits.cloning.Cloner;

public abstract class ErroStrategy
{
	protected int MAX_ITERATOR = 100;

	protected Parser parser;
	protected ParserStack parserStack;
	protected ParserToken parserToken;

	private ParserStack oldParserStack;
	private ParserToken oldParserToken;

	protected ParserStack tempParserStack;

	protected ParserAlternative analyzerAlternative;

	protected GGLLTable analyzerTable;

	public ErroStrategy(Parser parser)
	{
		this.parser = parser;
	}

	protected abstract int tryFix(int UI, int column, int line) throws LexicalException;

	protected void init()
	{
		this.parserStack = this.parser.getParserStacks();
		this.parserToken = this.parser.getParseToken();
		this.analyzerAlternative = this.parser.getParseAlternative();
		this.analyzerTable = this.parser.getParseTable();

		Cloner cloner = new Cloner();
		
		//Clone stack and tokens
		this.oldParserStack = cloner.deepClone(this.parserStack);
		this.oldParserToken = cloner.deepClone(this.parserToken);
		this.tempParserStack = cloner.deepClone(this.parserStack);
	}

	public int execute(int index, int column, int line) throws LexicalException
	{
		init();
		int newIndex = tryFix(index, column, line);
		if (newIndex < 0)
		{
			restore();
		}
		return newIndex;
	}

	protected void restore()
	{
		this.parser.setParserStacks(oldParserStack);
		this.parser.setParseToken(oldParserToken);
	}

	protected boolean validate(int index)
	{
		while (true)
		{
			if (index != 0)
			{
				final TableGraphNode currentGraphNode = analyzerTable.getGraphNode(index);
				if (currentGraphNode.IsTerminal())
				{
					final TableNode currentTerminal = analyzerTable.getTermial(currentGraphNode.getNodeReference());
					if (currentGraphNode.isLambda())
					{
						index = currentGraphNode.getSucessorIndex();
					}
					else
					{
						if (currentTerminal.getName().equals(parserToken.getCurrentSymbol()))
						{
							return true;
						}
						else
						{
							if (currentGraphNode.getAlternativeIndex() != 0)
							{
								index = currentGraphNode.getAlternativeIndex();
							}
							else
							{
								if (tempParserStack.getNTerminalStack().empty())
								{
									return false;
								}
								else
								{
									final int alternative = analyzerAlternative.findAlternative(index, tempParserStack.getNTerminalStack(), tempParserStack.getGGLLStack());
									if (alternative != 0)
									{
										index = alternative;
									}
									else
									{
										return false;
									}
								}
							}
						}
					}
				}
				else
				{
					final TableNode currentNTerminal = analyzerTable.getNTerminal(analyzerTable.getGraphNode(index).getNodeReference());
					tempParserStack.getNTerminalStack().push(index);
					tempParserStack.getGGLLStack().push(new GGLLNode(index, tempParserStack.getParseStack().size()));
					index = currentNTerminal.getFirstNode();
				}
			}
			else
			{
				if (!tempParserStack.getGGLLStack().empty())
				{
					final GGLLNode grViewStackNode = tempParserStack.getGGLLStack().pop();
					ParseNode auxParseSNode = null;

					while (tempParserStack.getParseStack().size() > grViewStackNode.size)
					{
						auxParseSNode = tempParserStack.getParseStack().pop();
					}

					if (auxParseSNode != null)
					{
						final TableNode currentNTerminal = analyzerTable.getNTerminal(analyzerTable.getGraphNode(grViewStackNode.index).getNodeReference());
						tempParserStack.getParseStack().push(new ParseNode(currentNTerminal.getFlag(), currentNTerminal.getName(), auxParseSNode.getSemanticSymbol()));
						this.parser.Output();
					}

					index = grViewStackNode.index;
					index = analyzerTable.getGraphNode(index).getSucessorIndex();
				}
				else if (this.parserToken.getCurrentSymbol().equals(new String("$")))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
	}
}
