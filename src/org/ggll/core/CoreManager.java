package org.ggll.core;

import java.util.ArrayList;

public class CoreManager
{
	private static ArrayList<String> errorList = new ArrayList<String>();
	private static boolean sucess = true;
		
	public static ArrayList<String> getErrorList()
	{
		return errorList;
	}

	public static void setError(String error)
	{
		CoreManager.errorList.add(error);
	}	

	public static void clearErrors()
	{
		errorList.clear();		
	}

	public static boolean isSucess()
	{
		return sucess;
	}

	public static void setSucess(boolean sucess)
	{
		CoreManager.sucess = sucess;
	}
}
