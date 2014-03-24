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
		
		System.out.println(prefix + "<FunctionDeclaration>");
		
		System.out.println(prefix + "\t<Name>" + name + "</Name>");
		
		System.out.println(prefix + "\t<Parameters>");
		parameters.print(indent + 2);
		System.out.println(prefix + "\t</Parameters>");
		
		body.print(indent++);
	}
}
