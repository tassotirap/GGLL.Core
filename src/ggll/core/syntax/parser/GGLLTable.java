package ggll.core.syntax.parser;

import ggll.core.syntax.model.TableGraphNode;
import ggll.core.syntax.model.TableNode;
import ggll.core.xml.GGLLTableParser;
import ggll.core.xml.XmlSemanticFile;
import ggll.core.xml.YylexSemanticFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringReader;

public class GGLLTable implements Serializable
{
	private static final long serialVersionUID = 1L;

	private TableGraphNode nodes[];

	private TableNode nTerminal[];

	private TableNode termial[];

	public GGLLTable(TableGraphNode tabGraphNodes[], TableNode nTerminalTab[], TableNode termialTab[])
	{
		this.nodes = tabGraphNodes;
		this.nTerminal = nTerminalTab;
		this.termial = termialTab;
	}

	public static GGLLTable deserialize(String file) throws Exception
	{
		return deserialize(new File(file));
	}

	public static GGLLTable deserialize(File file) throws Exception
	{
		YylexSemanticFile yylex = new YylexSemanticFile();
		GGLLTable ggllTable = new GGLLTableParser().ggllTable;
		XmlSemanticFile xmlSemanticFile = new ggll.core.xml.XmlSemanticFile();

		Parser parser = new Parser(ggllTable, yylex, xmlSemanticFile, false);
		yylex.yyreset(new StringReader(ReadFile(file)));
		parser.run();
		if (parser.isSucess())
		{
			return xmlSemanticFile.ggllTable;
		}
		return null;
	}

	private static String ReadFile(File file)
	{
		BufferedReader br = null;
		String tmp = "";
		String fileContent = "";
		try
		{
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			tmp = br.readLine();
			while (tmp != null)
			{

				fileContent = fileContent + tmp;
				tmp = br.readLine();
			}
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return fileContent;
	}

	public TableGraphNode getGraphNode(int index)
	{
		if (index < nodes.length)
		{
			return nodes[index];
		}
		return null;
	}

	public TableNode getNTerminal(int index)
	{
		if (index < nTerminal.length)
		{
			return nTerminal[index];
		}
		return null;
	}

	public TableNode getTermial(int index)
	{
		if (index < termial.length)
		{
			return termial[index];
		}
		return null;
	}

	public TableNode[] getTermials()
	{
		return termial;
	}

	public void serialize(String fileName)
	{
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
		xml.append("<GGLL>\n");
		{
			xml.append("\t<TableGraph size=\"" + nodes.length + "\">\n");
			for (int i = 0; i < nodes.length; i++)
			{
				if (nodes[i] == null)
				{
					xml.append("\t\t<Item />\n");
				}
				else
				{

					xml.append("\t\t<Item ");
					xml.append("AlternativeIndex=\"" + nodes[i].getAlternativeIndex() + "\" ");
					xml.append("IsTerminal=\"" + nodes[i].IsTerminal() + "\" ");
					xml.append("NodeReference=\"" + nodes[i].getNodeReference() + "\" ");
					xml.append("SemanticRoutine=\"" + nodes[i].getSemanticRoutine() + "\" ");
					xml.append("SucessorIndex=\"" + nodes[i].getSucessorIndex() + "\" ");
					xml.append("/>\n");
				}
			}
			xml.append("\t</TableGraph>\n");
		}

		{
			xml.append("\t<NTerminalTable size=\"" + nTerminal.length + "\">\n");
			for (int i = 0; i < nTerminal.length; i++)
			{
				if (nTerminal[i] == null)
				{
					xml.append("\t\t<Item />\n");
				}
				else
				{
					xml.append("\t\t<Item ");
					xml.append("FirstNode=\"" + nTerminal[i].getFirstNode() + "\" ");
					xml.append("Flag=\"" + nTerminal[i].getFlag() + "\" ");
					xml.append("Name=\"" + nTerminal[i].getName() + "\" ");
					xml.append("/>\n");
				}
			}
			xml.append("\t</NTerminalTable>\n");
		}

		{
			xml.append("\t<TerminalTable size=\"" + termial.length + "\">\n");
			for (int i = 0; i < termial.length; i++)
			{
				if (termial[i] == null)
				{
					xml.append("\t\t<Item />\n");
				}
				else
				{
					xml.append("\t\t<Item ");
					xml.append("FirstNode=\"" + termial[i].getFirstNode() + "\" ");
					xml.append("Flag=\"" + termial[i].getFlag() + "\" ");
					xml.append("Name=\"" + termial[i].getName() + "\" ");
					xml.append("/>\n");
				}
			}
			xml.append("\t</TerminalTable>\n");
		}

		xml.append("</GGLL>");

		FileOutputStream fos;
		try
		{
			fos = new FileOutputStream(fileName);
			OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
			out.write(xml.toString());
			out.close();
			fos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public void setGraphNode(int index, TableGraphNode value)
	{
		if (index < nodes.length)
		{
			nodes[index] = value;
		}
	}

	public void setNTermial(int index, TableNode value)
	{
		if (index < nTerminal.length)
		{
			nTerminal[index] = value;
		}
	}

	public void setTermial(int index, TableNode value)
	{
		if (index < termial.length)
		{
			termial[index] = value;
		}
	}
}
