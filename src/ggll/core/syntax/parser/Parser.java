package ggll.core.syntax.parser;

import ggll.core.lexical.Yylex;
import ggll.core.semantics.SemanticRoutine;
import ggll.core.semantics.SemanticRoutineClass;
import ggll.core.syntax.error.ParserError;
import ggll.core.syntax.model.GGLLNode;
import ggll.core.syntax.model.ParseNode;
import ggll.core.syntax.model.TableGraphNode;
import ggll.core.syntax.model.TableNode;

import java.util.ArrayList;


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
	
	private ArrayList<String> errorList = new ArrayList<String>();
	private boolean sucess = true;	

	public Parser(GGLLTable analyzerTabs, Yylex yylex, SemanticRoutineClass semanticRoutineClass, boolean debug)
	{
		this.yylex = yylex;
		this.parserTable = analyzerTabs;
		this.semanticRoutineClass = semanticRoutineClass;
		this.debug = debug;
	}

	private void Output()
	{
		if(parserOutput != null)
		{
			parserOutput.Output();
		}
	}

	public void clearErrors()
	{
		this.getErrorList().clear();
	}

	public ArrayList<String> getErrorList()
	{
		if(this.errorList == null)
		{
			this.errorList = new ArrayList<String>();
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

	public ParserStack getParseStacks()
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
			semanticRoutines = new SemanticRoutine(this, semanticRoutineClass);
		}

		return semanticRoutines;
	}
	
	public boolean isSucess()
	{
		return sucess;
	}

	public boolean next() throws Exception
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
						getSemanticRoutines().setCurrentToken(null);
						getSemanticRoutines().execFunction(currentGraphNode.getSemanticRoutine());

						I = currentGraphNode.getSucessorIndex();
						UI = I;
					}
					else
					{
						if ((currentTerminal.getName()).equals(getParseToken().getCurrentSymbol()))
						{
							getParseStacks().getParseStack().push(new ParseNode(currentTerminal.getFlag(), getParseToken().getCurrentSymbol(), getParseToken().getCurrentSemanticSymbol()));
							Output();
							
							getSemanticRoutines().setCurrentToken(getParseToken().getCurrentToken());
							getSemanticRoutines().execFunction(currentGraphNode.getSemanticRoutine());

							getParseToken().readNext();

							I = currentGraphNode.getSucessorIndex();
							UI = I;
							getParseStacks().setTop(getParseStacks().getTop() + 1);

							getParseStacks().getNTerminalStack().clear();

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
								if (getParseStacks().getNTerminalStack().empty())
								{
									I = getParseError().dealWithError(UI, getParseToken().getCurrentToken().charBegin + 1, getParseToken().getCurrentToken().line + 1);
									continueSentinel = I >= 0;
									setSucess(false);
								}
								else
								{
									int alternative = getParseAlternative().findAlternative(I, getParseStacks().getNTerminalStack(), getParseStacks().getGrViewStack());
									if (alternative != 0)
									{
										I = alternative;
									}
									else
									{
										I = getParseError().dealWithError(UI, getParseToken().getCurrentToken().charBegin + 1, getParseToken().getCurrentToken().line + 1);
										continueSentinel = I >= 0;
										setSucess(false);
									}
								}
							}
						}
					}
				}
				else
				{
					TableNode currentNTerminal = getParseTable().getNTerminal(getParseTable().getGraphNode(I).getNodeReference());
					getParseStacks().getNTerminalStack().push(I);
					getParseStacks().getGrViewStack().push(new GGLLNode(I, getParseStacks().getTop() + 1));
					I = currentNTerminal.getFirstNode(); 
				}
			}
			else
			{
				if (!getParseStacks().getGrViewStack().empty())
				{
					GGLLNode grViewStackNode = getParseStacks().getGrViewStack().pop();
					ParseNode auxParseSNode = null;

					while (getParseStacks().getParseStack().size() >= grViewStackNode.size)
					{
						auxParseSNode = getParseStacks().getParseStack().pop();
					}

					if (auxParseSNode != null)
					{
						TableNode currentNTerminal = getParseTable().getNTerminal(getParseTable().getGraphNode(grViewStackNode.indexNode).getNodeReference());
						getParseStacks().getParseStack().push(new ParseNode(currentNTerminal.getFlag(), currentNTerminal.getName(), auxParseSNode.getSemanticSymbol()));
						Output();
					}

					I = grViewStackNode.indexNode;

					getSemanticRoutines().setCurrentToken(getParseToken().getCurrentToken());
					getSemanticRoutines().execFunction(getParseTable().getGraphNode(I).getSemanticRoutine());

					I = getParseTable().getGraphNode(I).getSucessorIndex();
					UI = I;
				}
				else
				{
					if (!getParseToken().getCurrentSymbol().equals(new String("$")))
					{
						I = getParseError().dealWithError(UI, getParseToken().getCurrentToken().charBegin + 1, getParseToken().getCurrentToken().line + 1);
						setSucess(false);
					}
					continueSentinel = I > 0;
				}
			}
		}
		return false;
	}

	public void nextToEnd() throws Exception
	{
		while(next());
	}
	
	public void run() throws Exception
	{
		clearErrors();

		getParseToken().setCurrentSemanticSymbol(null);
		getParseStacks().init();

		setSucess(true);

		getParseToken().getYylex().TabT(getParseTable().getTermials());
		getParseTable().setGraphNode(0, new TableGraphNode());
		getParseTable().getGraphNode(0).setIsTerminal(false);
		getParseTable().getGraphNode(0).setNodeReference(1);
		getParseTable().getGraphNode(0).setAlternativeIndex(0);
		getParseTable().getGraphNode(0).setSucessorIndex(0);

		getParseStacks().getGrViewStack().push(new GGLLNode(0, 1));
		getParseStacks().setTop(0);

		getParseToken().readNext();

		UI = I = getParseTable().getNTerminal(1).getFirstNode();

		continueSentinel = true;

		if (!debug)
		{
			nextToEnd();
		}
	}

	public void setError(String error)
	{
		this.getErrorList().add(error);
	}

	public void setParserOutput(ParserOutput parserOutput)
	{
		this.parserOutput = parserOutput;
	}

	public void setSucess(boolean sucess)
	{
		this.sucess = sucess;
	}
}