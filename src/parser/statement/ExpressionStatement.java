package parser.statement;

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
	
	public void print(int indent)
	{
		String prefix = "";
		for(int i = 0; i < indent; i++)
			prefix += "\t";
		
		System.out.println(prefix + "<ExpressionStatement>");
		System.out.println(prefix + "\t<Expression>");
		data.print(indent+2);
		System.out.println(prefix + "\t</Expression>");
		System.out.println(prefix + "</ExpressionStatement>");
	}
	
}
