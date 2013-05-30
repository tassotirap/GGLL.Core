package org.ggll.core;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.ggll.core.lexical.YyFactory;
import org.ggll.core.lexical.Yylex;
import org.ggll.core.syntax.parser.Parser;
import org.ggll.core.syntax.parser.ParserTable;

public class GGLLCore
{
	private Parser parser = null;
	private Yylex yylex = null;

	public ArrayList<String> getErrorList()
	{
		return parser.getErrorList();
	}

	public void init(String xmlAnalyzerTable, File lexPath, File semanaticFile, boolean debug)
	{
		try
		{
			ParserTable analyzerTable = ParserTable.deserialize(xmlAnalyzerTable);			
			yylex = YyFactory.getYylex(lexPath);
			parser = new Parser(analyzerTable, yylex, semanaticFile, debug);			
		}
		catch (Exception e)
		{
			System.err.println("Could not create and run the analyzer");
			e.printStackTrace();
		}
	}
	
	public boolean isSucess()
	{
		return parser.isSucess();
	}	
		
	public boolean next() throws Exception
	{
		return parser.next();
	}

	public void run(String source) throws Exception
	{
		try
		{
			yylex.yyreset(new StringReader(source));
			parser.run();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
