package ggll.core.xml;

import ggll.core.syntax.model.TableGraphNode;
import ggll.core.syntax.model.TableNode;
import ggll.core.syntax.parser.GGLLTable;

public class GGLLTableParser
{
	private TableGraphNode[] nodes;
	private TableNode[] nTerminal;
	private TableNode[] terminal;
	
	public GGLLTable ggllTable;

	public GGLLTableParser()
	{
		nodes = new TableGraphNode[93];
		nTerminal = new TableNode[93];
		terminal = new TableNode[93];
		loadNodes();
		loadNTerminal();
		loadTerminal();
		ggllTable = new GGLLTable(nodes, nTerminal, terminal);
	}

	private void loadNodes()
	{
		nodes[1] = new TableGraphNode(0, false, 2, "-1", 2);
		nodes[2] = new TableGraphNode(0, false, 3, "-1", 0);
		nodes[3] = new TableGraphNode(0, true, 1, "-1", 4);
		nodes[4] = new TableGraphNode(0, true, 2, "-1", 5);
		nodes[5] = new TableGraphNode(0, true, 3, "-1", 6);
		nodes[6] = new TableGraphNode(0, true, 4, "-1", 7);
		nodes[7] = new TableGraphNode(0, true, 5, "TE_SIZE", 8);
		nodes[8] = new TableGraphNode(0, true, 6, "-1", 9);
		nodes[9] = new TableGraphNode(10, false, 5, "-1", 9);
		nodes[10] = new TableGraphNode(0, true, 7, "-1", 11);
		nodes[11] = new TableGraphNode(0, true, 2, "-1", 12);
		nodes[12] = new TableGraphNode(0, true, 6, "-1", 0);
		nodes[13] = new TableGraphNode(0, true, 1, "-1", 14);
		nodes[14] = new TableGraphNode(0, true, 8, "-1", 15);
		nodes[15] = new TableGraphNode(16, true, 9, "IT_EMPTY", 0);
		nodes[16] = new TableGraphNode(0, true, 10, "-1", 17);
		nodes[17] = new TableGraphNode(0, true, 4, "-1", 18);
		nodes[18] = new TableGraphNode(0, true, 5, "IT_FNO", 19);
		nodes[19] = new TableGraphNode(0, true, 11, "-1", 20);
		nodes[20] = new TableGraphNode(0, true, 4, "-1", 21);
		nodes[21] = new TableGraphNode(0, true, 5, "IT_FLAG", 22);
		nodes[22] = new TableGraphNode(0, true, 12, "-1", 23);
		nodes[23] = new TableGraphNode(0, true, 4, "-1", 24);
		nodes[24] = new TableGraphNode(0, true, 5, "IT_NAME", 25);
		nodes[25] = new TableGraphNode(0, true, 9, "-1", 0);
		nodes[26] = new TableGraphNode(0, true, 1, "-1", 27);
		nodes[27] = new TableGraphNode(0, true, 13, "-1", 28);
		nodes[28] = new TableGraphNode(0, true, 3, "-1", 29);
		nodes[29] = new TableGraphNode(0, true, 4, "-1", 30);
		nodes[30] = new TableGraphNode(0, true, 5, "TG_SIZE", 31);
		nodes[31] = new TableGraphNode(0, true, 6, "-1", 32);
		nodes[32] = new TableGraphNode(33, false, 7, "-1", 32);
		nodes[33] = new TableGraphNode(0, true, 7, "-1", 34);
		nodes[34] = new TableGraphNode(0, true, 13, "-1", 35);
		nodes[35] = new TableGraphNode(0, true, 6, "-1", 0);
		nodes[36] = new TableGraphNode(0, true, 1, "-1", 37);
		nodes[37] = new TableGraphNode(0, true, 14, "-1", 38);
		nodes[38] = new TableGraphNode(0, true, 6, "-1", 39);
		nodes[39] = new TableGraphNode(0, false, 6, "-1", 40);
		nodes[40] = new TableGraphNode(0, false, 8, "-1", 41);
		nodes[41] = new TableGraphNode(0, false, 4, "-1", 42);
		nodes[42] = new TableGraphNode(0, true, 7, "-1", 43);
		nodes[43] = new TableGraphNode(0, true, 14, "GGLL", 44);
		nodes[44] = new TableGraphNode(0, true, 6, "-1", 0);
		nodes[45] = new TableGraphNode(0, true, 1, "-1", 46);
		nodes[46] = new TableGraphNode(0, true, 15, "-1", 47);
		nodes[47] = new TableGraphNode(0, true, 3, "-1", 48);
		nodes[48] = new TableGraphNode(0, true, 4, "-1", 49);
		nodes[49] = new TableGraphNode(0, true, 5, "NTE_SIZE", 50);
		nodes[50] = new TableGraphNode(0, true, 6, "-1", 51);
		nodes[51] = new TableGraphNode(52, false, 5, "-1", 51);
		nodes[52] = new TableGraphNode(0, true, 7, "-1", 53);
		nodes[53] = new TableGraphNode(0, true, 15, "-1", 54);
		nodes[54] = new TableGraphNode(0, true, 6, "-1", 0);
		nodes[55] = new TableGraphNode(0, true, 1, "-1", 56);
		nodes[56] = new TableGraphNode(0, true, 8, "-1", 57);
		nodes[57] = new TableGraphNode(58, true, 9, "TG_EMPTY", 0);
		nodes[58] = new TableGraphNode(0, true, 16, "-1", 59);
		nodes[59] = new TableGraphNode(0, true, 4, "-1", 60);
		nodes[60] = new TableGraphNode(0, true, 5, "TG_ALT", 61);
		nodes[61] = new TableGraphNode(0, true, 17, "-1", 62);
		nodes[62] = new TableGraphNode(0, true, 4, "-1", 63);
		nodes[63] = new TableGraphNode(0, true, 5, "TG_ISTERM", 64);
		nodes[64] = new TableGraphNode(0, true, 18, "-1", 65);
		nodes[65] = new TableGraphNode(0, true, 4, "-1", 66);
		nodes[66] = new TableGraphNode(0, true, 5, "TG_NREF", 67);
		nodes[67] = new TableGraphNode(0, true, 19, "-1", 68);
		nodes[68] = new TableGraphNode(0, true, 4, "-1", 69);
		nodes[69] = new TableGraphNode(0, true, 5, "TG_SROUT", 70);
		nodes[70] = new TableGraphNode(0, true, 20, "-1", 71);
		nodes[71] = new TableGraphNode(0, true, 4, "-1", 72);
		nodes[72] = new TableGraphNode(0, true, 5, "TG_SUC", 73);
		nodes[73] = new TableGraphNode(0, true, 9, "-1", 0);
		nodes[74] = new TableGraphNode(0, true, 1, "-1", 75);
		nodes[75] = new TableGraphNode(0, true, 21, "-1", 76);
		nodes[76] = new TableGraphNode(0, true, 22, "-1", 77);
		nodes[77] = new TableGraphNode(0, true, 23, "-1", 78);
		nodes[78] = new TableGraphNode(0, true, 4, "-1", 79);
		nodes[79] = new TableGraphNode(0, true, 5, "-1", 80);
		nodes[80] = new TableGraphNode(0, true, 24, "-1", 81);
		nodes[81] = new TableGraphNode(0, true, 4, "-1", 82);
		nodes[82] = new TableGraphNode(0, true, 5, "-1", 83);
		nodes[83] = new TableGraphNode(0, true, 21, "-1", 84);
		nodes[84] = new TableGraphNode(0, true, 6, "-1", 0);

	}

	private void loadNTerminal()
	{
		nTerminal[1] = new TableNode("S1", "S1", 1);
		nTerminal[2] = new TableNode("NTerminal2", "HEADER", 74);
		nTerminal[3] = new TableNode("NTerminal3", "GGLLNODE", 36);
		nTerminal[4] = new TableNode("LeftSide7", "TERMINAL", 3);
		nTerminal[5] = new TableNode("NTerminal10", "ITEM", 13);
		nTerminal[6] = new TableNode("LeftSide3", "TABLEGRAPH", 26);
		nTerminal[7] = new TableNode("NTerminal6", "ITEM_TG", 55);
		nTerminal[8] = new TableNode("NTerminal9", "NTERMINAL", 45);
	}

	private void loadTerminal()
	{
		terminal[1] = new TableNode("Terminal73", "<", -1);
		terminal[2] = new TableNode("Terminal74", "TerminalTable", -1);
		terminal[3] = new TableNode("Terminal75", "size", -1);
		terminal[4] = new TableNode("Terminal76", "=", -1);
		terminal[5] = new TableNode("Terminal77", "String", -1);
		terminal[6] = new TableNode("Terminal78", ">", -1);
		terminal[7] = new TableNode("Terminal79", "</", -1);
		terminal[8] = new TableNode("Terminal61", "Item", -1);
		terminal[9] = new TableNode("Terminal62", "/>", -1);
		terminal[10] = new TableNode("Terminal63", "FirstNode", -1);
		terminal[11] = new TableNode("Terminal66", "Flag", -1);
		terminal[12] = new TableNode("Terminal69", "Name", -1);
		terminal[13] = new TableNode("Terminal20", "TableGraph", -1);
		terminal[14] = new TableNode("Terminal2", "GGLL", -1);
		terminal[15] = new TableNode("Terminal52", "NTerminalTable", -1);
		terminal[16] = new TableNode("Terminal34", "AlternativeIndex", -1);
		terminal[17] = new TableNode("Terminal37", "IsTerminal", -1);
		terminal[18] = new TableNode("Terminal40", "NodeReference", -1);
		terminal[19] = new TableNode("Terminal43", "SemanticRoutine", -1);
		terminal[20] = new TableNode("Terminal46", "SucessorIndex", -1);
		terminal[21] = new TableNode("Terminal9", "?", -1);
		terminal[22] = new TableNode("Terminal10", "xml", -1);
		terminal[23] = new TableNode("Terminal4", "version", -1);
		terminal[24] = new TableNode("Terminal5", "encoding", -1);
	}
}
