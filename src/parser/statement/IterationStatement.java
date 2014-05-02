package parser.statement;

import java.io.BufferedWriter;
import java.io.IOException;

import lowlevel.*;
import lowlevel.Operand.OperandType;
import lowlevel.Operation.OperationType;
import parser.expression.*;

/**
 * Represents a while loop.
 */
public class IterationStatement extends Statement
{
	// the deciding expression
	private Expression compare;
	// the code to execute in the loop
	private Statement body;
	
	public IterationStatement(Expression compare, Statement body)
	{
		this.compare = compare;
		this.body = body;
	}
	
	public Expression getCompare()
	{
		return compare;
	}
	
	public Statement getBody()
	{
		return body;
	}
	
	public void print(int indent, BufferedWriter out) throws IOException
	{
		String prefix = "";
		for(int i = 0; i < indent; i++)
			prefix += "\t";
		
		out.write(prefix + "<IterationStatement>\n");
		out.write(prefix + "\t<Expression>\n");
		compare.print(indent+2, out);
		out.write(prefix + "\t</Expression>\n");
		out.write(prefix + "\t<Do>\n");
		body.print(indent+2, out);
		out.write(prefix + "\t</Do>\n");
		out.write(prefix + "</IterationStatement>\n");
	}

	/* (non-Javadoc)
	 * @see parser.statement.Statement#genLLCode(lowlevel.Function)
	 */
	@Override
	public void genLLCode(Function parent) {			
		// make two blocks LOOP and POST
		BasicBlock Loop = new BasicBlock(parent);
		BasicBlock Post = new BasicBlock(parent);
		// gencode condition
		compare.genLLCode(parent);
		// gencode branch
		Operation op = new Operation(OperationType.BEQ, parent.getCurrBlock());
		Operand srcReg1 = new Operand(OperandType.REGISTER, compare.getRegisterNum());
		Operand srcReg2 = new Operand(OperandType.INTEGER, 0);
		Operand srcReg3 = new Operand(OperandType.BLOCK, Post.getBlockNum());
		op.setSrcOperand(0, srcReg1);
		op.setSrcOperand(1, srcReg2);
		op.setSrcOperand(2, srcReg3);
		parent.getCurrBlock().appendOper(op);
		// append LOOP
		parent.appendToCurrentBlock(Loop);
		// cb = LOOP
		parent.setCurrBlock(Loop);
		// gencode LOOP
		body.genLLCode(parent);
		// gencode condition
		compare.genLLCode(parent);
		// gencode branch
		Operation op2 = new Operation(OperationType.BNE, parent.getCurrBlock());
		Operand srcReg4 = new Operand(OperandType.REGISTER, compare.getRegisterNum());
		Operand srcReg5 = new Operand(OperandType.INTEGER, 0);
		Operand srcReg6 = new Operand(OperandType.BLOCK, Loop.getBlockNum());
		op2.setSrcOperand(0, srcReg4);
		op2.setSrcOperand(1, srcReg5);
		op2.setSrcOperand(2, srcReg6);
		
		parent.getCurrBlock().appendOper(op2);
		// append POST
		parent.appendToCurrentBlock(Post);
		// cb = POST
		parent.setCurrBlock(Post);
		
	}
}
