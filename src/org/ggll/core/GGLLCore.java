package org.ggll.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StringReader;

import org.ggll.core.lexical.YyFactory;
import org.ggll.core.lexical.Yylex;
import org.ggll.core.syntax.analyzer.Analyzer;
import org.ggll.core.syntax.analyzer.AnalyzerTable;
import org.ggll.core.syntax.model.TableGraphNode;
import org.ggll.core.syntax.model.TableNode;

public class GGLLCore
{
	private AnalyzerTable analyzerTable;
	
	
	Analyzer analyzer = null;
	Yylex yylex = null;

	public void init(String xmlAnalyzerTable, String lexPath, File semanaticFile, boolean debug)
	{
		try
		{
			analyzerTable = AnalyzerTable.deserialize(xmlAnalyzerTable);			
			yylex = YyFactory.getYylex(lexPath, null, new StringReader(""));
			analyzer = new Analyzer(analyzerTable, null, yylex, semanaticFile, debug);			
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
}
