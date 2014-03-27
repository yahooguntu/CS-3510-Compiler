package parser.expression;

import java.io.BufferedWriter;
import java.io.IOException;

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
	public void print(int indent, BufferedWriter out) throws IOException
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		out.write(prefix + "<BinaryExpression>\n");
		
		out.write(prefix + "\t<Operand>" + operand.name() + "</Operand>\n");
		
		out.write(prefix + "\t<LeftSide>\n");
		leftSide.print(indent + 2, out);
		out.write(prefix + "\t</LeftSide>\n");
		
		out.write(prefix + "\t<RightSide>\n");
		rightSide.print(indent + 2, out);
		out.write(prefix + "\t</RightSide>\n");
		
		out.write(prefix + "</BinaryExpression>\n");
	}
}