package ggll.core.semantics;

import ggll.core.lexical.Yytoken;
import ggll.core.syntax.model.ParseStack;

import java.util.ArrayList;


public abstract class SemanticRoutineClass
{
	protected Yytoken currentToken;
	protected ParseStack parseStack;
	protected ArrayList<String> errorList;
}
