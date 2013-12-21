package ggll.core.exceptions;

public class LexicalException extends Exception
{
	private static final long serialVersionUID = 1L;

	private final int line;
	private final int column;
	private final String token;

	public LexicalException(String token, int line, int column)
	{
		this.token = token;
		this.line = line;
		this.column = column;
	}

	public int getColumn()
	{
		return this.column;
	}

	public int getLine()
	{
		return this.line;
	}

	public String getToken()
	{
		return this.token;
	}
}
