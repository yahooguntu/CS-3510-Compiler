package parser.statement;

import java.io.BufferedWriter;
import java.io.IOException;

import lowlevel.*;
import parser.expression.*;

/**
 * Represents an expression statement.
 * e.g. 'func();'
 * or 'A + B;' (though I don't know why you'd want to do that)
 */
public class ExpressionStatement extends Statement
{
	// the expression
	private Expression data;
	
	public ExpressionStatement(Expression data)
	{
		this.data = data;
	}
	
	public Expression getData()
	{
		return data;
	}
	
	public void print(int indent, BufferedWriter out) throws IOException
	{
		String prefix = "";
		for(int i = 0; i < indent; i++)
			prefix += "\t";
		
		out.write(prefix + "<ExpressionStatement>\n");
		data.print(indent+1, out);
		out.write(prefix + "</ExpressionStatement>\n");
	}

	/* (non-Javadoc)
	 * @see parser.statement.Statement#genLLCode(lowlevel.Function)
	 */
	@Override
	public void genLLCode(Function parent) {
		// TODO Auto-generated method stub
	}
	
}
