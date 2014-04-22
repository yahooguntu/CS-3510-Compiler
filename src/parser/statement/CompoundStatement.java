package parser.statement;

import parser.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import lowlevel.*;

/**
 * Represents a group of declarations and statements
 * that are enclosed in curly braces.
 */
public class CompoundStatement extends Statement
{
	// the local declarations
	private List<Declaration> locals;
	// the statements to be executed in this block
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

	/* (non-Javadoc)
	 * @see parser.statement.Statement#genLLCode(lowlevel.Function)
	 */
	@Override
	public void genLLCode(Function parent) {
		// TODO Auto-generated method stub
	}
}
