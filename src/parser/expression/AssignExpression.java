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
	public void genLLCode(Function parent)
	{
		setRegisterNum(parent.getNewRegNum());
		rightSide.genLLCode(parent);
		
		if (parent.getTable().containsKey(variable.getIdentifier()))
		{
			//TODO handle arrays
			Operation op = new Operation(OperationType.ASSIGN, parent.getCurrBlock());
			
			Operand srcReg = new Operand(OperandType.REGISTER, rightSide.getRegisterNum());
			int regNum = (Integer) parent.getTable().get(variable.getIdentifier());
			Operand destReg = new Operand(OperandType.REGISTER, regNum);
			op.setSrcOperand(0, srcReg);
			op.setDestOperand(0, destReg);
		
			parent.getCurrBlock().appendOper(op);
		}
		else if (CMinusCompiler.globalHash.containsKey(variable.getIdentifier()))
		{
			//TODO handle global vars
			Data globalReg = (Data) CMinusCompiler.globalHash.get(variable.getIdentifier());
			
			Operation op = new Operation(OperationType.STORE_I, parent.getCurrBlock());
			Operand data = new Operand(OperandType.REGISTER, rightSide.getRegisterNum());
			Operand varName = new Operand(OperandType.STRING, globalReg.getName());
			Operand arrIndex = new Operand(OperandType.INTEGER, 0);
			op.setSrcOperand(0, data);
			op.setSrcOperand(1, varName);
			op.setSrcOperand(2, arrIndex);
			
			parent.getCurrBlock().appendOper(op);
		}
		
	}
}
