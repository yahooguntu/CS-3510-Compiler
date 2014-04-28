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
		// TODO Auto-generated method stub
		int regNum = parent.getNewRegNum();
		// make 2-3 blocks THEN, [ELSE], POST
		BasicBlock Then = new BasicBlock(parent);
		BasicBlock Else = new BasicBlock(parent);
		BasicBlock Post = new BasicBlock(parent);
		Operation op = new Operation(OperationType.BEQ, parent.getCurrBlock());
		// gencode the condition
		compare.genLLCode(parent);
		// gencode the branch
		Operand srcReg = new Operand(OperandType.REGISTER, "RetReg");
		// append THEN block
		// cb = THEN
		// gencode THEN statements
		// append POST
		// if else block
		//		cb = ELSE
		//		gencode ELSE statements
		//		add jump to POST in ELSE block
		//		append ELSE to unconnected chain
		// cb = POST
	}

}
