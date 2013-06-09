package ggll.core.xml;
import ggll.core.semantics.SemanticRoutineClass;
import ggll.core.syntax.model.TableGraphNode;
import ggll.core.syntax.model.TableNode;
import ggll.core.syntax.parser.GGLLTable;

public class XmlSemanticFile extends SemanticRoutineClass
{
	public GGLLTable ggllTable;
	
	private TableGraphNode[] nodes;
	private TableNode[] nTerminal;
	private TableNode[] terminal;
	private int index;
	private boolean isNTerminal;

	/*-------------- SEMANTIC ROUTINES ESPECIFICATION ------------*/
	/* you can modify the lines bellow */

	/* BEGIN SEMANTIC ROUTINES */
	/* BEGIN ROUTINE: IT_EMPTY */
	public void IT_EMPTY()
	{
		index++;
	}

	/* BEGIN SEMANTIC ROUTINES */
	/* BEGIN ROUTINE: GGLL */
	public void GGLL()
	{
		ggllTable = new GGLLTable(nodes, nTerminal, terminal);
	}

	/* END ROUTINE: GGLL */
	/* BEGIN ROUTINE: IT_NAME */
	public void IT_NAME()
	{
		if(isNTerminal)
		{
			nTerminal[index].setName(currentToken.token());
		}
		else
		{
			terminal[index].setName(currentToken.token());
		}
		index++;
	}

	/* END ROUTINE: IT_NAME */
	/* BEGIN ROUTINE: IT_FLAG */
	public void IT_FLAG()
	{
		if(isNTerminal)
		{
			nTerminal[index].setFlag(currentToken.token());
		}
		else
		{
			terminal[index].setFlag(currentToken.token());
		}
	}

	/* END ROUTINE: IT_FLAG */
	/* BEGIN ROUTINE: IT_FNO */
	public void IT_FNO()
	{
		int value = Integer.parseInt(currentToken.token());
		if(isNTerminal)
		{
			nTerminal[index] = new TableNode();
			nTerminal[index].setFirstNode(value);
		}
		else
		{
			terminal[index] = new TableNode();
			terminal[index].setFirstNode(value);
		}
	}

	/* END ROUTINE: IT_FNO */
	/* BEGIN ROUTINE: TE_SIZE */
	public void TE_SIZE()
	{
		int size = Integer.parseInt(currentToken.token());
		terminal = new TableNode[size];
		index = 0;
		isNTerminal = false;
	}

	/* END ROUTINE: TE_SIZE */
	/* BEGIN ROUTINE: NTE_SIZE */
	public void NTE_SIZE()
	{
		int size = Integer.parseInt(currentToken.token());
		nTerminal = new TableNode[size];
		index = 0;
		isNTerminal = true;
	}

	/* END ROUTINE: NTE_SIZE */
	/* BEGIN ROUTINE: TG_SUC */
	public void TG_SUC()
	{
		int value = Integer.parseInt(currentToken.token()); 
		nodes[index].setSucessorIndex(value);
		index++;
	}

	/* END ROUTINE: TG_SUC */
	/* BEGIN ROUTINE: TG_SROUT */
	public void TG_SROUT()
	{
		nodes[index].setSemanticRoutine(currentToken.token());
	}

	/* END ROUTINE: TG_SROUT */
	/* BEGIN ROUTINE: TG_NREF */
	public void TG_NREF()
	{
		int value = Integer.parseInt(currentToken.token()); 
		nodes[index].setNodeReference(value);
	}

	/* END ROUTINE: TG_NREF */
	/* BEGIN ROUTINE: TG_ISTERM */
	public void TG_ISTERM()
	{
		boolean value = Boolean.parseBoolean(currentToken.token()); 
		nodes[index].setIsTerminal(value);
	}

	/* END ROUTINE: TG_ISTERM */
	/* BEGIN ROUTINE: TG_ALT */
	public void TG_ALT()
	{
		int value = Integer.parseInt(currentToken.token()); 
		nodes[index] = new TableGraphNode();
		nodes[index].setAlternativeIndex(value);
	}
	
	/* END ROUTINE: IT_EMPTY */
	/* BEGIN ROUTINE: TG_EMPTY */
	public void TG_EMPTY()
	{
		index++;
	}

	/* END ROUTINE: TG_ALT */
	/* BEGIN ROUTINE: TG_SIZE */
	public void TG_SIZE()
	{
		int size = Integer.parseInt(currentToken.token());
		nodes = new TableGraphNode[size];
		index = 0;
	}
	/* END ROUTINE: TG_SIZE */

	/* END SEMANTIC ROUTINES */
	/* do not modify the lines bellow */
	/*-------------- SELF GENERATED METHODS -------------*/
}
