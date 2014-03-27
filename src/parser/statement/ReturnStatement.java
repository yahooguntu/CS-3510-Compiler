package parser.statement;

import java.io.BufferedWriter;
import java.io.IOException;

import parser.expression.*;

public class ReturnStatement extends Statement{

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
		
		out.write(prefix + "<ReturnStatemnt>\n");
		out.write(prefix + "\t<Expression>\n");
		body.print(indent+2, out);
		out.write(prefix + "\t</Expression>\n");
		out.write(prefix + "</ReturnStatement>\n");
	}
}
