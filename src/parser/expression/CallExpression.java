package parser.expression;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

/**
 * Represents a function call.
 */
public class CallExpression extends Expression
{
	// the name of the function
	String functionName;
	// the arguments being passed to the function
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
	public void print(int indent, BufferedWriter out) throws IOException
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		out.write(prefix + "<CallExpression>\n");
		
		out.write(prefix + "\t<FunctionName>" + functionName + "</FunctionName>\n");
		
		out.write(prefix + "\t<Arguments>\n");
		
		for (Expression arg : arguments)
			arg.print(indent + 2, out);
		
		out.write(prefix + "\t</Arguments>\n");
		out.write(prefix + "</CallExpression>\n");
	}
}
