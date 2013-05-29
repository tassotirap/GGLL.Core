package org.ggll.core.syntax.error;

import org.ggll.core.semantics.SemanticRoutines;
import org.ggll.core.syntax.analyzer.AnalyzerAlternative;
import org.ggll.core.syntax.analyzer.AnalyzerStackRepository;
import org.ggll.core.syntax.analyzer.AnalyzerTable;
import org.ggll.core.syntax.analyzer.AnalyzerToken;
import org.ggll.core.syntax.model.GrViewStack;
import org.ggll.core.syntax.model.NTerminalStack;

public abstract class IErroStrategy
{
	protected AnalyzerTable analyzerTable;
	protected AnalyzerStackRepository analyzerStack;

	public IErroStrategy(AnalyzerTable analyzerTable)
	{
		this.analyzerTable = analyzerTable;
	}

	protected AnalyzerToken oldToken;
	protected GrViewStack oldGrViewStack;
	protected NTerminalStack oldNTerminalStack;
	protected int oldTop;

	protected AnalyzerAlternative analyzerAlternative;
	protected AnalyzerToken analyzerToken;
	protected SemanticRoutines semanticRoutinesRepo;

	abstract int tryFix(int UI, int column, int line);

	protected void init()
	{
		this.analyzerStack = AnalyzerStackRepository.getInstance();
		this.analyzerAlternative = AnalyzerAlternative.getInstance();
		this.analyzerToken = AnalyzerToken.getInstance();
		this.semanticRoutinesRepo = SemanticRoutines.getInstance();
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
