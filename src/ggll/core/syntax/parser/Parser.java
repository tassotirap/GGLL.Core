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

	private SemanticRoutine semanticRoutine;
	private FindAlternativeHelper findAlternativeHelper;
	private ParserOutput parserOutput;
	private ParserError parserError;
	private ParserToken parserToken;
	private ParserStack parserStacks;
	private GGLLTable ggllTable;

	private int currentIndex;
	private int safeIndex;
	private boolean sucess;

	private final boolean debug;
	private final Yylex yylex;
	private final SemanticRoutineClass semanticRoutineClass;

	private ExtendedList<Exception> errorList = new ExtendedList<Exception>();

	public Parser(GGLLTable analyzerTabs, Yylex yylex, SemanticRoutineClass semanticRoutineClass, boolean debug)
	{
		this.yylex = yylex;
		this.ggllTable = analyzerTabs;
		this.semanticRoutineClass = semanticRoutineClass;

		this.errorList = new ExtendedList<Exception>();
		this.findAlternativeHelper = new FindAlternativeHelper(this);
		this.parserError = new ParserError(this);
		this.parserToken = new ParserToken(this.yylex);
		this.parserStacks = new ParserStack();
		this.semanticRoutine = new SemanticRoutine(this.semanticRoutineClass);

		this.debug = debug;
		this.sucess = true;
	}

	private void endRightSide()
	{
		if (!getParserStacks().getGGLLStack().empty())
		{
			final GGLLNode ggllStackNode = getParserStacks().getGGLLStack().pop();

			ParseNode parseStack = null;
			while (getParserStacks().getParseStack().size() > ggllStackNode.size)
			{
				parseStack = getParserStacks().getParseStack().pop();
			}

			if (parseStack != null)
			{
				final TableNode currentNTerminal = getGGLLTable().getNTerminal(getGGLLTable().getGraphNode(ggllStackNode.index).getNodeReference());
				getParserStacks().getParseStack().push(new ParseNode(currentNTerminal.getFlag(), currentNTerminal.getName(), parseStack.getSemanticSymbol()));
				output();
			}
			this.currentIndex = ggllStackNode.index;
			
			executeSemanticRoutine(getGGLLTable().getGraphNode(this.currentIndex));
			
			this.currentIndex = getGGLLTable().getGraphNode(this.currentIndex).getSucessorIndex();
			this.safeIndex = this.currentIndex;
		}
		else
		{
			if (!getParserToken().getCurrentSymbol().equals("$"))
			{
				this.continueSentinel = errorRecovery();
			}
			else
			{
				this.continueSentinel = false;
			}
		}
	}

	private boolean errorRecovery()
	{
		try
		{
			sucess = false;
			this.currentIndex = getParseError().dealWithError(this.safeIndex, getParserToken().getCurrentToken().column + 1, getParserToken().getCurrentToken().line + 1);
		}
		catch (final Exception e)
		{
			setError(e);
			this.currentIndex = -1;
		}

		return this.currentIndex >= 0;
	}

	private void executeSemanticRoutine(final TableGraphNode currentGraphNode)
	{
		if (sucess)
		{
			try
			{
				getSemanticRoutines().setCurrentToken(getParserToken().getCurrentToken());
				getSemanticRoutines().execFunction(currentGraphNode.getSemanticRoutine());
			}
			catch (final Exception e)
			{
				sucess = false;
				setError(e);
			}
			catch (Throwable e)
			{
				sucess = false;
				setError((Exception)e);
			}
		}
	}

	private void nTerminal(TableGraphNode currentGraphNode)
	{
		final TableNode currentNTerminal = getGGLLTable().getNTerminal(currentGraphNode.getNodeReference());
		getParserStacks().getNTerminalStack().push(this.currentIndex);
		getParserStacks().getGGLLStack().push(new GGLLNode(this.currentIndex, getParserStacks().getParseStack().size()));
		this.currentIndex = currentNTerminal.getFirstNode();
	}

	public void clearErrors()
	{
		getErrorList().removeAll();
	}

	public ExtendedList<Exception> getErrorList()
	{
		return this.errorList;
	}

	public GGLLTable getGGLLTable()
	{
		return this.ggllTable;
	}

	public FindAlternativeHelper getParseAlternative()
	{
		return this.findAlternativeHelper;
	}

	public ParserError getParseError()
	{
		return this.parserError;
	}

	public ParserStack getParserStacks()
	{
		return this.parserStacks;
	}

	public ParserToken getParserToken()
	{
		return this.parserToken;
	}

	public SemanticRoutine getSemanticRoutines()
	{
		return this.semanticRoutine;
	}

	public boolean isSucess()
	{
		return sucess;
	}

	public boolean next()
	{
		while (this.continueSentinel)
		{
			if (this.currentIndex != 0)
			{
				final TableGraphNode currentGraphNode = getGGLLTable().getGraphNode(this.currentIndex);
				if (currentGraphNode.IsTerminal())
				{
					final TableNode currentTerminal = getGGLLTable().getTermial(currentGraphNode.getNodeReference());
					if (currentGraphNode.isLambda())
					{
						executeSemanticRoutine(currentGraphNode);

						this.currentIndex = currentGraphNode.getSucessorIndex();
						this.safeIndex = this.currentIndex;
					}
					else
					{
						if (currentTerminal.getName().equals(getParserToken().getCurrentSymbol()))
						{
							getParserStacks().getParseStack().push(new ParseNode(currentTerminal.getFlag(), getParserToken().getCurrentSymbol(), getParserToken().getCurrentSemanticSymbol()));

							output();

							executeSemanticRoutine(currentGraphNode);

							try
							{
								getParserToken().readNext();
							}
							catch (final Exception e)
							{
								setError(e);
								return false;
							}

							this.currentIndex = currentGraphNode.getSucessorIndex();
							this.safeIndex = this.currentIndex;

							getParserStacks().getNTerminalStack().clear();

							if (this.debug)
							{
								return true;
							}
						}
						else
						{
							if (currentGraphNode.getAlternativeIndex() != 0)
							{
								this.currentIndex = currentGraphNode.getAlternativeIndex();
							}
							else
							{
								if (!getParserStacks().getNTerminalStack().empty())
								{
									final int alternative = getParseAlternative().find(this.currentIndex, getParserStacks().getNTerminalStack(), getParserStacks().getGGLLStack());
									if (alternative != 0)
									{
										this.currentIndex = alternative;
									}
									else
									{
										this.continueSentinel = errorRecovery();
									}
								}
								else
								{
									this.continueSentinel = errorRecovery();
								}
							}
						}
					}
				}
				else
				{
					nTerminal(currentGraphNode);
				}
			}
			else
			{
				endRightSide();
			}
		}
		return false;
	}

	public void nextToEnd()
	{
		while (next())
			;
	}

	public void output()
	{
		if (this.parserOutput != null)
		{
			this.parserOutput.output();
		}
	}

	public void run()
	{
		clearErrors();

		getParserToken().setCurrentSemanticSymbol(null);
		getParserStacks().init();
		getParserToken().getYylex().TableNodes(getGGLLTable().getTermials());
		getGGLLTable().setGraphNode(0, new TableGraphNode(0, false, 1, null, 0));
		getParserStacks().getGGLLStack().push(new GGLLNode(0, 0));
		try
		{
			getSemanticRoutines().setParseStack(getParserStacks().getParseStack());
			getParserToken().readNext();
		}
		catch (final Exception e)
		{
			setError(e);
			return;
		}

		this.safeIndex = this.currentIndex = getGGLLTable().getNTerminal(1).getFirstNode();

		this.continueSentinel = true;

		if (!this.debug)
		{
			nextToEnd();
		}
	}

	public void setError(Exception error)
	{
		getErrorList().append(error);
	}

	public void setParserOutput(ParserOutput parserOutput)
	{
		this.parserOutput = parserOutput;
	}

	public void setParserStacks(ParserStack parserStack)
	{
		this.parserStacks = parserStack;
	}

	public void setParseToken(ParserToken parserToken)
	{
		this.parserToken = parserToken;
	}

}
