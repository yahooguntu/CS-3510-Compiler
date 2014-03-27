package parser.statement;

import java.io.BufferedWriter;
import java.io.IOException;

import parser.expression.*;

public class ExpressionStatement extends Statement{

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
		out.write(prefix + "\t<Expression>\n");
		data.print(indent+2, out);
		out.write(prefix + "\t</Expression>\n");
		out.write(prefix + "</ExpressionStatement>\n");
	}
	
}
