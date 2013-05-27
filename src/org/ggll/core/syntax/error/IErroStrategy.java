package org.ggll.core.syntax.error;

import org.ggll.core.semantics.SemanticRoutinesHelper;
import org.ggll.core.syntax.analyzer.AnalyzerAlternative;
import org.ggll.core.syntax.analyzer.AnalyzerStackRepository;
import org.ggll.core.syntax.analyzer.AnalyzerTableRepository;
import org.ggll.core.syntax.analyzer.AnalyzerToken;
import org.ggll.core.syntax.model.GrViewStack;
import org.ggll.core.syntax.model.NTerminalStack;

public abstract class IErroStrategy
{
	protected AnalyzerTableRepository analyzerTable;
	protected AnalyzerStackRepository analyzerStack;

	protected AnalyzerToken oldToken;
	protected GrViewStack oldGrViewStack;
	protected NTerminalStack oldNTerminalStack;
	protected int oldTop;

	protected AnalyzerAlternative analyzerAlternative;
	protected AnalyzerToken analyzerToken;
	protected SemanticRoutinesHelper semanticRoutinesRepo;

	abstract int tryFix(int UI, int column, int line);

	protected void init()
	{
		this.analyzerTable = AnalyzerTableRepository.getInstance();
		this.analyzerStack = AnalyzerStackRepository.getInstance();
		this.analyzerAlternative = AnalyzerAlternative.getInstance();
		this.analyzerToken = AnalyzerToken.getInstance();
		this.semanticRoutinesRepo = SemanticRoutinesHelper.getInstance();
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
