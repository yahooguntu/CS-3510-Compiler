package parser.expression;

import java.util.List;

public class CallExpression extends Expression
{
	String functionName;
	List<Expression> arguments;
	
	public CallExpression(String funcName, List<Expression> args)
	{
		functionName = funcName;
		arguments = args;
	}
	
	public String getFunctionName()
	{
		return functionName;
	}
	
	public List<Expression> getArgs()
	{
		return arguments;
	}
	
	@Override
	public void print(int indent)
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		System.out.println(prefix + "<CallExpression>");
		
		System.out.println(prefix + "\t<FunctionName>" + functionName + "</FunctionName>");
		
		System.out.println(prefix + "\t<Arguments>");
		
		for (Expression arg : arguments)
			arg.print(indent + 2);
		
		System.out.println(prefix + "\t</Arguments>");
		System.out.println(prefix + "</CallExpression>");
	}
}
