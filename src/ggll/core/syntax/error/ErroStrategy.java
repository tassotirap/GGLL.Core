package ggll.core.syntax.error;

import ggll.core.exceptions.LexicalException;
import ggll.core.semantics.SemanticRoutine;
import ggll.core.syntax.parser.GGLLTable;
import ggll.core.syntax.parser.Parser;
import ggll.core.syntax.parser.ParserAlternative;
import ggll.core.syntax.parser.ParserStack;
import ggll.core.syntax.parser.ParserToken;

public abstract class ErroStrategy
{
	protected int MAX_ITERATOR = 100;
	
	protected Parser parser;
	protected ParserStack parserStack;
	protected ParserToken parserToken;

	protected ParserStack oldParserStack;
	protected ParserToken oldParserToken;	
	
	protected ParserAlternative analyzerAlternative;	
	protected SemanticRoutine semanticRoutines;
	
	
	protected GGLLTable analyzerTable;

	public ErroStrategy(Parser analyzer)
	{
		this.parser = analyzer;
	}

	protected abstract int tryFix(int UI, int column, int line) throws LexicalException;

	protected void init()
	{
		this.parserStack = this.parser.getParserStacks();
		this.parserToken = this.parser.getParseToken();
		
		this.oldParserStack = (ParserStack)this.parserStack.clone();
		this.oldParserToken = (ParserToken)this.parserToken.clone();
	
		
		
		this.semanticRoutines = this.parser.getSemanticRoutines();
		this.analyzerAlternative = this.parser.getParseAlternative();
		this.analyzerTable = this.parser.getParseTable();	
	}

	public int execute(int UI, int column, int line) throws LexicalException
	{
		init();
		int I = tryFix(UI, column, line);
		if (I < 0)
		{
			restore();
		}
		return I;
	}

	protected void restore()
	{
		this.parser.setParserStacks(oldParserStack);
		this.parser.setParseToken(oldParserToken);
	}
}
