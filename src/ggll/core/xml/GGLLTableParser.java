package ggll.core.xml;

import ggll.core.syntax.model.TableGraphNode;
import ggll.core.syntax.model.TableNode;
import ggll.core.syntax.parser.GGLLTable;

public class GGLLTableParser
{
	private final TableGraphNode[] nodes;
	private final TableNode[] nTerminal;
	private final TableNode[] terminal;

	public GGLLTable ggllTable;

	public GGLLTableParser()
	{
		this.nodes = new TableGraphNode[93];
		this.nTerminal = new TableNode[93];
		this.terminal = new TableNode[93];
		loadNodes();
		loadNTerminal();
		loadTerminal();
		this.ggllTable = new GGLLTable(this.nodes, this.nTerminal, this.terminal);
	}

	private void loadNodes()
	{
		this.nodes[1] = new TableGraphNode(0, false, 2, "-1", 2);
		this.nodes[2] = new TableGraphNode(0, false, 3, "-1", 0);
		this.nodes[3] = new TableGraphNode(0, true, 1, "-1", 4);
		this.nodes[4] = new TableGraphNode(0, true, 2, "-1", 5);
		this.nodes[5] = new TableGraphNode(7, true, 3, "-1", 6);
		this.nodes[6] = new TableGraphNode(0, true, 4, "-1", 8);
		this.nodes[7] = new TableGraphNode(0, true, 8, "IT_EMPTY", 0);
		this.nodes[8] = new TableGraphNode(0, true, 5, "IT_FNO", 9);
		this.nodes[9] = new TableGraphNode(0, true, 6, "-1", 10);
		this.nodes[10] = new TableGraphNode(0, true, 4, "-1", 11);
		this.nodes[11] = new TableGraphNode(0, true, 5, "IT_FLAG", 12);
		this.nodes[12] = new TableGraphNode(0, true, 7, "-1", 13);
		this.nodes[13] = new TableGraphNode(0, true, 4, "-1", 14);
		this.nodes[14] = new TableGraphNode(0, true, 5, "IT_NAME", 15);
		this.nodes[15] = new TableGraphNode(0, true, 8, "-1", 0);
		this.nodes[16] = new TableGraphNode(0, true, 1, "-1", 17);
		this.nodes[17] = new TableGraphNode(0, true, 2, "-1", 18);
		this.nodes[18] = new TableGraphNode(20, true, 9, "-1", 19);
		this.nodes[19] = new TableGraphNode(0, true, 4, "-1", 21);
		this.nodes[20] = new TableGraphNode(0, true, 8, "TG_EMPTY", 0);
		this.nodes[21] = new TableGraphNode(0, true, 5, "TG_ALT", 22);
		this.nodes[22] = new TableGraphNode(0, true, 10, "-1", 23);
		this.nodes[23] = new TableGraphNode(0, true, 4, "-1", 24);
		this.nodes[24] = new TableGraphNode(0, true, 5, "TG_ISTERM", 25);
		this.nodes[25] = new TableGraphNode(0, true, 11, "-1", 26);
		this.nodes[26] = new TableGraphNode(0, true, 4, "-1", 27);
		this.nodes[27] = new TableGraphNode(0, true, 5, "TG_NREF", 28);
		this.nodes[28] = new TableGraphNode(0, true, 12, "-1", 29);
		this.nodes[29] = new TableGraphNode(0, true, 4, "-1", 30);
		this.nodes[30] = new TableGraphNode(0, true, 5, "TG_SROUT", 31);
		this.nodes[31] = new TableGraphNode(0, true, 13, "-1", 32);
		this.nodes[32] = new TableGraphNode(0, true, 4, "-1", 33);
		this.nodes[33] = new TableGraphNode(0, true, 5, "TG_SUC", 34);
		this.nodes[34] = new TableGraphNode(0, true, 8, "-1", 0);
		this.nodes[35] = new TableGraphNode(0, true, 1, "-1", 36);
		this.nodes[36] = new TableGraphNode(0, true, 14, "-1", 37);
		this.nodes[37] = new TableGraphNode(0, true, 15, "-1", 38);
		this.nodes[38] = new TableGraphNode(0, true, 4, "-1", 39);
		this.nodes[39] = new TableGraphNode(0, true, 5, "TG_SIZE", 40);
		this.nodes[40] = new TableGraphNode(0, true, 16, "-1", 41);
		this.nodes[41] = new TableGraphNode(42, false, 5, "-1", 41);
		this.nodes[42] = new TableGraphNode(0, true, 0, "-1", 43);
		this.nodes[43] = new TableGraphNode(0, true, 17, "-1", 44);
		this.nodes[44] = new TableGraphNode(0, true, 14, "-1", 45);
		this.nodes[45] = new TableGraphNode(0, true, 16, "-1", 0);
		this.nodes[46] = new TableGraphNode(0, true, 1, "-1", 47);
		this.nodes[47] = new TableGraphNode(0, true, 18, "-1", 48);
		this.nodes[48] = new TableGraphNode(0, true, 16, "-1", 49);
		this.nodes[49] = new TableGraphNode(0, false, 6, "-1", 50);
		this.nodes[50] = new TableGraphNode(0, false, 7, "-1", 51);
		this.nodes[51] = new TableGraphNode(0, false, 8, "-1", 52);
		this.nodes[52] = new TableGraphNode(0, true, 17, "-1", 53);
		this.nodes[53] = new TableGraphNode(0, true, 18, "-1", 54);
		this.nodes[54] = new TableGraphNode(0, true, 16, "GGLL", 0);
		this.nodes[55] = new TableGraphNode(0, true, 1, "-1", 56);
		this.nodes[56] = new TableGraphNode(0, true, 19, "-1", 57);
		this.nodes[57] = new TableGraphNode(0, true, 15, "-1", 58);
		this.nodes[58] = new TableGraphNode(0, true, 4, "-1", 59);
		this.nodes[59] = new TableGraphNode(0, true, 5, "TE_SIZE", 60);
		this.nodes[60] = new TableGraphNode(0, true, 16, "-1", 61);
		this.nodes[61] = new TableGraphNode(62, false, 4, "-1", 61);
		this.nodes[62] = new TableGraphNode(0, true, 0, "-1", 63);
		this.nodes[63] = new TableGraphNode(0, true, 17, "-1", 64);
		this.nodes[64] = new TableGraphNode(0, true, 19, "-1", 65);
		this.nodes[65] = new TableGraphNode(0, true, 16, "-1", 0);
		this.nodes[66] = new TableGraphNode(0, true, 1, "-1", 67);
		this.nodes[67] = new TableGraphNode(0, true, 20, "-1", 68);
		this.nodes[68] = new TableGraphNode(0, true, 15, "-1", 69);
		this.nodes[69] = new TableGraphNode(0, true, 4, "-1", 70);
		this.nodes[70] = new TableGraphNode(0, true, 5, "NTE_SIZE", 71);
		this.nodes[71] = new TableGraphNode(0, true, 16, "-1", 72);
		this.nodes[72] = new TableGraphNode(73, false, 4, "-1", 72);
		this.nodes[73] = new TableGraphNode(0, true, 0, "-1", 74);
		this.nodes[74] = new TableGraphNode(0, true, 17, "-1", 75);
		this.nodes[75] = new TableGraphNode(0, true, 20, "-1", 76);
		this.nodes[76] = new TableGraphNode(0, true, 16, "-1", 0);
		this.nodes[77] = new TableGraphNode(0, true, 21, "-1", 78);
		this.nodes[78] = new TableGraphNode(0, true, 22, "-1", 79);
		this.nodes[79] = new TableGraphNode(0, true, 23, "-1", 80);
		this.nodes[80] = new TableGraphNode(0, true, 4, "-1", 81);
		this.nodes[81] = new TableGraphNode(0, true, 5, "-1", 82);
		this.nodes[82] = new TableGraphNode(0, true, 24, "-1", 83);
		this.nodes[83] = new TableGraphNode(0, true, 4, "-1", 84);
		this.nodes[84] = new TableGraphNode(0, true, 5, "-1", 85);
		this.nodes[85] = new TableGraphNode(0, true, 25, "-1", 0);

	}

	private void loadNTerminal()
	{
		this.nTerminal[1] = new TableNode("S1", "S", 1);
		this.nTerminal[2] = new TableNode("NTerminal1", "HEADER", 77);
		this.nTerminal[3] = new TableNode("NTerminal3", "GGLLNODE", 46);
		this.nTerminal[4] = new TableNode("LeftSide7", "ITEMNODE", 3);
		this.nTerminal[5] = new TableNode("LeftSide6", "ITEMGRAPH", 16);
		this.nTerminal[6] = new TableNode("LeftSide3", "TABLENODE", 35);
		this.nTerminal[7] = new TableNode("NTerminal5", "NTERMINAL", 66);
		this.nTerminal[8] = new TableNode("NTerminal6", "TERMINAL", 55);
	}

	private void loadTerminal()
	{
		this.terminal[1] = new TableNode("Terminal64", "<", -1);
		this.terminal[2] = new TableNode("Terminal65", "Item", -1);
		this.terminal[3] = new TableNode("Terminal66", "FirstNode", -1);
		this.terminal[4] = new TableNode("Terminal67", "=", -1);
		this.terminal[5] = new TableNode("Terminal68", "String", -1);
		this.terminal[6] = new TableNode("Terminal69", "Flag", -1);
		this.terminal[7] = new TableNode("Terminal72", "Name", -1);
		this.terminal[8] = new TableNode("Terminal75", "/>", -1);
		this.terminal[9] = new TableNode("Terminal30", "AlternativeIndex", -1);
		this.terminal[10] = new TableNode("Terminal33", "IsTerminal", -1);
		this.terminal[11] = new TableNode("Terminal36", "NodeReference", -1);
		this.terminal[12] = new TableNode("Terminal39", "SemanticRoutine", -1);
		this.terminal[13] = new TableNode("Terminal42", "SucessorIndex", -1);
		this.terminal[14] = new TableNode("Terminal20", "TableGraph", -1);
		this.terminal[15] = new TableNode("Terminal21", "size", -1);
		this.terminal[16] = new TableNode("Terminal24", ">", -1);
		this.terminal[17] = new TableNode("Terminal25", "</", -1);
		this.terminal[18] = new TableNode("Terminal11", "GGLL", -1);
		this.terminal[19] = new TableNode("Terminal56", "TerminalTable", -1);
		this.terminal[20] = new TableNode("Terminal48", "NTerminalTable", -1);
		this.terminal[21] = new TableNode("Terminal1", "<?", -1);
		this.terminal[22] = new TableNode("Terminal2", "xml", -1);
		this.terminal[23] = new TableNode("Terminal3", "version", -1);
		this.terminal[24] = new TableNode("Terminal6", "encoding", -1);
		this.terminal[25] = new TableNode("Terminal9", "?>", -1);
	}
}
