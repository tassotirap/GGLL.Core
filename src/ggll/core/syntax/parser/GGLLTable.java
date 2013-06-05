package ggll.core.syntax.parser;

import ggll.core.syntax.model.TableGraphNode;
import ggll.core.syntax.model.TableNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	private static String readFile(File file) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(file));
		try
		{
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null)
			{
				sb.append(line);
				line = br.readLine();
			}
			return sb.toString();
		}
		finally
		{
			br.close();
		}
	}

	public static GGLLTable deserialize(File file) throws Exception
	{
		TableGraphNode tableGraphNode[] = null;
		TableNode nTerminalTableNode[] = null;
		TableNode terminalTableNode[] = null;

		BufferedReader in = new BufferedReader(new FileReader(file));
		String fileString = readFile(file);

		Pattern pattern = Pattern.compile("<TableGraph size=\"(.*?)\">(.*?)</TableGraph>");
		Matcher matcher = pattern.matcher(fileString);

		if (matcher.find())
		{
			int size = Integer.parseInt(matcher.group(1));
			tableGraphNode = new TableGraphNode[size];

			pattern = Pattern.compile("<Item.*?/>");
			matcher = pattern.matcher(matcher.group(2));
			int i = 0;
			while (matcher.find())
			{
				Pattern ItemPattern = Pattern.compile("AlternativeIndex=\"(.*?)\" IsTerminal=\"(.*?)\" NodeReference=\"(.*?)\" SemanticRoutine=\"(.*?)\" SucessorIndex=\"(.*?)\" />");
				Matcher ItemMatcher = pattern.matcher(matcher.group(0));
				ItemMatcher = ItemPattern.matcher(matcher.group(0)); 
				if (ItemMatcher.find())
				{
					tableGraphNode[i] = new TableGraphNode();
					tableGraphNode[i].setAlternativeIndex(Integer.parseInt(ItemMatcher.group(1)));
					tableGraphNode[i].setIsTerminal(Boolean.parseBoolean(ItemMatcher.group(2)));
					tableGraphNode[i].setNodeReference(Integer.parseInt(ItemMatcher.group(3)));
					tableGraphNode[i].setSemanticRoutine(ItemMatcher.group(4));
					tableGraphNode[i].setSucessorIndex(Integer.parseInt(ItemMatcher.group(5)));
				}
				i++;
			}
		}
		
		
		pattern = Pattern.compile("<NTerminalTable size=\"(.*?)\">(.*?)</NTerminalTable>");
		matcher = pattern.matcher(fileString);

		if (matcher.find())
		{
			int size = Integer.parseInt(matcher.group(1));
			nTerminalTableNode = new TableNode[size];

			pattern = Pattern.compile("<Item.*?/>");
			matcher = pattern.matcher(matcher.group(2));
			int i = 0;
			while (matcher.find())
			{
				Pattern ItemPattern = Pattern.compile("<Item FirstNode=\"(.*?)\" Flag=\"(.*?)\" Name=\"(.*?)\" />");
				Matcher ItemMatcher = pattern.matcher(matcher.group(0));
				ItemMatcher = ItemPattern.matcher(matcher.group(0)); 
				if (ItemMatcher.find())
				{
					nTerminalTableNode[i] = new TableNode();
					nTerminalTableNode[i].setFirstNode(Integer.parseInt(ItemMatcher.group(1)));
					nTerminalTableNode[i].setFlag(ItemMatcher.group(2));
					nTerminalTableNode[i].setName(ItemMatcher.group(3));
				}
				i++;
			}
		}
		
		pattern = Pattern.compile("<TerminalTable size=\"(.*?)\">(.*?)</TerminalTable>");
		matcher = pattern.matcher(fileString);

		if (matcher.find())
		{
			int size = Integer.parseInt(matcher.group(1));
			terminalTableNode = new TableNode[size];

			pattern = Pattern.compile("<Item.*?/>");
			matcher = pattern.matcher(matcher.group(2));
			int i = 0;
			while (matcher.find())
			{
				Pattern ItemPattern = Pattern.compile("<Item FirstNode=\"(.*?)\" Flag=\"(.*?)\" Name=\"(.*?)\" />");
				Matcher ItemMatcher = pattern.matcher(matcher.group(0));
				ItemMatcher = ItemPattern.matcher(matcher.group(0)); 
				if (ItemMatcher.find())
				{
					terminalTableNode[i] = new TableNode();
					terminalTableNode[i].setFirstNode(Integer.parseInt(ItemMatcher.group(1)));
					terminalTableNode[i].setFlag(ItemMatcher.group(2));
					terminalTableNode[i].setName(ItemMatcher.group(3));
				}
				i++;
			}
		}

		GGLLTable analyzerTable = new GGLLTable(tableGraphNode, nTerminalTableNode, terminalTableNode);
		in.close();
		return analyzerTable;
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
		StringBuffer xml = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>\n");
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
