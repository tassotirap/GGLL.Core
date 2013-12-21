package ggll.core.lexical;

public class Yytoken
{
	public int column;
	public int line;
	public String type;
	public String text;

	public Yytoken(String p1, String text, int line, int column)
	{
		this.type = p1;
		this.text = text;
		this.line = line;
		this.column = column;
	}

	public String token()
	{
		return this.text;
	}

	@Override
	public String toString()
	{
		return "Text: " + this.text + "   Type: " + this.type + "   line: " + this.line + "   column: " + this.column;
	}
}
