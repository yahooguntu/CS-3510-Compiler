package parser.statement;

import java.io.BufferedWriter;
import java.io.IOException;

import lowlevel.*;
import parser.expression.*;

/**
 * Represents a while loop.
 */
public class IterationStatement extends Statement
{
	// the deciding expression
	private Expression compare;
	// the code to execute in the loop
	private Statement body;
	
	public IterationStatement(Expression compare, Statement body)
	{
		this.compare = compare;
		this.body = body;
	}
	
	public Expression getCompare()
	{
		return compare;
	}
	
	public Statement getBody()
	{
		return body;
	}
	
	public void print(int indent, BufferedWriter out) throws IOException
	{
		String prefix = "";
		for(int i = 0; i < indent; i++)
			prefix += "\t";
		
		out.write(prefix + "<IterationStatement>\n");
		out.write(prefix + "\t<Expression>\n");
		compare.print(indent+2, out);
		out.write(prefix + "\t</Expression>\n");
		out.write(prefix + "\t<Do>\n");
		body.print(indent+2, out);
		out.write(prefix + "\t</Do>\n");
		out.write(prefix + "</IterationStatement>\n");
	}

	/* (non-Javadoc)
	 * @see parser.statement.Statement#genLLCode(lowlevel.Function)
	 */
	@Override
	public CodeItem genLLCode(Function parent) {
		// TODO Auto-generated method stub
		return null;
	}
}
