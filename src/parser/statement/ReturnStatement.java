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
	public void genLLCode(Function parent)
	{
		
		if (body != null)
		{
			body.genLLCode(parent);
		}
		
		//puts the registered return statement into the retreg 
		Operation op2 = new Operation(OperationType.ASSIGN, parent.getCurrBlock());
		Operand srcReg = new Operand(OperandType.REGISTER, body.getRegisterNum());
		Operand desReg = new Operand(OperandType.MACRO, "RetReg");
		op2.setSrcOperand(0, srcReg);
		op2.setDestOperand(0, desReg);
		
		parent.getCurrBlock().appendOper(op2);
	}
}
