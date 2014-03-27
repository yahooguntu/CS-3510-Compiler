package parser.statement;

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
	
	public void print(int indent)
	{
		String prefix = "";
		for(int i = 0; i < indent; i++)
			prefix += "\t";
		
		System.out.println(prefix + "<IterationStatement>");
		System.out.println(prefix + "\t<Expression>");
		compare.print(indent+2);
		System.out.println(prefix + "\t</Expression>");
		System.out.println(prefix + "\t<Then>");
		body.print(indent+2);
		System.out.println(prefix + "\t</Then>");
		System.out.println(prefix + "</IterationStatement>");
	}
}
