package parser.statement;

import parser.*;
import java.util.List;

public class CompoundStatement extends Statement
{
	private Declaration locals;
	private List<Statement> body;
	
	public CompoundStatement(Declaration locals, List<Statement> body)
	{
		locals = this.locals;
		body = this.body;
	}
	
	public Declaration getLocals()
	{
		return locals;
	}
	
	public List<Statement> getBody()
	{
		return body;
	}
	
	// to keep Statement happy in the meantime
	@Override
	public void print(int indent)
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		System.out.println(prefix + "<CompoundStatement>");
		System.out.println(prefix + "\t<Declaration>");
		locals.print(indent+2);
		System.out.println(prefix + "\t</Declaration>");
		System.out.println(prefix + "\t<Statments>");
		for(Statement temp : body)
		{
			temp.print(indent+2);
		}
		System.out.println(prefix + "\t</Statments>");
		System.out.println(prefix + "</CompoundStatment>");
	}
}
