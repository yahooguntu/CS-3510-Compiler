package parser.statement;

import java.io.BufferedWriter;
import java.io.IOException;

import lowlevel.*;
import lowlevel.Operand.OperandType;
import lowlevel.Operation.OperationType;
import parser.statement.*;
import parser.expression.*;

/**
 * Represents an if statement.
 */
public class SelectionStatement extends Statement
{
	// the decision expession
	private Expression compare;
	// the body to be executed if the expression is 1
	private Statement body;
	// the optional else statement
	private Statement else_part;
	
	public SelectionStatement(Expression compare, Statement body, Statement else_part)
	{
		this.compare = compare;
		this.body = body;
		this.else_part = else_part;
	}
	
	public Expression getCompare() {
		return compare;
	}
	
	public Statement getBody() {
		return body;
	}

	public Statement getElse_part() {
		return else_part;
	}

	public void print(int indent, BufferedWriter out) throws IOException 
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		out.write(prefix + "<SelectionStatement>\n");
		
		out.write(prefix + "\t<Expression>\n");
		compare.print(indent+2, out);
		out.write(prefix + "\t</Expression>\n");
		
		out.write(prefix + "\t<Then>\n");
		body.print(indent+2, out);
		out.write(prefix + "\t</Then>\n");
		
		if(else_part != null)
		{
			out.write(prefix + "\t<Else>\n");
			else_part.print(indent+2, out);
			out.write(prefix + "\t</Else>\n");
		}
		
		out.write(prefix + "</SelectionStatement>\n");		
	}

	/* (non-Javadoc)
	 * @see parser.statement.Statement#genLLCode(lowlevel.Function)
	 */
	@Override
	public void genLLCode(Function parent) {
		// make 2-3 blocks THEN, [ELSE], POST
		BasicBlock Then = new BasicBlock(parent);
		BasicBlock Else = null;
		if(else_part != null)
		{
			Else = new BasicBlock(parent);
		}
		BasicBlock Post = new BasicBlock(parent);
		Operation op = new Operation(OperationType.BEQ, parent.getCurrBlock());
		// gencode the condition
		compare.genLLCode(parent);
		// gencode the branch
		Operand srcReg1 = new Operand(OperandType.REGISTER, compare.getRegisterNum());
		Operand srcReg2 = new Operand(OperandType.INTEGER, 0);
		Operand srcReg3 = null;
		if(Else != null)
		{
			srcReg3 = new Operand(OperandType.BLOCK, Else.getBlockNum());
		}
		else
		{
			srcReg3 = new Operand(OperandType.BLOCK, Post.getBlockNum());
		}
		op.setSrcOperand(0, srcReg1);
		op.setSrcOperand(1, srcReg2);
		op.setSrcOperand(2, srcReg3);
		parent.getCurrBlock().appendOper(op);
		// append THEN block
		parent.appendToCurrentBlock(Then);
		// cb = THEN
		parent.setCurrBlock(Then);
		// gencode THEN statements
		body.genLLCode(parent);
		// append POST
		parent.appendToCurrentBlock(Post);
		// if else block
		if(else_part != null)
		{
		//		cb = ELSE
				parent.setCurrBlock(Else);
		//		gencode ELSE statements
				else_part.genLLCode(parent);
		//		add jump to POST in ELSE block if there isnt a jump already there
				if (parent.getCurrBlock().getLastOper() == null || parent.getCurrBlock().getLastOper().getType() != OperationType.JMP)
				{
					Operation op2 = new Operation(OperationType.JMP, parent.getCurrBlock());
					Operand srcBlock = new Operand(OperandType.BLOCK, Post.getBlockNum());
					op2.setSrcOperand(0, srcBlock);
					parent.getCurrBlock().appendOper(op2);
				}
		//		append ELSE to unconnected chain
				parent.appendUnconnectedBlock(Else);
		}
		// cb = POST
		parent.setCurrBlock(Post);
	}

}
