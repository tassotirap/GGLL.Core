package ggll.core.syntax.error;

import ggll.core.exceptions.ErrorRecoveryException;
import ggll.core.exceptions.LexicalException;
import ggll.core.syntax.parser.Parser;

public class DeleteStrategy extends ErroStrategy
{
	public DeleteStrategy(Parser analyzer)
	{
		super(analyzer);
	}

	@Override
	protected int tryFix(int nextIndex, int column, int line) throws LexicalException
	{
		this.parserToken.readNext();
		if (validate(nextIndex))
		{

			this.parser.setError(new ErrorRecoveryException("Symbol \"" + this.parserToken.getLastToken().text + "\" was ignored."));
			return nextIndex;
		}
		return -1;
	}

}
