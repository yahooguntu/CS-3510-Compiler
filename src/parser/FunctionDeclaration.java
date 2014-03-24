package parser;

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
		
		System.out.println(prefix + "<FunctionDeclaration name=\"" + name + "\">");
		parameters.print(indent++);
		body.print(indent++);
	}
}
