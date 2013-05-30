package org.ggll.core.syntax.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.ggll.core.syntax.model.TableGraphNode;
import org.ggll.core.syntax.model.TableNode;

@XmlRootElement
public class ParserTable implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TableGraphNode graphNodesTable[];

	private TableNode nTerminalTable[];

	private TableNode termialTable[];

	public ParserTable(TableGraphNode tabGraphNodes[], TableNode nTerminalTab[], TableNode termialTab[])
	{
		this.graphNodesTable = tabGraphNodes;
		this.nTerminalTable = nTerminalTab;
		this.termialTable = termialTab;
	}

	public static ParserTable deserialize(File file)
	{
		ParserTable analyzerTable = null;
		try
		{
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			analyzerTable = (ParserTable) in.readObject();
			in.close();
			fileIn.close();
		}
		catch (IOException i)
		{
			i.printStackTrace();
		}
		catch (ClassNotFoundException c)
		{
			System.out.println("Employee class not found");
			c.printStackTrace();
		}
		return analyzerTable;
	}

	public static ParserTable deserialize(String fileName)
	{
		ParserTable analyzerTable = null;
		try
		{
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			analyzerTable = (ParserTable) in.readObject();
			in.close();
			fileIn.close();
		}
		catch (IOException i)
		{
			i.printStackTrace();
		}
		catch (ClassNotFoundException c)
		{
			System.out.println("Employee class not found");
			c.printStackTrace();
		}
		return analyzerTable;
	}

	public TableGraphNode getGraphNode(int index)
	{
		if (index < graphNodesTable.length)
		{
			return graphNodesTable[index];
		}
		return null;
	}

	public TableNode getNTerminal(int index)
	{
		if (index < nTerminalTable.length)
		{
			return nTerminalTable[index];
		}
		return null;
	}

	public TableNode getTermial(int index)
	{
		if (index < termialTable.length)
		{
			return termialTable[index];
		}
		return null;
	}

	public TableNode[] getTermialTable()
	{
		return termialTable;
	}

	public void serialize(String fileName)
	{
		try
		{
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
		}
		catch (IOException i)
		{
			i.printStackTrace();
		}
	}

	public void setGraphNode(int index, TableGraphNode value)
	{
		if (index < graphNodesTable.length)
		{
			graphNodesTable[index] = value;
		}
	}

	public void setNTermial(int index, TableNode value)
	{
		if (index < nTerminalTable.length)
		{
			nTerminalTable[index] = value;
		}
	}
	
	public void setTermial(int index, TableNode value)
	{
		if (index < termialTable.length)
		{
			termialTable[index] = value;
		}
	}
}
