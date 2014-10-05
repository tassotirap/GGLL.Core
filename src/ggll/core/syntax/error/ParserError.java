package ggll.core.syntax.error;

import ggll.core.exceptions.ErrorRecoveryException;
import ggll.core.exceptions.SyntacticException;
import ggll.core.list.ExtendedList;
import ggll.core.syntax.model.TableGraphNode;
import ggll.core.syntax.parser.Parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class ParserError
{
	private final Parser analyzer;

	public ParserError(Parser analyzer)
	{
		this.analyzer = analyzer;
	}

	private void sintaticErrorMessage(int column, int line, int index)
	{
		final Stack<TableGraphNode> nTerminalStack = new Stack<TableGraphNode>();
		final ArrayList<String> expecteds = new ArrayList<String>();
		while (index != 0)
		{
			TableGraphNode tableGraphNode = this.analyzer.getGGLLTable().getGraphNode(index);
			if (tableGraphNode.IsTerminal())
			{
				expecteds.add(this.analyzer.getGGLLTable().getTermial(tableGraphNode.getNodeReference()).getName());
				index = tableGraphNode.getAlternativeIndex();
				if (index == 0 && nTerminalStack.size() > 0)
				{
					index = nTerminalStack.pop().getAlternativeIndex();
				}
			}
			else
			{
				nTerminalStack.push(tableGraphNode);
				index = this.analyzer.getGGLLTable().getNTerminal(tableGraphNode.getNodeReference()).getFirstNode();
			}
		}
		String error = "Error at line: " + line + " and column: " + column + ", \"" + this.analyzer.getParserToken().getCurrentToken().text + "\" found";
		if (expecteds.size() > 0)
		{
			error += ", but " + join(expecteds, ",") + " expected.";
		}
		else
		{
			error += ".";
		}
		this.analyzer.setError(new SyntacticException(error));
	}

	public int dealWithError(int lastIndex, int column, int line) throws Exception
	{
		sintaticErrorMessage(column, line, lastIndex);


		final ExtendedList<ErroStrategy> strategyList = new ExtendedList<ErroStrategy>();

		strategyList.append(new InsertStrategy(this.analyzer));
		strategyList.append(new ChangeStrategy(this.analyzer));
		strategyList.append(new DeleteStrategy(this.analyzer));
		strategyList.append(new DelimiterSearchStrategy(this.analyzer));

		int index = lastIndex;
		for (final ErroStrategy errorStrategy : strategyList.getAll())
		{
			index = errorStrategy.execute(lastIndex, column, line);
			if (index >= 0)
			{
				return index;
			}
		}
		if (index < 0)
		{
			this.analyzer.setError(new ErrorRecoveryException("symbol \"" + this.analyzer.getParserToken().getCurrentToken().text + "\" was ignored."));
			this.analyzer.getParserToken().readNext();
			if (this.analyzer.getParserToken().getCurrentToken().text.equals("$"))
			{
				return index;
			}
			else
			{
				index = dealWithError(lastIndex, this.analyzer.getParserToken().getCurrentToken().column + 1, this.analyzer.getParserToken().getCurrentToken().line + 1);
			}
		}
		return index;
	}

	public String join(ArrayList<String> s, String delimiter)
	{
		if (s == null || s.isEmpty())
			return "";
		Iterator<String> iter = s.iterator();
		StringBuilder builder = new StringBuilder("\"" + iter.next() + "\"");
		while (iter.hasNext())
		{
			builder.append(delimiter).append("\"" + iter.next() + "\"");
		}
		return builder.toString();
	}
}
