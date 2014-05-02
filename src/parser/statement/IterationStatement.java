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
		Operand srcReg = new Operand(OperandType.MACRO, "RetReg");
		op.setSrcOperand(0, srcReg);
		parent.getCurrBlock().appendOper(op);
		// append LOOP
		parent.appendBlock(Loop);
		// cb = LOOP
		parent.setCurrBlock(Loop);
		// gencode LOOP
		body.genLLCode(parent);
		// gencode condition
		compare.genLLCode(parent);
		// gencode branch
		Operation op2 = new Operation(OperationType.BNE, parent.getCurrBlock());
		Operand srcReg2 = new Operand(OperandType.MACRO, "RetReg");
		parent.getCurrBlock().appendOper(op2);
		// append POST
		parent.appendBlock(Post);
		// cb = POST
		parent.setCurrBlock(Post);
		
	}
}
