package parser.statement;

import parser.statement.*;
import parser.expression.*;

public class SelectionStatement extends Statement{
	
	private Expression compare;
	private Statement body;
	private Statement else_part;
	
	public SelectionStatement(Expression compare, Statement body, Statement else_part)
	{
		compare = this.compare;
		body = this.body;
		else_part = this.else_part;
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

	public void print(int indent){
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		System.out.print(prefix + "<SelectionStatement>");
		
		System.out.println(prefix + "\t<Expression>");
		compare.print(indent+2);
		System.out.println(prefix + "\t</Expression>");
		
		System.out.println(prefix + "\t<Then>");
		body.print(indent+2);
		System.out.println(prefix + "\t</Then>");
		
		if(else_part == null)
		{
			System.out.println(prefix + "\t<Else>");
			else_part.print(indent+2);
			System.out.println(prefix + "\t</Else>");
		}
		
		System.out.println(prefix + "</SelectionStatment>");		
	}

}
