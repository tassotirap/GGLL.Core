package org.ggll.core.syntax.error;

import org.ggll.core.CoreManager;
import org.ggll.core.syntax.analyzer.AnalyzerTable;
import org.ggll.core.syntax.model.GrViewNode;
import org.ggll.core.syntax.model.NTerminalStack;
import org.ggll.core.syntax.model.ParseNode;
import org.ggll.core.syntax.model.TableNode;

public class InsertStrategy extends IErroStrategy
{
	public InsertStrategy(AnalyzerTable analyzerTable)
	{
		super(analyzerTable);
	}
	
	public int tryFix(int UI, int column, int line)
	{
		int IX, IY;
		int I = -1;
		
		IX = UI;

		init();		

		while (IX != 0 && I < 0)
		{
			if (analyzerTable.getGraphNode(IX).IsTerminal())
			{
				NTerminalStack pilhaNaoTerminalY = new NTerminalStack();
				
				TableNode terminalNode = analyzerTable.getTermial(analyzerTable.getGraphNode(IX).getNodeReference());				
				IY = analyzerTable.getGraphNode(IX).getSucessorIndex();	
				
				while (IY != 0 && I < 0)
				{
					if (analyzerTable.getGraphNode(IY).IsTerminal())
					{
						if (analyzerTable.getGraphNode(IY).isLambda())
						{
							IY = analyzerTable.getGraphNode(IY).getSucessorIndex();
						}
						else
						{
							String temp = analyzerTable.getTermial(analyzerTable.getGraphNode(IY).getNodeReference()).getName();
							if (temp.equals(analyzerToken.getCurrentSymbol()))
							{
								CoreManager.setError("Symbol \"" + terminalNode.getName() + "\" inserted before column " + column + ".");
								analyzerStack.getParseStack().push(new ParseNode(terminalNode.getFlag(), terminalNode.getName(), terminalNode.getName()));
								analyzerStack.setTop(analyzerStack.getTop() + 1);
								semanticRoutinesRepo.setCurrentToken(analyzerToken.getLastToken());
								semanticRoutinesRepo.execFunction(analyzerTable.getGraphNode(IX).getSemanticRoutine());
								analyzerStack.getNTerminalStack().clear();
								I = IY;
							}
							else
								IY = analyzerAlternative.findAlternative(IY, pilhaNaoTerminalY, analyzerStack.getGrViewStack());
						}
					}
					else
					{
						analyzerStack.getGrViewStack().push(new GrViewNode(IY, analyzerStack.getTop() + 2));
						pilhaNaoTerminalY.push(IY);
						IY = analyzerTable.getNTerminal(analyzerTable.getGraphNode(IY).getNodeReference()).getFirstNode();
					}
				}
				if (I < 0)
				{
					IX = analyzerAlternative.findAlternative(IX, analyzerStack.getNTerminalStack(), analyzerStack.getGrViewStack());
				}
			}
			else
			{
				analyzerStack.getGrViewStack().push(new GrViewNode(IX, analyzerStack.getTop() + 1));
				analyzerStack.getNTerminalStack().push(IX);
				IX = analyzerTable.getNTerminal(analyzerTable.getGraphNode(IX).getNodeReference()).getFirstNode();
			}
		}
		
		if (I < 0)
		{
			restore(false);
		}
		return I;
	}
}
