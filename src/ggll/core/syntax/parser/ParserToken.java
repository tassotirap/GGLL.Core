package ggll.core.syntax.parser;

import ggll.core.lexical.Yylex;
import ggll.core.lexical.Yytoken;

import java.io.IOException;


public class ParserToken
{	
	private Yytoken currentToken;
	
	private String currentSemanticSymbol;
	private String lastSemanticSymbol;
	private String currentSymbol;
	private String lastSymbol;	
	private Yylex yylex;

	private Yytoken lastToken;
	
	public ParserToken(Yylex yylex)
	{
		this.yylex = yylex;
	}
	
	private void setLastToken(Yytoken token)
	{
		this.lastToken = token;		
	}

	public String getCurrentSemanticSymbol()
	{
		return currentSemanticSymbol;
	}

	public String getCurrentSymbol()
	{
		return currentSymbol;
	}

	public Yytoken getCurrentToken()
	{
		return currentToken;
	}

	public String getLastSemanticSymbol()
	{
		return lastSemanticSymbol;
	}

	public String getLastSymbol()
	{
		return lastSymbol;
	}

	public Yytoken getLastToken()
	{
		return lastToken;
	}

	public Yylex getYylex()
	{
		return yylex;
	}

	public void readNext()
	{
		try
		{
			setLastToken(getCurrentToken());			
			setLastSymbol(getCurrentSymbol());
			setLastSemanticSymbol(getCurrentSemanticSymbol());
			
			setCurrentToken(getYylex().yylex());
			
			if (getCurrentToken().type.equals("Res") || getCurrentToken().type.equals("Esp") || getCurrentToken().type.equals("EOF"))
			{
				setCurrentSymbol(getCurrentToken().text);
			}
			else
			{
				setCurrentSymbol(getCurrentToken().type);
			}
			
			setCurrentSemanticSymbol(getCurrentToken().text);
		}
		catch (IOException e)
		{
		}
	}

	public void setCurrentSemanticSymbol(String currentSemanticSymbol)
	{
		this.currentSemanticSymbol = currentSemanticSymbol;
	}

	public void setCurrentSymbol(String currentSymbol)
	{
		this.currentSymbol = currentSymbol;
	}

	public void setCurrentToken(Yytoken currentToken)
	{
		this.currentToken = currentToken;
	}

	public void setLastSemanticSymbol(String lastSemanticSymbol)
	{
		this.lastSemanticSymbol = lastSemanticSymbol;
	}
	
	public void setLastSymbol(String lastSymbol)
	{
		this.lastSymbol = lastSymbol;
	}

}
