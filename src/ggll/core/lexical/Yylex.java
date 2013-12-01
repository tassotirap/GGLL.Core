package ggll.core.lexical;

import ggll.core.exceptions.LexicalException;
import ggll.core.syntax.model.TableNode;

public interface Yylex
{
	public abstract void pushback(int number);

	public abstract int searchTableNodeSymbol(String text);

	public abstract void setReader(java.io.Reader in);

	public abstract void TableNodes(TableNode TbT[]);

	public abstract void yybegin(int newState);

	public abstract char yycharat(int pos);

	public abstract void yyclose() throws java.io.IOException;

	public abstract int yylength();

	public abstract Yytoken yylex() throws java.io.IOException, LexicalException;

	public abstract void yyreset(java.io.Reader reader) throws java.io.IOException;

	public abstract int yystate();

	public abstract String yytext();

}