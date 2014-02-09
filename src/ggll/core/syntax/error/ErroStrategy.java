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
		this.parseStack = parser.getParserStacks().getParseStack();
		this.nTerminalStack = parser.getParserStacks().getNTerminalStack();
		this.parseToken = parser.getParseToken();
	}

	protected abstract int tryFix(int UI, int column, int line) throws LexicalException;

	public int execute(int index, int column, int line) throws LexicalException
	{
		return tryFix(index, column, line);
	}
}
