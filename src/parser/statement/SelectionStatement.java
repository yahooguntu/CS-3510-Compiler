package parser.statement;

import java.io.BufferedWriter;
import java.io.IOException;

import parser.statement.*;
import parser.expression.*;

/**
 * Represents an if statement.
 */
public class SelectionStatement extends Statement
{
	// the decision expession
	private Expression compare;
	// the body to be executed if the expression is 1
	private Statement body;
	// the optional else statement
	private Statement else_part;
	
	public SelectionStatement(Expression compare, Statement body, Statement else_part)
	{
		this.compare = compare;
		this.body = body;
		this.else_part = else_part;
	}
	
	public Expression getCompare() {
		return compare;
	}
	
	public Statement getBody() {
		return body;
	}

	public Statement getElse_part() {
		return else_part;
	}

	public void print(int indent, BufferedWriter out) throws IOException 
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		out.write(prefix + "<SelectionStatement>\n");
		
		out.write(prefix + "\t<Expression>\n");
		compare.print(indent+2, out);
		out.write(prefix + "\t</Expression>\n");
		
		out.write(prefix + "\t<Then>\n");
		body.print(indent+2, out);
		out.write(prefix + "\t</Then>\n");
		
		if(else_part != null)
		{
			out.write(prefix + "\t<Else>\n");
			else_part.print(indent+2, out);
			out.write(prefix + "\t</Else>\n");
		}
		
		out.write(prefix + "</SelectionStatement>\n");		
	}

}
