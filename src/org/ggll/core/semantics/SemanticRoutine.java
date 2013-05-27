package org.ggll.core.semantics;

import org.ggll.core.lexical.Yytoken;
import org.ggll.core.syntax.model.ParseStack;

public abstract class SemanticRoutine
{
	protected Yytoken currentToken;
	protected ParseStack parseStack;
}
