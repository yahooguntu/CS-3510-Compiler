package parser.statement;

import java.io.BufferedWriter;
import java.io.IOException;

import compiler.CMinusCompiler;

import lowlevel.*;
import lowlevel.Operand.*;
import lowlevel.Operation.*;
import parser.expression.*;

/**
 * Represents a return from a function.
 */
public class ReturnStatement extends Statement
{
	// optional expression to evaluate and return
	private Expression body;
	
	public ReturnStatement(Expression body)
	{
		this.body = body;		
	}
	
	public Expression getBody()
	{
		return body;
	}
	
	public void print(int indent, BufferedWriter out) throws IOException
	{
		String prefix = "";
		for(int i = 0; i < indent; i++)
			prefix += "\t";
		
		out.write(prefix + "<ReturnStatement>");
		if (body != null)
		{
			out.write("\n" + prefix + "\t<Expression>\n");
			body.print(indent+2, out);
			out.write(prefix + "\t</Expression>\n" + prefix);
		}
		out.write("</ReturnStatement>\n");
	}

	/* (non-Javadoc)
	 * @see parser.statement.Statement#genLLCode(lowlevel.Function)
	 */
	@Override
	public void genLLCode(Function parent) {
		// Seth
		int regNum = parent.getNewRegNum();
		setRegisterNum(regNum);
		body.genLLCode(parent);
		
		//put the return statement into register
		Operation op1 = new Operation(OperationType.ASSIGN, parent.getCurrBlock());
		Operand srcReg1 = new Operand(OperandType.REGISTER, body.getRegisterNum());
		Operand desReg1 = new Operand(OperandType.REGISTER, regNum);
		op1.setSrcOperand(0, srcReg1);
		op1.setDestOperand(0, desReg1);
		
		//puts the registered return statement into the retreg 
		Operation op2 = new Operation(OperationType.ASSIGN, parent.getCurrBlock());
		Operand srcReg2 = new Operand(OperandType.REGISTER, regNum);
		Operand desReg2 = new Operand(OperandType.MACRO, "RetReg");
		op2.setSrcOperand(0, srcReg2);
		op2.setDestOperand(0, desReg2);
		
		//returns the retreg register
		Operation op = new Operation(OperationType.RETURN, parent.getCurrBlock());
		Operand srcReg = new Operand(OperandType.MACRO, "RetReg");
		op.setSrcOperand(0, srcReg);
		
		
		parent.getCurrBlock().appendOper(op1);
		parent.getCurrBlock().appendOper(op2);
		parent.getCurrBlock().appendOper(op);
	}
}
