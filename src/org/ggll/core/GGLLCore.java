package org.ggll.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StringReader;

import org.ggll.core.lexical.YyFactory;
import org.ggll.core.lexical.Yylex;
import org.ggll.core.syntax.analyzer.Analyzer;
import org.ggll.core.syntax.model.TableGraphNode;
import org.ggll.core.syntax.model.TableNode;

public class GGLLCore
{

	Analyzer analyzer = null;
	Yylex yylex = null;

	public void init(String lexPath, String fileTabGraphNodes, String fileTnTerminalTab, String fileTerminalTab, File semanaticFile, boolean debug)
	{
		try
		{
			yylex = YyFactory.getYylex(lexPath, null, new StringReader(""));
			TableGraphNode[] tabGraphNodes = toFileTabGraphNodes(fileTabGraphNodes);
			TableNode[] nTerminalTab = toFileTnTerminalTab(fileTnTerminalTab);
			TableNode[] termialTab = toFileTerminalTab(fileTerminalTab);
			analyzer = new Analyzer(tabGraphNodes, termialTab, nTerminalTab, null, yylex, semanaticFile, debug);			
			
		}
		catch (Exception e)
		{
			System.err.println("Could not create and run the analyzer");
			e.printStackTrace();
		}
	}

	public void run(String source)
	{
		try
		{
			yylex.yyreset(new StringReader(source));
			analyzer.run();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean next()
	{
		return analyzer.next();
	}

	private TableNode[] toFileTerminalTab(String file)
	{
		TableNode[] tableNode = null;
		try
		{

			try
			{
				FileInputStream fin = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fin);
				tableNode = (TableNode[]) ois.readObject();
				ois.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return tableNode;

	}

	private TableNode[] toFileTnTerminalTab(String file)
	{
		TableNode[] tableNode = null;
		try
		{

			try
			{
				FileInputStream fin = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fin);
				tableNode = (TableNode[]) ois.readObject();
				ois.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return tableNode;

	}

	private TableGraphNode[] toFileTabGraphNodes(String file)
	{
		TableGraphNode[] tableGraphNode = null;
		try
		{

			try
			{
				FileInputStream fin = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fin);
				tableGraphNode = (TableGraphNode[]) ois.readObject();
				ois.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return tableGraphNode;

	}

}
