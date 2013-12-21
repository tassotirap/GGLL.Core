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
	/* BEGIN ROUTINE: GGLL */
	public void GGLL()
	{
		this.ggllTable = new GGLLTable(this.nodes, this.nTerminal, this.terminal);
	}

	/* BEGIN SEMANTIC ROUTINES */
	/* BEGIN ROUTINE: IT_EMPTY */
	public void IT_EMPTY()
	{
		this.index++;
	}

	/* END ROUTINE: IT_NAME */
	/* BEGIN ROUTINE: IT_FLAG */
	public void IT_FLAG()
	{
		if (this.isNTerminal)
		{
			this.nTerminal[this.index].setFlag(getCurrentToken().token());
		}
		else
		{
			this.terminal[this.index].setFlag(getCurrentToken().token());
		}
	}

	/* END ROUTINE: IT_FLAG */
	/* BEGIN ROUTINE: IT_FNO */
	public void IT_FNO()
	{
		final int value = Integer.parseInt(getCurrentToken().token());
		if (this.isNTerminal)
		{
			this.nTerminal[this.index] = new TableNode();
			this.nTerminal[this.index].setFirstNode(value);
		}
		else
		{
			this.terminal[this.index] = new TableNode();
			this.terminal[this.index].setFirstNode(value);
		}
	}

	/* END ROUTINE: GGLL */
	/* BEGIN ROUTINE: IT_NAME */
	public void IT_NAME()
	{
		if (this.isNTerminal)
		{
			this.nTerminal[this.index].setName(getCurrentToken().token());
		}
		else
		{
			this.terminal[this.index].setName(getCurrentToken().token());
		}
		this.index++;
	}

	/* END ROUTINE: TE_SIZE */
	/* BEGIN ROUTINE: NTE_SIZE */
	public void NTE_SIZE()
	{
		final int size = Integer.parseInt(getCurrentToken().token());
		this.nTerminal = new TableNode[size];
		this.index = 0;
		this.isNTerminal = true;
	}

	/* END ROUTINE: IT_FNO */
	/* BEGIN ROUTINE: TE_SIZE */
	public void TE_SIZE()
	{
		final int size = Integer.parseInt(getCurrentToken().token());
		this.terminal = new TableNode[size];
		this.index = 0;
		this.isNTerminal = false;
	}

	/* END ROUTINE: TG_ISTERM */
	/* BEGIN ROUTINE: TG_ALT */
	public void TG_ALT()
	{
		final int value = Integer.parseInt(getCurrentToken().token());
		this.nodes[this.index] = new TableGraphNode();
		this.nodes[this.index].setAlternativeIndex(value);
	}

	/* END ROUTINE: IT_EMPTY */
	/* BEGIN ROUTINE: TG_EMPTY */
	public void TG_EMPTY()
	{
		this.index++;
	}

	/* END ROUTINE: TG_NREF */
	/* BEGIN ROUTINE: TG_ISTERM */
	public void TG_ISTERM()
	{
		final boolean value = Boolean.parseBoolean(getCurrentToken().token());
		this.nodes[this.index].setIsTerminal(value);
	}

	/* END ROUTINE: TG_SROUT */
	/* BEGIN ROUTINE: TG_NREF */
	public void TG_NREF()
	{
		final int value = Integer.parseInt(getCurrentToken().token());
		this.nodes[this.index].setNodeReference(value);
	}

	/* END ROUTINE: TG_ALT */
	/* BEGIN ROUTINE: TG_SIZE */
	public void TG_SIZE()
	{
		final int size = Integer.parseInt(getCurrentToken().token());
		this.nodes = new TableGraphNode[size];
		this.index = 0;
	}

	/* END ROUTINE: TG_SIZE */

	/* END ROUTINE: TG_SUC */
	/* BEGIN ROUTINE: TG_SROUT */
	public void TG_SROUT()
	{
		this.nodes[this.index].setSemanticRoutine(getCurrentToken().token());
	}

	/* END ROUTINE: NTE_SIZE */
	/* BEGIN ROUTINE: TG_SUC */
	public void TG_SUC()
	{
		final int value = Integer.parseInt(getCurrentToken().token());
		this.nodes[this.index].setSucessorIndex(value);
		this.index++;
	}

	/* END SEMANTIC ROUTINES */
	/* do not modify the lines bellow */
	/*-------------- SELF GENERATED METHODS -------------*/
}
