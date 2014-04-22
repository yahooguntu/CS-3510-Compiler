package parser.expression;

import java.io.BufferedWriter;
import java.io.IOException;

import lowlevel.*;

/**
 * Represents an expression consisting of a single variable.
 */
public class VariableExpression extends Expression
{
	private String identifier;
	private Expression index;
	
	public VariableExpression(String id)
	{
		identifier = id;
		index = null;
	}
	
	public VariableExpression(String id, Expression index)
	{
		identifier = id;
		this.index = index;
	}
	
	public String getIdentifier()
	{
		return identifier;
	}
	
	/**
	 * Gets the index expression for this array.
	 * If null, this isn't an array variable.
	 * @return
	 */
	public Expression getIndex()
	{
		return index;
	}
	
	@Override
	public void print(int indent, BufferedWriter out) throws IOException
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		out.write(prefix + "<VariableExpression>\n");
		
		out.write(prefix + "\t<Identifier>" + identifier + "</Identifier>\n");
		if(index != null)
		{
			out.write(prefix + "\t<Index>\n");
			index.print(indent + 2, out);
			out.write(prefix + "\t</Index>\n");
		}
		
		out.write(prefix + "</VariableExpression>\n");
	}

	/* (non-Javadoc)
	 * @see parser.expression.Expression#genLLCode(lowlevel.Function)
	 */
	@Override
	public void genLLCode(Function parent) {
		// TODO Auto-generated method stub
	}
}
