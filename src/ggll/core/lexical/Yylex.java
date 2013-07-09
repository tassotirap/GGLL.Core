package ggll.core.lexical;

import ggll.core.syntax.model.TableNode;

public interface Yylex
{
	public abstract void pushback(int number);

	public abstract int serchTabTSymbol(String text);

	public abstract void setReader(java.io.Reader in);

	public abstract void TabT(TableNode TbT[]);

	public abstract void yybegin(int newState);

	public abstract char yycharat(int pos);

	public abstract void yyclose() throws java.io.IOException;

	public abstract int yylength();

	public abstract Yytoken yylex() throws java.io.IOException;

	public abstract void yyreset(java.io.Reader reader) throws java.io.IOException;

	public abstract int yystate();

	public abstract String yytext();

}