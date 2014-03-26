package parser.expression;

import scanner.Token.TokenType;

public class BinaryExpression extends Expression
{
	private Expression leftSide;
	private Expression rightSide;
	private TokenType operand;
	
	public BinaryExpression(Expression lhs, TokenType op, Expression rhs)
	{
		leftSide = lhs;
		operand = op;
		rightSide = rhs;
	}
	
	public Expression getLeftSide()
	{
		return leftSide;
	}
	
	public Expression getRightSide()
	{
		return rightSide;
	}
	
	public TokenType getOperand()
	{
		return operand;
	}
	
	@Override
	public void print(int indent)
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		System.out.println(prefix + "<BinaryExpression>");
		
		System.out.println(prefix + "\t<Operand>" + operand.name() + "</Operand>");
		
		System.out.println(prefix + "\t<LeftSide>");
		leftSide.print(indent + 2);
		System.out.println(prefix + "\t</LeftSide>");
		
		System.out.println(prefix + "\t<RightSide>");
		rightSide.print(indent + 2);
		System.out.println(prefix + "\t</RightSide>");
		
		System.out.println(prefix + "</BinaryExpression>");
	}
}