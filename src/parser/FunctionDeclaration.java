package parser;

import parser.statement.CompoundStatement;

public class FunctionDeclaration extends Declaration
{
	private Params parameters;
	private String name;
	private CompoundStatement body;
	
	public FunctionDeclaration(String functionName, Params params, CompoundStatement body)
	{
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
	public void print(int indent)
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		System.out.println(prefix + "<FunctionDeclaration>");
		
		System.out.println(prefix + "\t<Name>" + name + "</Name>");
		
		parameters.print(indent + 1);
		
		body.print(indent + 1);
		System.out.println(prefix + "</FunctionDeclaration>");
	}
}
