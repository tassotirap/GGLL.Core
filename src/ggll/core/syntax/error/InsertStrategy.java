package ggll.core.syntax.error;

import ggll.core.syntax.model.GGLLNode;
import ggll.core.syntax.model.NTerminalStack;
import ggll.core.syntax.model.ParseNode;
import ggll.core.syntax.model.TableNode;
import ggll.core.syntax.parser.Parser;

public class InsertStrategy extends IErroStrategy
{
	public InsertStrategy(Parser analyzer)
	{
		super(analyzer);
	}
	
	public int tryFix(int UI, int column, int line) throws Exception
	{
		int IX, IY;
		int I = -1;
		
		IX = UI;

		init();		

		int iteration = 0;
		while (IX != 0 && I < 0)
		{
			if(iteration > MAX_ITERATOR)
			{
				break;
			}
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
								analyzer.setError("Symbol \"" + terminalNode.getName() + "\" inserted before column " + column + ".");
								analyzerStack.getParseStack().push(new ParseNode(terminalNode.getFlag(), terminalNode.getName(), terminalNode.getName()));
								analyzerStack.setTop(analyzerStack.getTop() + 1);
								semanticRoutines.setCurrentToken(analyzerToken.getLastToken());
								semanticRoutines.execFunction(analyzerTable.getGraphNode(IX).getSemanticRoutine());
								analyzerStack.getNTerminalStack().clear();
								I = IY;
							}
							else
								IY = analyzerAlternative.findAlternative(IY, pilhaNaoTerminalY, analyzerStack.getGrViewStack());
						}
					}
					else
					{
						analyzerStack.getGrViewStack().push(new GGLLNode(IY, analyzerStack.getTop() + 2));
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
				analyzerStack.getGrViewStack().push(new GGLLNode(IX, analyzerStack.getTop() + 1));
				analyzerStack.getNTerminalStack().push(IX);
				IX = analyzerTable.getNTerminal(analyzerTable.getGraphNode(IX).getNodeReference()).getFirstNode();
			}
			iteration++;
		}
		
		if (I < 0)
		{
			restore(false);
		}
		return I;
	}
}
