package parser.statement;

import parser.expression.*;

public class ReturnStatement extends Statement{

	private Expression body;
	
	public ReturnStatement(Expression body)
	{
		body = this.body;		
	}
	
	public Expression getBody()
	{
		return body;
	}
	
	public void print(int indent)
	{
		String prefix = "";
		for(int i = 0; i < indent; i++)
			prefix += "\t";
		
		System.out.println(prefix + "<ReturnStatemnt>");
		System.out.println(prefix + "\t<Expression>");
		body.print(indent+2);
		System.out.println(prefix + "\t</Expression>");
		System.out.println(prefix + "</ReturnStatement>");
	}
}
