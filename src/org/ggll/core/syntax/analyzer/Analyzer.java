package org.ggll.core.syntax.analyzer;

import java.io.File;

import org.ggll.core.CoreManager;
import org.ggll.core.lexical.Yylex;
import org.ggll.core.semantics.SemanticRoutinesRepo;
import org.ggll.core.syntax.error.AnalyzerErrorFacede;
import org.ggll.core.syntax.model.GrViewNode;
import org.ggll.core.syntax.model.ParseNode;
import org.ggll.core.syntax.model.TableGraphNode;
import org.ggll.core.syntax.model.TableNode;

public class Analyzer
{
	boolean continueSentinel;

	private GrViewNode grViewStackNode;

	ParseNode auxParseSNode = null;

	private SemanticRoutinesRepo semanticRoutinesRepo;

	private AnalyzerAlternative analyzerAlternative;
	private AnalyzerErrorFacede analyzerError;
	private AnalyzerToken analyzerToken;
	private AnalyzerStackRepository analyzerStacks;
	private AnalyzerTableRepository analyzerTabs;
	private int I;
	private int UI;
	private boolean debug;

	public Analyzer(TableGraphNode tabGraphNodes[], TableNode termialTab[], TableNode nTerminalTab[], File fileIn, Yylex yylex, boolean debug)
	{
		analyzerToken = AnalyzerToken.setInstance(yylex);
		analyzerTabs = AnalyzerTableRepository.setInstance(tabGraphNodes, nTerminalTab, termialTab);
		analyzerStacks = AnalyzerStackRepository.setInstance();
		analyzerAlternative = AnalyzerAlternative.setInstance();
		analyzerError = new AnalyzerErrorFacede(fileIn);
		this.debug = debug;
	}

	public void run()
	{
		CoreManager.clearErrors();
		
		analyzerToken.setCurrentSemanticSymbol(null);
		getAnalyzerStacks().init();

		CoreManager.setSucess(true);
		semanticRoutinesRepo = SemanticRoutinesRepo.setInstance(getAnalyzerStacks().getParseStack());

		analyzerToken.getYylex().TabT(analyzerTabs.getTermialTable());
		analyzerTabs.setGraphNode(0, new TableGraphNode());
		analyzerTabs.getGraphNode(0).setIsTerminal(false);
		analyzerTabs.getGraphNode(0).setNodeReference(1);
		analyzerTabs.getGraphNode(0).setAlternativeIndex(0);
		analyzerTabs.getGraphNode(0).setSucessorIndex(0);

		getAnalyzerStacks().getGrViewStack().push(new GrViewNode(0, 1));
		getAnalyzerStacks().setTop(0);

		analyzerToken.readNext();

		UI = I = analyzerTabs.getNTerminal(1).getFirstNode();		

		continueSentinel = true;
		
		if(!debug)
		{
			next();
		}
	}
	
	public boolean next()
	{
		while (continueSentinel)
		{
			if (I != 0)
			{
				TableGraphNode currentGraphNode = analyzerTabs.getGraphNode(I);
				if (currentGraphNode.IsTerminal())
				{
					TableNode currentTerminal = analyzerTabs.getTermial(currentGraphNode.getNodeReference());
					if (currentGraphNode.isLambda())
					{
						semanticRoutinesRepo.setCurrentToken(null);
						semanticRoutinesRepo.execFunction(currentGraphNode.getSemanticRoutine());

						I = currentGraphNode.getSucessorIndex();
						UI = I;
					}
					else
					{
						if ((currentTerminal.getName()).equals(analyzerToken.getCurrentSymbol()))
						{
							getAnalyzerStacks().getParseStack().push(new ParseNode(currentTerminal.getFlag(), analyzerToken.getCurrentSymbol(), analyzerToken.getCurrentSemanticSymbol()));

							semanticRoutinesRepo.setCurrentToken(analyzerToken.getCurrentToken());
							semanticRoutinesRepo.execFunction(currentGraphNode.getSemanticRoutine());

							analyzerToken.readNext();

							I = currentGraphNode.getSucessorIndex();
							UI = I;
							getAnalyzerStacks().setTop(getAnalyzerStacks().getTop() + 1);

							getAnalyzerStacks().getNTerminalStack().clear();

							if (debug)
							{
								return true;
							}
						}
						else
						{
							if (currentGraphNode.getAlternativeIndex() != 0)
							{
								I = currentGraphNode.getAlternativeIndex();
							}
							else
							{
								if (getAnalyzerStacks().getNTerminalStack().empty())
								{
									I = analyzerError.dealWithError(UI, analyzerToken.getCurrentToken().charBegin + 1, analyzerToken.getCurrentToken().line + 1);
									continueSentinel = I >= 0;
									CoreManager.setSucess(false);
								}
								else
								{
									int alternative = analyzerAlternative.findAlternative(I, getAnalyzerStacks().getNTerminalStack(), getAnalyzerStacks().getGrViewStack());
									if (alternative != 0)
									{
										I = alternative;
									}
									else
									{
										I = analyzerError.dealWithError(UI, analyzerToken.getCurrentToken().charBegin + 1, analyzerToken.getCurrentToken().line + 1);
										continueSentinel = I >= 0;
										CoreManager.setSucess(false);
									}
								}
							}
						}
					}
				}
				else
				{
					TableNode currentNTerminal = analyzerTabs.getNTerminal(analyzerTabs.getGraphNode(I).getNodeReference());
					getAnalyzerStacks().getNTerminalStack().push(I);
					getAnalyzerStacks().getGrViewStack().push(new GrViewNode(I, getAnalyzerStacks().getTop() + 1));
					I = currentNTerminal.getFirstNode();
				}
			}
			else
			{
				if (!getAnalyzerStacks().getGrViewStack().empty())
				{
					grViewStackNode = getAnalyzerStacks().getGrViewStack().pop();
					auxParseSNode = null;

					while (getAnalyzerStacks().getParseStack().size() >= grViewStackNode.size)
					{
						auxParseSNode = getAnalyzerStacks().getParseStack().pop();
					}

					if (auxParseSNode != null)
					{
						TableNode currentNTerminal = analyzerTabs.getNTerminal(analyzerTabs.getGraphNode(grViewStackNode.indexNode).getNodeReference());
						getAnalyzerStacks().getParseStack().push(new ParseNode(currentNTerminal.getFlag(), currentNTerminal.getName(), auxParseSNode.getSemanticSymbol()));

						if (debug)
						{
							return true;
						}
					}

					I = grViewStackNode.indexNode;

					semanticRoutinesRepo.setCurrentToken(analyzerToken.getCurrentToken());
					semanticRoutinesRepo.execFunction(analyzerTabs.getGraphNode(I).getSemanticRoutine());

					I = analyzerTabs.getGraphNode(I).getSucessorIndex();
					UI = I;
				}
				else
				{
					if (!analyzerToken.getCurrentSymbol().equals(new String("$")))
					{
						I = analyzerError.dealWithError(UI, analyzerToken.getCurrentToken().charBegin + 1, analyzerToken.getCurrentToken().line + 1);
						CoreManager.setSucess(false);
					}
					continueSentinel = I > 0;
				}
			}
		}
		return false;
	}

	public AnalyzerStackRepository getAnalyzerStacks()
	{
		return analyzerStacks;
	}
}
