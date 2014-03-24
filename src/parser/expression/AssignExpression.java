package parser.expression;

public class AssignExpression extends Expression
{
	private VariableExpression variable;
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
	
	@Override
	public void print(int indent)
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix = "\t";
		
		System.out.println(prefix + "<AssignExpression>");
		
		System.out.println(prefix + "\t<Variable>");
		variable.print(indent + 2);
		System.out.println(prefix + "\t</Variable>");
		
		System.out.println(prefix + "\t<Value>");
		rightSide.print(indent + 2);
		System.out.println(prefix + "\t</Value>");
		
		System.out.println(prefix + "</AssignExpression>");
	}
}
