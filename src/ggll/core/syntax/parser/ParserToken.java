package ggll.core.syntax.parser;

import ggll.core.exceptions.LexicalException;
import ggll.core.lexical.Yylex;
import ggll.core.lexical.Yytoken;

import java.io.IOException;

import com.rits.cloning.Cloner;
import com.rits.cloning.ObjenesisInstantiationStrategy;

public class ParserToken
{
	private Yytoken currentToken;
	
	private String currentSemanticSymbol;
	private String lastSemanticSymbol;
	private String currentSymbol;
	private String lastSymbol;
	private final Yylex yylex;
	
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
		return this.currentSemanticSymbol;
	}
	
	public String getCurrentSymbol()
	{
		return this.currentSymbol;
	}
	
	public Yytoken getCurrentToken()
	{
		return this.currentToken;
	}
	
	public String getLastSemanticSymbol()
	{
		return this.lastSemanticSymbol;
	}
	
	public String getLastSymbol()
	{
		return this.lastSymbol;
	}
	
	public Yytoken getLastToken()
	{
		return this.lastToken;
	}
	
	public Yylex getYylex()
	{
		return this.yylex;
	}
	
	public void readNext() throws LexicalException
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
		catch (final IOException e)
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
	
	public ParserToken clone()
	{
		Cloner cloner = new Cloner(new ObjenesisInstantiationStrategy());
		ParserToken clone = cloner.deepClone(this);
		return clone;		
	}	
}
