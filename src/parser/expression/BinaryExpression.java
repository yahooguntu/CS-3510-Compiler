package parser.expression;

import java.io.BufferedWriter;
import java.io.IOException;

import lowlevel.*;
import scanner.Token.TokenType;

/**
 * Represents a binary expression.
 */
public class BinaryExpression extends Expression
{
	// the left side of the expression
	private Expression leftSide;
	// the right side of the expression
	private Expression rightSide;
	// the operator: anything in relop, addop, mulop
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

	/* (non-Javadoc)
	 * @see parser.expression.Expression#genLLCode(lowlevel.Function)
	 */
	@Override
	public CodeItem genLLCode(Function parent) {
		// TODO Auto-generated method stub
		return null;
	}
}