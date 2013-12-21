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

	private int I;
	private int UI;

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

	private void Output()
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

	public GGLLTable getParseTable()
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

	public SemanticRoutine getSemanticRoutines() throws Exception
	{
		if (this.semanticRoutines == null)
		{
			this.semanticRoutines = new SemanticRoutine(this.semanticRoutineClass);
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
			if (this.I != 0)
			{
				final TableGraphNode currentGraphNode = getParseTable().getGraphNode(this.I);
				if (currentGraphNode.IsTerminal())
				{
					final TableNode currentTerminal = getParseTable().getTermial(currentGraphNode.getNodeReference());
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

						this.I = currentGraphNode.getSucessorIndex();
						this.UI = this.I;
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

							this.I = currentGraphNode.getSucessorIndex();
							this.UI = this.I;
							getParserStacks().setTop(getParserStacks().getTop() + 1);

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
								this.I = currentGraphNode.getAlternativeIndex();
							}
							else
							{
								if (getParserStacks().getNTerminalStack().empty())
								{
									try
									{
										this.I = getParseError().dealWithError(this.UI, getParseToken().getCurrentToken().column + 1, getParseToken().getCurrentToken().line + 1);
									}
									catch (final Exception e)
									{
										this.errorList.append(e);
										this.I = -1;
									}

									this.continueSentinel = this.I >= 0;
								}
								else
								{
									final int alternative = getParseAlternative().findAlternative(this.I, getParserStacks().getNTerminalStack(), getParserStacks().getGGLLStack());
									if (alternative != 0)
									{
										this.I = alternative;
									}
									else
									{
										try
										{
											this.I = getParseError().dealWithError(this.UI, getParseToken().getCurrentToken().column + 1, getParseToken().getCurrentToken().line + 1);
										}
										catch (final Exception e)
										{
											this.errorList.append(e);
										}
										this.continueSentinel = this.I >= 0;
									}
								}
							}
						}
					}
				}
				else
				{
					final TableNode currentNTerminal = getParseTable().getNTerminal(getParseTable().getGraphNode(this.I).getNodeReference());
					getParserStacks().getNTerminalStack().push(this.I);
					getParserStacks().getGGLLStack().push(new GGLLNode(this.I, getParserStacks().getParseStack().size()));
					this.I = currentNTerminal.getFirstNode();
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
						final TableNode currentNTerminal = getParseTable().getNTerminal(getParseTable().getGraphNode(grViewStackNode.index).getNodeReference());
						getParserStacks().getParseStack().push(new ParseNode(currentNTerminal.getFlag(), currentNTerminal.getName(), auxParseSNode.getSemanticSymbol()));
						Output();
					}

					this.I = grViewStackNode.index;

					try
					{
						getSemanticRoutines().setCurrentToken(getParseToken().getCurrentToken());
						getSemanticRoutines().execFunction(getParseTable().getGraphNode(this.I).getSemanticRoutine());
					}
					catch (final Exception e)
					{
						this.errorList.append(e);
					}

					this.I = getParseTable().getGraphNode(this.I).getSucessorIndex();
					this.UI = this.I;
				}
				else
				{
					if (!getParseToken().getCurrentSymbol().equals(new String("$")))
					{

						try
						{
							this.I = getParseError().dealWithError(this.UI, getParseToken().getCurrentToken().column + 1, getParseToken().getCurrentToken().line + 1);
						}
						catch (final Exception e)
						{
							this.errorList.append(e);
						}
					}
					this.continueSentinel = this.I > 0;
				}
			}
		}
		return false;
	}

	public void nextToEnd()
	{
		while (next())
		{
			;
		}
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
		catch (final Exception e)
		{
			this.errorList.append(e);
			return;
		}

		this.UI = this.I = getParseTable().getNTerminal(1).getFirstNode();

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
