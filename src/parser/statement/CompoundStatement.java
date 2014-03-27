package parser.statement;

import parser.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class CompoundStatement extends Statement
{
	private List<Declaration> locals;
	private List<Statement> body;
	
	public CompoundStatement(List<Declaration> locals, List<Statement> body)
	{
		this.locals = locals;
		this.body = body;
	}
	
	public List<Declaration> getLocals()
	{
		return locals;
	}
	
	public List<Statement> getBody()
	{
		return body;
	}
	
	// to keep Statement happy in the meantime
	@Override
	public void print(int indent, BufferedWriter out) throws IOException
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		out.write(prefix + "<CompoundStatement>\n");
		out.write(prefix + "\t<Declarations>\n");
		for (Declaration decl : locals)
		{
			decl.print(indent+2, out);
		}
		out.write(prefix + "\t</Declarations>\n");
		out.write(prefix + "\t<Statments>\n");
		for (Statement temp : body)
		{
			temp.print(indent+2, out);
		}
		out.write(prefix + "\t</Statments>\n");
		out.write(prefix + "</CompoundStatement>\n");
	}
}
