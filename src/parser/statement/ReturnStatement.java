package parser.statement;

import java.io.BufferedWriter;
import java.io.IOException;

import lowlevel.*;
import parser.expression.*;

/**
 * Represents a return from a function.
 */
public class ReturnStatement extends Statement
{
	// optional expression to evaluate and return
	private Expression body;
	
	public ReturnStatement(Expression body)
	{
		this.body = body;		
	}
	
	public Expression getBody()
	{
		return body;
	}
	
	public void print(int indent, BufferedWriter out) throws IOException
	{
		String prefix = "";
		for(int i = 0; i < indent; i++)
			prefix += "\t";
		
		out.write(prefix + "<ReturnStatement>");
		if (body != null)
		{
			out.write("\n" + prefix + "\t<Expression>\n");
			body.print(indent+2, out);
			out.write(prefix + "\t</Expression>\n" + prefix);
		}
		out.write("</ReturnStatement>\n");
	}

	/* (non-Javadoc)
	 * @see parser.statement.Statement#genLLCode(lowlevel.Function)
	 */
	@Override
	public void genLLCode(Function parent) {
		// TODO Auto-generated method stub
		// Seth
	}
}
