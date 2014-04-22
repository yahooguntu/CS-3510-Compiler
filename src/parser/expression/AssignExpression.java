package parser.expression;

import java.io.BufferedWriter;
import java.io.IOException;

import compiler.CMinusCompiler;

import lowlevel.*;
import lowlevel.Operand.OperandType;
import lowlevel.Operation.OperationType;

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

	/* (non-Javadoc)
	 * @see parser.expression.Expression#genLLCode(lowlevel.Function)
	 */
	@Override
	public void genLLCode(Function parent) {
		// TODO Auto-generated method stub
		
		setRegisterNum(parent.getNewRegNum());
		rightSide.genLLCode(parent);
		
		Operation op = new Operation(OperationType.ASSIGN, parent.getCurrBlock());
		
		Operand srcReg = new Operand(OperandType.REGISTER, rightSide.getRegisterNum());
		Operand destReg = null;
		
		if (parent.getTable().containsKey(variable.getIdentifier()))
		{
			//TODO handle arrays
			int regNum = (Integer) parent.getTable().get(variable.getIdentifier());
			destReg = new Operand(OperandType.REGISTER, regNum);
		}
		else
		{
			//TODO handle arrays
			if (CMinusCompiler.globalHash.containsKey(variable.getIdentifier()))
			{
				//TODO handle global vars
			}
		}
		
		op.setSrcOperand(0, srcReg);
		op.setDestOperand(0, destReg);
		
		parent.getCurrBlock().appendOper(op);
	}
}
