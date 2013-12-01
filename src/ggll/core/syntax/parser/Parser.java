package ggll.core.syntax.parser;

import ggll.core.lexical.Yylex;
import ggll.core.list.ExtendedList;
import ggll.core.semantics.SemanticRoutine;
import ggll.core.semantics.SemanticRoutineClass;
import ggll.core.syntax.error.ParserError;
import ggll.core.syntax.model.GGLLNode;
import ggll.core.syntax.model.ParseNode;
import ggll.core.syntax.model.TableGraphNode;
import ggll.core.syntax.model.TableNode;

public class Parser
{
	boolean continueSentinel;

	private SemanticRoutine semanticRoutines;
	private ParserAlternative parserAlternative;
	private ParserOutput parserOutput;
	private ParserError parserError;
	private ParserToken parserToken;
	private ParserStack parserStacks;
	private GGLLTable parserTable;

	private int I;
	private int UI;

	private boolean debug;
	private Yylex yylex;
	private SemanticRoutineClass semanticRoutineClass;

	private ExtendedList<Exception> errorList = new ExtendedList<Exception>();

	public Parser(GGLLTable analyzerTabs, Yylex yylex, SemanticRoutineClass semanticRoutineClass, boolean debug)
	{
		this.yylex = yylex;
		this.parserTable = analyzerTabs;
		this.semanticRoutineClass = semanticRoutineClass;
		this.debug = debug;
	}

	private void Output()
	{
		if (parserOutput != null)
		{
			parserOutput.Output();
		}
	}

	public void clearErrors()
	{
		this.getErrorList().removeAll();
	}

	public ExtendedList<Exception> getErrorList()
	{
		if (this.errorList == null)
		{
			this.errorList = new ExtendedList<Exception>();
		}
		return errorList;
	}

	public ParserAlternative getParseAlternative()
	{
		if (parserAlternative == null)
		{
			parserAlternative = new ParserAlternative(this);
		}

		return parserAlternative;
	}

	public ParserError getParseError()
	{
		if (parserError == null)
		{
			parserError = new ParserError(this);
		}
		return parserError;
	}

	public ParserStack getParserStacks()
	{
		if (parserStacks == null)
		{
			parserStacks = new ParserStack();
		}
		return parserStacks;
	}

	public GGLLTable getParseTable()
	{
		return parserTable;
	}

	public ParserToken getParseToken()
	{
		if (parserToken == null)
		{
			parserToken = new ParserToken(yylex);
		}

		return parserToken;
	}

	public SemanticRoutine getSemanticRoutines() throws Exception
	{
		if (semanticRoutines == null)
		{
			semanticRoutines = new SemanticRoutine(semanticRoutineClass);
		}

		return semanticRoutines;
	}

	public boolean isSucess()
	{
		return errorList.count() == 0;
	}

	public boolean next()
	{
		while (continueSentinel)
		{
			if (I != 0)
			{
				TableGraphNode currentGraphNode = getParseTable().getGraphNode(I);
				if (currentGraphNode.IsTerminal())
				{
					TableNode currentTerminal = getParseTable().getTermial(currentGraphNode.getNodeReference());
					if (currentGraphNode.isLambda())
					{
						try
						{
							getSemanticRoutines().setCurrentToken(null);
							getSemanticRoutines().execFunction(currentGraphNode.getSemanticRoutine());
						}
						catch (Exception e)
						{
							errorList.append(e);
						}

						I = currentGraphNode.getSucessorIndex();
						UI = I;
					}
					else
					{
						if ((currentTerminal.getName()).equals(getParseToken().getCurrentSymbol()))
						{
							getParserStacks().getParseStack().push(new ParseNode(currentTerminal.getFlag(), getParseToken().getCurrentSymbol(), getParseToken().getCurrentSemanticSymbol()));
							Output();

							try
							{
								getSemanticRoutines().setCurrentToken(getParseToken().getCurrentToken());
								getSemanticRoutines().execFunction(currentGraphNode.getSemanticRoutine());
							}
							catch (Exception e)
							{
								errorList.append(e);
							}

							try
							{

								getParseToken().readNext();
							}
							catch (Exception e)
							{
								errorList.append(e);
								return false;
							}

							I = currentGraphNode.getSucessorIndex();
							UI = I;
							getParserStacks().setTop(getParserStacks().getTop() + 1);

							getParserStacks().getNTerminalStack().clear();

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
								if (getParserStacks().getNTerminalStack().empty())
								{
									try
									{
										I = getParseError().dealWithError(UI, getParseToken().getCurrentToken().column + 1, getParseToken().getCurrentToken().line + 1);
									}
									catch (Exception e)
									{
										errorList.append(e);
										I = -1;
									}

									continueSentinel = I >= 0;
								}
								else
								{
									int alternative = getParseAlternative().findAlternative(I, getParserStacks().getNTerminalStack(), getParserStacks().getGGLLStack());
									if (alternative != 0)
									{
										I = alternative;
									}
									else
									{
										try
										{
											I = getParseError().dealWithError(UI, getParseToken().getCurrentToken().column + 1, getParseToken().getCurrentToken().line + 1);
										}
										catch (Exception e)
										{
											errorList.append(e);
										}
										continueSentinel = I >= 0;
									}
								}
							}
						}
					}
				}
				else
				{
					TableNode currentNTerminal = getParseTable().getNTerminal(getParseTable().getGraphNode(I).getNodeReference());
					getParserStacks().getNTerminalStack().push(I);
					getParserStacks().getGGLLStack().push(new GGLLNode(I, getParserStacks().getParseStack().size()));
					I = currentNTerminal.getFirstNode();
				}
			}
			else
			{
				if (!getParserStacks().getGGLLStack().empty())
				{
					GGLLNode grViewStackNode = getParserStacks().getGGLLStack().pop();
					ParseNode auxParseSNode = null;

					while (getParserStacks().getParseStack().size() > grViewStackNode.size)
					{
						auxParseSNode = getParserStacks().getParseStack().pop();
					}

					if (auxParseSNode != null)
					{
						TableNode currentNTerminal = getParseTable().getNTerminal(getParseTable().getGraphNode(grViewStackNode.index).getNodeReference());
						getParserStacks().getParseStack().push(new ParseNode(currentNTerminal.getFlag(), currentNTerminal.getName(), auxParseSNode.getSemanticSymbol()));
						Output();
					}

					I = grViewStackNode.index;

					try
					{
						getSemanticRoutines().setCurrentToken(getParseToken().getCurrentToken());
						getSemanticRoutines().execFunction(getParseTable().getGraphNode(I).getSemanticRoutine());
					}
					catch (Exception e)
					{
						errorList.append(e);
					}

					I = getParseTable().getGraphNode(I).getSucessorIndex();
					UI = I;
				}
				else
				{
					if (!getParseToken().getCurrentSymbol().equals(new String("$")))
					{

						try
						{
							I = getParseError().dealWithError(UI, getParseToken().getCurrentToken().column + 1, getParseToken().getCurrentToken().line + 1);
						}
						catch (Exception e)
						{
							errorList.append(e);
						}
					}
					continueSentinel = I > 0;
				}
			}
		}
		return false;
	}

	public void nextToEnd()
	{
		while (next())
			;
	}

	public void run()
	{
		clearErrors();

		getParseToken().setCurrentSemanticSymbol(null);
		getParserStacks().init();

		getParseToken().getYylex().TableNodes(getParseTable().getTermials());
		getParseTable().setGraphNode(0, new TableGraphNode());
		getParseTable().getGraphNode(0).setIsTerminal(false);
		getParseTable().getGraphNode(0).setNodeReference(1);
		getParseTable().getGraphNode(0).setAlternativeIndex(0);
		getParseTable().getGraphNode(0).setSucessorIndex(0);

		getParserStacks().getGGLLStack().push(new GGLLNode(0, 0));
		getParserStacks().setTop(0);
		try
		{
			getSemanticRoutines().setParseStack(getParserStacks().getParseStack());
			getParseToken().readNext();
		}
		catch (Exception e)
		{
			errorList.append(e);
			return;
		}

		UI = I = getParseTable().getNTerminal(1).getFirstNode();

		continueSentinel = true;

		if (!debug)
		{
			nextToEnd();
		}
	}

	public void setError(Exception error)
	{
		this.getErrorList().append(error);
	}

	public void setParserOutput(ParserOutput parserOutput)
	{
		this.parserOutput = parserOutput;
	}
}
