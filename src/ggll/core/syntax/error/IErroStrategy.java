package ggll.core.syntax.error;

import ggll.core.semantics.SemanticRoutine;
import ggll.core.syntax.model.GGLLStack;
import ggll.core.syntax.model.NTerminalStack;
import ggll.core.syntax.parser.GGLLTable;
import ggll.core.syntax.parser.Parser;
import ggll.core.syntax.parser.ParserAlternative;
import ggll.core.syntax.parser.ParserStack;
import ggll.core.syntax.parser.ParserToken;

public abstract class IErroStrategy
{
	protected Parser analyzer;
	protected ParserStack analyzerStack;

	protected ParserToken oldToken;

	protected GGLLStack oldGGLLStack;
	protected NTerminalStack oldNTerminalStack;
	protected int oldTop;
	protected int MAX_ITERATOR = 100;
	protected ParserAlternative analyzerAlternative;

	protected ParserToken analyzerToken;
	protected SemanticRoutine semanticRoutines;
	protected GGLLTable analyzerTable;

	public IErroStrategy(Parser analyzer)
	{
		this.analyzer = analyzer;
	}

	abstract int tryFix(int UI, int column, int line) throws Exception;

	protected void init() throws Exception
	{
		this.analyzerStack = this.analyzer.getParserStacks();
		this.analyzerAlternative = this.analyzer.getParseAlternative();
		this.analyzerToken = this.analyzer.getParseToken();
		this.semanticRoutines = this.analyzer.getSemanticRoutines();
		this.analyzerTable = this.analyzer.getParseTable();

		this.oldGGLLStack = this.analyzerStack.getGGLLStack().clone();
		this.oldNTerminalStack = this.analyzerStack.getNTerminalStack().clone();
		this.oldTop = this.analyzerStack.getTop();
	}

	protected void restore(boolean restoreToken)
	{
		this.analyzerStack.setGGLLStack(this.oldGGLLStack);
		this.analyzerStack.setNTerminalStack(this.oldNTerminalStack);
		this.analyzerStack.setTop(this.oldTop);
		if (restoreToken)
		{
			this.analyzerToken.setCurrentToken(this.analyzerToken.getLastToken());
			this.analyzerToken.setCurrentSymbol(this.analyzerToken.getLastSymbol());
			this.analyzerToken.setCurrentSemanticSymbol(this.analyzerToken.getLastSemanticSymbol());
			this.analyzerToken.getYylex().pushback(this.analyzerToken.getYylex().yylength());
		}
	}
}
