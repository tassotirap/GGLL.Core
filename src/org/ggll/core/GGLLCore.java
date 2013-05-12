package org.ggll.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StringReader;

import org.ggll.lexical.YyFactory;
import org.ggll.lexical.Yylex;
import org.ggll.semantics.SemanticRoutinesIvoker;
import org.ggll.syntax.analyzer.gsll1.Analyzer;
import org.ggll.syntax.model.TableGraphNode;
import org.ggll.syntax.model.TableNode;

public class GGLLCore
{

	Analyzer analyzer = null;
	Yylex yylex = null;

	public void init(String lexPath, String fileTabGraphNodes, String fileTnTerminalTab, String fileTerminalTab, String semanaticFile)
	{
		try
		{
			yylex = YyFactory.getYylex(lexPath, null, new StringReader(""));
			TableGraphNode[] tabGraphNodes = toFileTabGraphNodes(fileTabGraphNodes);
			TableNode[] nTerminalTab = toFileTnTerminalTab(fileTnTerminalTab);
			TableNode[] termialTab = toFileTerminalTab(fileTerminalTab);
			SemanticRoutinesIvoker semanticRoutinesIvoker = new SemanticRoutinesIvoker(new File(semanaticFile));
			semanticRoutinesIvoker.configureAndLoad();
			analyzer = new Analyzer(tabGraphNodes, termialTab, nTerminalTab, null, yylex);
		}
		catch (Exception e)
		{
			System.err.println("Could not create and run the analyzer");
			e.printStackTrace();
		}
	}

	public void analise(String source)
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
