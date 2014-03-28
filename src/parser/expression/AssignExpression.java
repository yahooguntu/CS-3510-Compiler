package parser.expression;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Represents assigning a value to a variable.
 */
public class AssignExpression extends Expression
{
	// the variable being written to
	private VariableExpression variable;
	// the value to assign to the variable
	private Expression rightSide;
	
	public AssignExpression(VariableExpression var, Expression rhs)
	{
		variable = var;
		rightSide = rhs;
	}
	
	public VariableExpression getVariableExpr()
	{
		return variable;
	}
	
	public Expression getRightSide()
	{
		return rightSide;
	}
	
	public void print(int indent, BufferedWriter out) throws IOException
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		out.write(prefix + "<AssignExpression>\n");
		
		out.write(prefix + "\t<Variable>\n");
		variable.print(indent + 2, out);
		out.write(prefix + "\t</Variable>\n");
		
		out.write(prefix + "\t<Value>\n");
		rightSide.print(indent + 2, out);
		out.write(prefix + "\t</Value>\n");
		
		out.write(prefix + "</AssignExpression>\n");
	}
}
