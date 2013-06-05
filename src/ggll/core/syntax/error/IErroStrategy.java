package ggll.core.syntax.error;

import ggll.core.semantics.SemanticRoutine;
import ggll.core.syntax.model.GGLLStack;
import ggll.core.syntax.model.NTerminalStack;
import ggll.core.syntax.parser.Parser;
import ggll.core.syntax.parser.ParserAlternative;
import ggll.core.syntax.parser.ParserStack;
import ggll.core.syntax.parser.GGLLTable;
import ggll.core.syntax.parser.ParserToken;

public abstract class IErroStrategy
{
	protected Parser analyzer;
	protected ParserStack analyzerStack;

	protected ParserToken oldToken;

	protected GGLLStack oldGrViewStack;
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
		this.analyzerStack = analyzer.getParseStacks();
		this.analyzerAlternative = analyzer.getParseAlternative();
		this.analyzerToken = analyzer.getParseToken();
		this.semanticRoutines = analyzer.getSemanticRoutines();
		this.analyzerTable = analyzer.getParseTable();
		
		
		oldGrViewStack = analyzerStack.getGrViewStack().clone();
		oldNTerminalStack = analyzerStack.getNTerminalStack().clone();
		oldTop = analyzerStack.getTop();
	}

	protected void restore(boolean restoreToken)
	{
		analyzerStack.setGrViewStack(oldGrViewStack);
		analyzerStack.setNTerminalStack(oldNTerminalStack);
		analyzerStack.setTop(oldTop);
		if (restoreToken)
		{
			analyzerToken.setCurrentToken(analyzerToken.getLastToken());
			analyzerToken.setCurrentSymbol(analyzerToken.getLastSymbol());
			analyzerToken.setCurrentSemanticSymbol(analyzerToken.getLastSemanticSymbol());
			analyzerToken.getYylex().pushback(analyzerToken.getYylex().yylength());
		}
	}
}
