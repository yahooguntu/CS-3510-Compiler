package parser;

import java.io.BufferedWriter;
import java.io.IOException;

import lowlevel.Function;
import parser.statement.CompoundStatement;
import scanner.Token.TokenType;

/**
 * Represents a function declaration.
 */
public class FunctionDeclaration extends Declaration
{
	// the function's return type (INT or VOID)
	private TokenType returnType;
	// expected parameters
	private Params parameters;
	// function name
	private String name;
	// function body
	private CompoundStatement body;
	
	public FunctionDeclaration(TokenType returnType, String functionName, Params params, CompoundStatement body)
	{
		this.returnType = returnType;
		parameters = params;
		name = functionName;
		this.body = body;
	}

	public String getName()
	{
		return name;
	}
	
	public Params getParams()
	{
		return parameters;
	}
	
	public CompoundStatement getBody()
	{
		return body;
	}
	
	@Override
	public void print(int indent, BufferedWriter out) throws IOException
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		out.write(prefix + "<FunctionDeclaration>\n");
		
		out.write(prefix + "\t<Name>" + name + "</Name>\n");
		out.write(prefix + "\t<ReturnType>" + returnType.name() + "</ReturnType>\n");
		
		parameters.print(indent + 1, out);
		
		body.print(indent + 1, out);
		out.write(prefix + "</FunctionDeclaration>\n");
	}

	@Override
	public void genLLCode(Function parent) {
		// TODO Auto-generated method stub
		
	}
}
