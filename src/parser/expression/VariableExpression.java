package parser.expression;

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
	public void print(int indent)
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		System.out.println(prefix + "<VariableExpression>");
		
		System.out.println(prefix + "\t<Identifier>" + identifier + "</Identifier>");
		
		System.out.println(prefix + "\t<Index>");
		index.print(indent + 2);
		System.out.println(prefix + "\t</Index>");
		
		System.out.println(prefix + "</VariableExpression>");
	}
}
