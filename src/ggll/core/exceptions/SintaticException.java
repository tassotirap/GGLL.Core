package ggll.core.exceptions;

public class SintaticException extends Exception
{
	private static final long serialVersionUID = 1L;

	private int line;
	private int column;
	private String token;

	public SintaticException(String token, int line, int column)
	{
		this.token = token;
		this.line = line;
		this.column = column;
	}

	public int getLine()
	{
		return line;
	}

	public int getColumn()
	{
		return column;
	}

	public String getToken()
	{
		return token;
	}
}