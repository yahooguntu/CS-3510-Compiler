package parser.expression;

import java.io.BufferedWriter;
import java.io.IOException;

import lowlevel.*;
import lowlevel.Operation.OperationType;
import lowlevel.Operand.OperandType;

/**
 * Represents a numerical value.
 * e.g. '5'
 */
public class NumberExpression extends Expression
{
	// the numerical value
	private int value;
	
	public NumberExpression(int val)
	{
		value = val;
	}
	
	public int getValue()
	{
		return value;
	}
	
	@Override
	public void print(int index, BufferedWriter out) throws IOException
	{
		String prefix = "";
		for (int i = 0; i < index; i++)
			prefix += "\t";
		
		out.write(prefix + "<NumberExpression>" + value + "</NumberExpression>\n");
	}

	/* (non-Javadoc)
	 * @see parser.expression.Expression#genLLCode(lowlevel.Function)
	 */
	@Override
	public void genLLCode(Function parent) {
				
		int regNum = parent.getNewRegNum();
		Operation op = new Operation(OperationType.ASSIGN, parent.getCurrBlock());
		Operand destReg = new Operand(OperandType.REGISTER, regNum);
		Operand sorcReg = new Operand(OperandType.INTEGER, value);
		
		op.setDestOperand(0, destReg);
		op.setSrcOperand(0, sorcReg);
		parent.getCurrBlock().appendOper(op);
		this.setRegisterNum(regNum);
	}
}
