package org.ggll.core.syntax.parser;

import java.io.File;
import java.util.ArrayList;

import org.ggll.core.lexical.Yylex;
import org.ggll.core.semantics.SemanticRoutine;
import org.ggll.core.syntax.error.ParserError;
import org.ggll.core.syntax.model.GrViewNode;
import org.ggll.core.syntax.model.ParseNode;
import org.ggll.core.syntax.model.TableGraphNode;
import org.ggll.core.syntax.model.TableNode;

public class Parser
{
	boolean continueSentinel;

	private SemanticRoutine semanticRoutines;
	private ParserAlternative parserAlternative;
	private ParserError parserError;
	private ParserToken parserToken;
	private ParserStack parserStacks;
	private ParserTable parserTable;

	private int I;
	private int UI;

	private boolean debug;
	private Yylex yylex;
	private File semanticFile;
	
	private ArrayList<String> errorList = new ArrayList<String>();
	private boolean sucess = true;

	public Parser(ParserTable analyzerTabs, Yylex yylex, File semanticFile, boolean debug)
	{
		this.yylex = yylex;
		this.parserTable = analyzerTabs;
		this.semanticFile = semanticFile;
		this.debug = debug;
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

	public ParserTable getParseTable()
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
			semanticRoutines = new SemanticRoutine(parserStacks.getParseStack(), semanticFile.getParent() + "\\", semanticFile.getName().replace(".java", ""));
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
					getParseStacks().getGrViewStack().push(new GrViewNode(I, getParseStacks().getTop() + 1));
					I = currentNTerminal.getFirstNode();
				}
			}
			else
			{
				if (!getParseStacks().getGrViewStack().empty())
				{
					GrViewNode grViewStackNode = getParseStacks().getGrViewStack().pop();
					ParseNode auxParseSNode = null;

					while (getParseStacks().getParseStack().size() >= grViewStackNode.size)
					{
						auxParseSNode = getParseStacks().getParseStack().pop();
					}

					if (auxParseSNode != null)
					{
						TableNode currentNTerminal = getParseTable().getNTerminal(getParseTable().getGraphNode(grViewStackNode.indexNode).getNodeReference());
						getParseStacks().getParseStack().push(new ParseNode(currentNTerminal.getFlag(), currentNTerminal.getName(), auxParseSNode.getSemanticSymbol()));
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

	public void run() throws Exception
	{
		clearErrors();

		getParseToken().setCurrentSemanticSymbol(null);
		getParseStacks().init();

		setSucess(true);

		getParseToken().getYylex().TabT(getParseTable().getTermialTable());
		getParseTable().setGraphNode(0, new TableGraphNode());
		getParseTable().getGraphNode(0).setIsTerminal(false);
		getParseTable().getGraphNode(0).setNodeReference(1);
		getParseTable().getGraphNode(0).setAlternativeIndex(0);
		getParseTable().getGraphNode(0).setSucessorIndex(0);

		getParseStacks().getGrViewStack().push(new GrViewNode(0, 1));
		getParseStacks().setTop(0);

		getParseToken().readNext();

		UI = I = getParseTable().getNTerminal(1).getFirstNode();

		continueSentinel = true;

		if (!debug)
		{
			while(next());
		}
	}

	public void setError(String error)
	{
		this.getErrorList().add(error);
	}

	public void setSucess(boolean sucess)
	{
		this.sucess = sucess;
	}
}
