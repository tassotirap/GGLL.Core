package ggll.core.syntax.model;

public class ParseNode
{
	private final String flag;

	private Object semanticSymbol;

	private String type;

	public ParseNode(String flag, String str)
	{
		this(flag, str, null);
	}

	public ParseNode(String flag, String type, Object semanticSymbol)
	{
		this.flag = flag;
		this.type = type;
		this.semanticSymbol = semanticSymbol;
	}

	public String getFlag()
	{
		return this.flag;
	}

	public Object getSemanticSymbol()
	{
		return this.semanticSymbol;
	}

	public String getType()
	{
		return this.type;
	}


	public void setSemanticSymbol(Object semanticSymbol)
	{
		this.semanticSymbol = semanticSymbol;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	@Override
	public String toString()
	{
		return "Syn: " + getType() + " Sem: " + getSemanticSymbol();
	}

}