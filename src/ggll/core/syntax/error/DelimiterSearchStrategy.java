package ggll.core.syntax.error;

import ggll.core.exceptions.ErrorRecoveryException;
import ggll.core.exceptions.LexicalException;
import ggll.core.syntax.model.GGLLNode;
import ggll.core.syntax.model.GGLLStack;
import ggll.core.syntax.model.ParseNode;
import ggll.core.syntax.model.ParseStack;
import ggll.core.syntax.model.TableNode;
import ggll.core.syntax.parser.Parser;
import ggll.core.syntax.parser.ParserToken;

public class DelimiterSearchStrategy extends ErroStrategy
{
	public DelimiterSearchStrategy(Parser analyzer)
	{
		super(analyzer);
	}
	
	@Override
	protected int tryFix(int Index, int column, int line) throws LexicalException
	{	
		int currentIndex = Index;		
		ParseStack parseStackClone = this.parseStack.clone();
		GGLLStack ggLLStackClone = this.ggLLStack.clone();
		final ParserToken parserTokenClone = (ParserToken) parseToken.clone();
		
		while (!parserTokenClone.getCurrentToken().text.equals("$"))
		{
			while (Index != 0 && !ggLLStackClone.empty())
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
				if (ggLLTable.getGraphNode(Index).IsTerminal())
				{
					TableNode terminalNode = ggLLTable.getTermial(ggLLTable.getGraphNode(Index).getNodeReference());
					if (terminalNode.getName().equals(parserTokenClone.getCurrentSymbol()))
					{
						this.parser.setError(new ErrorRecoveryException("Symbol \"" + terminalNode.getName() + "\" at before column " + column + " assumed as delimiter."));
						parser.getParserStacks().setParseStack(parseStackClone);
						
						this.parser.getParserStacks().setGGLLStack(ggLLStackClone);
						this.parser.getParserStacks().setParseStack(parseStackClone);
						this.parser.setParseToken(parserTokenClone);						
						return Index;
					}
					else
					{
						Index = ggLLTable.getGraphNode(Index).getAlternativeIndex();
					}
				}
				else
				{
					TableNode currentNTerminal = ggLLTable.getNTerminal(ggLLTable.getGraphNode(Index).getNodeReference());
					parseStackClone.push(new ParseNode(currentNTerminal.getFlag(), currentNTerminal.getName(), currentNTerminal.getName()));
					Index = ggLLTable.getGraphNode(Index).getSucessorIndex();
				}
			}
			
			parserTokenClone.readNext();
			Index = currentIndex;			
			parseStackClone = this.parseStack.clone();
			ggLLStackClone = this.ggLLStack.clone();			
		}
		
		return -1;
	}
}
