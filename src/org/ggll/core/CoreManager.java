package org.ggll.core;

import java.io.File;
import java.util.ArrayList;

public class CoreManager
{
	private static File semanticFile;	
	private static File lexFile;	
	private static File sintaticaFile;	

	public static File getSemanticFile()
	{
		return semanticFile;
	}

	public static void setSemanticFile(File semanticFile)
	{
		CoreManager.semanticFile = semanticFile;
	}

	public static File getLexFile()
	{
		return lexFile;
	}

	public static void setLexFile(File lexFile)
	{
		CoreManager.lexFile = lexFile;
	}

	public static File getSintaticaFile()
	{
		return sintaticaFile;
	}

	public static void setSintaticaFile(File sintaticaFile)
	{
		CoreManager.sintaticaFile = sintaticaFile;
	}
	
	public static ArrayList<String> getErrorList()
	{
		return errorList;
	}

	public static void setError(String error)
	{
		CoreManager.errorList.add(error);
	}

	private static ArrayList<String> errorList = new ArrayList<String>();

	public static void clearErrors()
	{
		errorList.clear();		
	}
}
