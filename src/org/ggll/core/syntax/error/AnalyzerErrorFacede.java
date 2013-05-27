package org.ggll.core.syntax.error;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import org.ggll.core.CoreManager;
import org.ggll.core.syntax.analyzer.AnalyzerTableRepository;
import org.ggll.core.syntax.analyzer.AnalyzerToken;
import org.ggll.core.syntax.model.TableGraphNode;

public class AnalyzerErrorFacede
{

	private File fileIn;
	private AnalyzerTableRepository analyzerTable;

	private AnalyzerToken syntaxToken;

	public AnalyzerErrorFacede(File fileIn)
	{
		this.fileIn = fileIn;
		this.analyzerTable = AnalyzerTableRepository.getInstance();
		this.syntaxToken = AnalyzerToken.getInstance();
	}

	public int dealWithError(int UI, int column, int line)
	{
		int lastIndexNode = UI;

		try
		{
			if (fileIn != null)
			{

				BufferedReader bufferedReader = new BufferedReader(new FileReader(fileIn));
				for (int j = 0; j < line; j++)
				{
					bufferedReader.readLine();
				}
				String wrongLine = bufferedReader.readLine();
				bufferedReader.close();
				CoreManager.setError("\n" + wrongLine + "\n");
			}

		}
		catch (IOException e)
		{
			if (fileIn != null)
			{
				CoreManager.setError("File not found...");
			}
		}
		CoreManager.setError("Error found at the symbol " + syntaxToken.getCurrentToken().text + " of line: " + line + ", column: " + column + ".");
		int IX = UI;

		Stack<TableGraphNode> nTerminalStack = new Stack<TableGraphNode>();

		while (IX != 0)
		{
			if (analyzerTable.getGraphNode(IX).IsTerminal())
			{
				CoreManager.setError(analyzerTable.getTermial(analyzerTable.getGraphNode(IX).getNodeReference()).getName() + " expected.");
				IX = analyzerTable.getGraphNode(IX).getAlternativeIndex();

				if (IX == 0 && nTerminalStack.size() > 0)
				{
					IX = nTerminalStack.pop().getAlternativeIndex();
				}

			}
			else
			{
				nTerminalStack.push(analyzerTable.getGraphNode(IX));
				IX = analyzerTable.getNTerminal(analyzerTable.getGraphNode(IX).getNodeReference()).getFirstNode();
			}
		}

		ArrayList<IErroStrategy> strategyList = new ArrayList<IErroStrategy>();
		
		strategyList.add(new DeleteStrategy());
		strategyList.add(new InsertStrategy());		
		strategyList.add(new ChangeStrategy());
		strategyList.add(new DelimiterSearchStrategy());

		int I = UI;

		for (IErroStrategy errorStrategy : strategyList)
		{
			I = errorStrategy.tryFix(lastIndexNode,  column, line);
			if (I >= 0)
			{
				return I;
			}
		}

		if (I < 0)
		{
			syntaxToken.readNext();
			if (syntaxToken.getCurrentToken().text.equals("$"))
			{
				return I;
			}
			else
			{
				I = dealWithError(lastIndexNode, syntaxToken.getCurrentToken().charBegin + 1, syntaxToken.getCurrentToken().line + 1);
			}
		}
		return I;
	}
}