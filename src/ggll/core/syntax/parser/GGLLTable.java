package ggll.core.syntax.parser;

import ggll.core.syntax.model.TableGraphNode;
import ggll.core.syntax.model.TableNode;
import ggll.core.xml.GGLLTableParser;
import ggll.core.xml.XmlSemanticFile;
import ggll.core.xml.XmlLexicalAnaliser;

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

	private final TableGraphNode nodes[];

	private final TableNode nTerminal[];

	private final TableNode termial[];

	public GGLLTable(TableGraphNode tabGraphNodes[], TableNode nTerminalTab[], TableNode termialTab[])
	{
		this.nodes = tabGraphNodes;
		this.nTerminal = nTerminalTab;
		this.termial = termialTab;
	}

	private static String ReadFile(File file)
	{
		BufferedReader br = null;
		String tmp = "";
		String fileContent = "";
		try
		{
			final FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			tmp = br.readLine();
			while (tmp != null)
			{

				fileContent = fileContent + tmp;
				tmp = br.readLine();
			}
			br.close();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return fileContent;
	}

	public static GGLLTable deserialize(File file) throws Exception
	{
		final XmlLexicalAnaliser yylex = new XmlLexicalAnaliser();
		final GGLLTable ggllTable = new GGLLTableParser().ggllTable;
		final XmlSemanticFile xmlSemanticFile = new ggll.core.xml.XmlSemanticFile();

		final Parser parser = new Parser(ggllTable, yylex, xmlSemanticFile, false);
		yylex.yyreset(new StringReader(ReadFile(file)));
		parser.run();
		if (parser.isSucess())
		{
			return xmlSemanticFile.ggllTable;
		}
		return null;
	}

	public static GGLLTable deserialize(String file) throws Exception
	{
		return deserialize(new File(file));
	}

	public TableGraphNode getGraphNode(int index)
	{
		if (index < this.nodes.length)
		{
			return this.nodes[index];
		}
		return null;
	}

	public TableNode getNTerminal(int index)
	{
		if (index < this.nTerminal.length)
		{
			return this.nTerminal[index];
		}
		return null;
	}

	public TableNode getTermial(int index)
	{
		if (index < this.termial.length)
		{
			return this.termial[index];
		}
		return null;
	}

	public TableNode[] getTermials()
	{
		return this.termial;
	}

	public void serialize(String fileName)
	{
		final StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
		xml.append("<GGLL>\n");
		{
			xml.append("\t<TableGraph size=\"" + this.nodes.length + "\">\n");
			for (final TableGraphNode node : this.nodes)
			{
				if (node == null)
				{
					xml.append("\t\t<Item />\n");
				}
				else
				{

					xml.append("\t\t<Item ");
					xml.append("AlternativeIndex=\"" + node.getAlternativeIndex() + "\" ");
					xml.append("IsTerminal=\"" + node.IsTerminal() + "\" ");
					xml.append("NodeReference=\"" + node.getNodeReference() + "\" ");
					xml.append("SemanticRoutine=\"" + node.getSemanticRoutine() + "\" ");
					xml.append("SucessorIndex=\"" + node.getSucessorIndex() + "\" ");
					xml.append("/>\n");
				}
			}
			xml.append("\t</TableGraph>\n");
		}

		{
			xml.append("\t<NTerminalTable size=\"" + this.nTerminal.length + "\">\n");
			for (final TableNode element : this.nTerminal)
			{
				if (element == null)
				{
					xml.append("\t\t<Item />\n");
				}
				else
				{
					xml.append("\t\t<Item ");
					xml.append("FirstNode=\"" + element.getFirstNode() + "\" ");
					xml.append("Flag=\"" + element.getFlag() + "\" ");
					xml.append("Name=\"" + element.getName() + "\" ");
					xml.append("/>\n");
				}
			}
			xml.append("\t</NTerminalTable>\n");
		}

		{
			xml.append("\t<TerminalTable size=\"" + this.termial.length + "\">\n");
			for (final TableNode element : this.termial)
			{
				if (element == null)
				{
					xml.append("\t\t<Item />\n");
				}
				else
				{
					xml.append("\t\t<Item ");
					xml.append("FirstNode=\"" + element.getFirstNode() + "\" ");
					xml.append("Flag=\"" + element.getFlag() + "\" ");
					xml.append("Name=\"" + element.getName() + "\" ");
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
			final OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
			out.write(xml.toString());
			out.close();
			fos.close();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

	}

	public void setGraphNode(int index, TableGraphNode value)
	{
		if (index < this.nodes.length)
		{
			this.nodes[index] = value;
		}
	}

	public void setNTermial(int index, TableNode value)
	{
		if (index < this.nTerminal.length)
		{
			this.nTerminal[index] = value;
		}
	}

	public void setTermial(int index, TableNode value)
	{
		if (index < this.termial.length)
		{
			this.termial[index] = value;
		}
	}
}
