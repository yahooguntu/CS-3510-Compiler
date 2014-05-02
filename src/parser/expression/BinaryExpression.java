package parser.expression;

import java.io.BufferedWriter;
import java.io.IOException;

import lowlevel.*;
import lowlevel.Operand.OperandType;
import lowlevel.Operation.OperationType;
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
	public void genLLCode(Function parent)
	{
		setRegisterNum(parent.getNewRegNum());
		
		leftSide.genLLCode(parent);
		rightSide.genLLCode(parent);
		
		Operation op;
		
		switch (operand)
		{
		case PLUS:
			op = new Operation(OperationType.ADD_I, parent.getCurrBlock());
			break;
			
		case MINUS:
			op = new Operation(OperationType.SUB_I, parent.getCurrBlock());
			break;
			
		case DIVIDE:
			op = new Operation(OperationType.DIV_I, parent.getCurrBlock());
			break;
			
		case MULTIPLY:
			op = new Operation(OperationType.MUL_I, parent.getCurrBlock());
			break;
			
		case GREATER_THAN:
			op = new Operation(OperationType.GT, parent.getCurrBlock());
			break;
			
		case LESS_THAN:
			op = new Operation(OperationType.LT, parent.getCurrBlock());
			break;
			
		case GREATER_EQUAL_THAN:
			op = new Operation(OperationType.GTE, parent.getCurrBlock());
			break;
			
		case LESS_EQUAL_THAN:
			op = new Operation(OperationType.LTE, parent.getCurrBlock());
			break;
			
		case EQUALS:
			op = new Operation(OperationType.EQUAL, parent.getCurrBlock());
			break;
			
		case NOT_EQUALS:
			op = new Operation(OperationType.NOT_EQUAL, parent.getCurrBlock());
			break;
			
		default:
			throw new RuntimeException("Illegal operand for BinaryExpression: " + operand.name());
		}
		
		Operand destReg = new Operand(OperandType.REGISTER, getRegisterNum());
		Operand addItemOne = new Operand(OperandType.REGISTER, leftSide.getRegisterNum());
		Operand addItemTwo = new Operand(OperandType.REGISTER, rightSide.getRegisterNum());
		
		op.setDestOperand(0, destReg);
		op.setSrcOperand(0, addItemOne);
		op.setSrcOperand(1, addItemTwo);
		
		parent.getCurrBlock().appendOper(op);
	}
}