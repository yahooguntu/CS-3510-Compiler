package parser.statement;

import java.io.BufferedWriter;
import java.io.IOException;

import parser.expression.*;

public class IterationStatement extends Statement{

	private Expression compare;
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
		out.write(prefix + "\t<Then>\n");
		body.print(indent+2, out);
		out.write(prefix + "\t</Then>\n");
		out.write(prefix + "</IterationStatement>\n");
	}
}
