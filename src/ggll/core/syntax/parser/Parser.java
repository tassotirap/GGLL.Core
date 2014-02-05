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
	private final GGLLTable parserTable;

	private int currentIndex;
	private int safeIndex;

	private final boolean debug;
	private final Yylex yylex;
	private final SemanticRoutineClass semanticRoutineClass;

	private ExtendedList<Exception> errorList = new ExtendedList<Exception>();

	public Parser(GGLLTable analyzerTabs, Yylex yylex, SemanticRoutineClass semanticRoutineClass, boolean debug)
	{
		this.yylex = yylex;
		this.parserTable = analyzerTabs;
		this.semanticRoutineClass = semanticRoutineClass;
		this.debug = debug;
	}

	public void Output()
	{
		if (this.parserOutput != null)
		{
			this.parserOutput.Output();
		}
	}

	public void clearErrors()
	{
		getErrorList().removeAll();
	}

	public ExtendedList<Exception> getErrorList()
	{
		if (this.errorList == null)
		{
			this.errorList = new ExtendedList<Exception>();
		}
		return this.errorList;
	}

	public ParserAlternative getParseAlternative()
	{
		if (this.parserAlternative == null)
		{
			this.parserAlternative = new ParserAlternative(this);
		}

		return this.parserAlternative;
	}

	public ParserError getParseError()
	{
		if (this.parserError == null)
		{
			this.parserError = new ParserError(this);
		}
		return this.parserError;
	}

	public ParserStack getParserStacks()
	{
		if (this.parserStacks == null)
		{
			this.parserStacks = new ParserStack();
		}
		return this.parserStacks;
	}
	
	public void setParserStacks(ParserStack parserStack)
	{
		this.parserStacks = parserStack;
	}

	public GGLLTable getGGLLTable()
	{
		return this.parserTable;
	}

	public ParserToken getParseToken()
	{
		if (this.parserToken == null)
		{
			this.parserToken = new ParserToken(this.yylex);
		}

		return this.parserToken;
	}
	
	public void setParseToken(ParserToken parserToken)
	{
		this.parserToken = parserToken;
	}

	public SemanticRoutine getSemanticRoutines()
	{
		try
		{
			if (this.semanticRoutines == null)
			{
				this.semanticRoutines = new SemanticRoutine(this.semanticRoutineClass);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return this.semanticRoutines;
	}

	public boolean isSucess()
	{
		return this.errorList.count() == 0;
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
						try
						{
							getSemanticRoutines().setCurrentToken(null);
							getSemanticRoutines().execFunction(currentGraphNode.getSemanticRoutine());
						}
						catch (final Exception e)
						{
							this.errorList.append(e);
						}

						this.currentIndex = currentGraphNode.getSucessorIndex();
						this.safeIndex = this.currentIndex;
					}
					else
					{
						if (currentTerminal.getName().equals(getParseToken().getCurrentSymbol()))
						{
							getParserStacks().getParseStack().push(new ParseNode(currentTerminal.getFlag(), getParseToken().getCurrentSymbol(), getParseToken().getCurrentSemanticSymbol()));
							Output();

							try
							{
								getSemanticRoutines().setCurrentToken(getParseToken().getCurrentToken());
								getSemanticRoutines().execFunction(currentGraphNode.getSemanticRoutine());
							}
							catch (final Exception e)
							{
								this.errorList.append(e);
							}

							try
							{

								getParseToken().readNext();
							}
							catch (final Exception e)
							{
								this.errorList.append(e);
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
								if (getParserStacks().getNTerminalStack().empty())
								{
									try
									{
										this.currentIndex = getParseError().dealWithError(this.safeIndex, getParseToken().getCurrentToken().column + 1, getParseToken().getCurrentToken().line + 1);
									}
									catch (final Exception e)
									{
										this.errorList.append(e);
										this.currentIndex = -1;
									}

									this.continueSentinel = this.currentIndex >= 0;
								}
								else
								{
									final int alternative = getParseAlternative().findAlternative(this.currentIndex, getParserStacks().getNTerminalStack(), getParserStacks().getGGLLStack());
									if (alternative != 0)
									{
										this.currentIndex = alternative;
									}
									else
									{
										try
										{
											this.currentIndex = getParseError().dealWithError(this.safeIndex, getParseToken().getCurrentToken().column + 1, getParseToken().getCurrentToken().line + 1);
										}
										catch (final Exception e)
										{
											this.errorList.append(e);
										}
										this.continueSentinel = this.currentIndex >= 0;
									}
								}
							}
						}
					}
				}
				else
				{
					final TableNode currentNTerminal = getGGLLTable().getNTerminal(getGGLLTable().getGraphNode(this.currentIndex).getNodeReference());
					getParserStacks().getNTerminalStack().push(this.currentIndex);
					getParserStacks().getGGLLStack().push(new GGLLNode(this.currentIndex, getParserStacks().getParseStack().size()));
					this.currentIndex = currentNTerminal.getFirstNode();
				}
			}
			else
			{
				if (!getParserStacks().getGGLLStack().empty())
				{
					final GGLLNode grViewStackNode = getParserStacks().getGGLLStack().pop();
					ParseNode auxParseSNode = null;

					while (getParserStacks().getParseStack().size() > grViewStackNode.size)
					{
						auxParseSNode = getParserStacks().getParseStack().pop();
					}

					if (auxParseSNode != null)
					{
						final TableNode currentNTerminal = getGGLLTable().getNTerminal(getGGLLTable().getGraphNode(grViewStackNode.index).getNodeReference());
						getParserStacks().getParseStack().push(new ParseNode(currentNTerminal.getFlag(), currentNTerminal.getName(), auxParseSNode.getSemanticSymbol()));
						Output();
					}

					this.currentIndex = grViewStackNode.index;

					try
					{
						getSemanticRoutines().setCurrentToken(getParseToken().getCurrentToken());
						getSemanticRoutines().execFunction(getGGLLTable().getGraphNode(this.currentIndex).getSemanticRoutine());
					}
					catch (final Exception e)
					{
						this.errorList.append(e);
					}

					this.currentIndex = getGGLLTable().getGraphNode(this.currentIndex).getSucessorIndex();
					this.safeIndex = this.currentIndex;
				}
				else
				{
					if (!getParseToken().getCurrentSymbol().equals(new String("$")))
					{

						try
						{
							this.currentIndex = getParseError().dealWithError(this.safeIndex, getParseToken().getCurrentToken().column + 1, getParseToken().getCurrentToken().line + 1);
						}
						catch (final Exception e)
						{
							this.errorList.append(e);
						}
					}
					this.continueSentinel = this.currentIndex > 0;
				}
			}
		}
		return false;
	}

	public void nextToEnd()
	{
		while (next())
		{
		}
	}

	public void run()
	{
		clearErrors();

		getParseToken().setCurrentSemanticSymbol(null);
		getParserStacks().init();
		getParseToken().getYylex().TableNodes(getGGLLTable().getTermials());
		getGGLLTable().setGraphNode(0, new TableGraphNode(0, false, 1, null, 0));
		getParserStacks().getGGLLStack().push(new GGLLNode(0, 0));
		try
		{
			getSemanticRoutines().setParseStack(getParserStacks().getParseStack());
			getParseToken().readNext();
		}
		catch (final Exception e)
		{
			this.errorList.append(e);
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
	
	
}
