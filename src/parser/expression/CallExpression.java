package parser.expression;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import lowlevel.*;
import lowlevel.Operand.OperandType;
import lowlevel.Operation.OperationType;

/**
 * Represents a function call.
 */
public class CallExpression extends Expression
{
	// the name of the function
	String functionName;
	// the arguments being passed to the function
	List<Expression> arguments;
	
	public CallExpression(String funcName, List<Expression> args)
	{
		functionName = funcName;
		arguments = args;
	}
	
	public String getFunctionName()
	{
		return functionName;
	}
	
	public List<Expression> getArgs()
	{
		return arguments;
	}
	
	@Override
	public void print(int indent, BufferedWriter out) throws IOException
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		out.write(prefix + "<CallExpression>\n");
		
		out.write(prefix + "\t<FunctionName>" + functionName + "</FunctionName>\n");
		
		out.write(prefix + "\t<Arguments>\n");
		
		for (Expression arg : arguments)
			arg.print(indent + 2, out);
		
		out.write(prefix + "\t</Arguments>\n");
		out.write(prefix + "</CallExpression>\n");
	}

	/* (non-Javadoc)
	 * @see parser.expression.Expression#genLLCode(lowlevel.Function)
	 */
	@Override
	public void genLLCode(Function parent)
	{
		for (int i = 0; i < arguments.size(); i++)
		{
			Expression param = arguments.get(i);
			param.genLLCode(parent);
			
			Operation passOp = new Operation(OperationType.PASS, parent.getCurrBlock());
			Operand target = new Operand(OperandType.REGISTER, param.getRegisterNum());
			passOp.addAttribute(new Attribute("PARAM_NUM", Integer.toString(i+1)));
			passOp.setSrcOperand(0, target);
			parent.getCurrBlock().appendOper(passOp);
		}
		
		Operation callOp = new Operation(OperationType.CALL, parent.getCurrBlock());
		Operand funcName = new Operand(OperandType.STRING, functionName);
		callOp.setSrcOperand(0, funcName);
		parent.getCurrBlock().appendOper(callOp);
		callOp.addAttribute(new Attribute("numParams", Integer.toString(arguments.size())));
		
		int retVal = parent.getNewRegNum();
		Operation mvRetVal = new Operation(OperationType.ASSIGN, parent.getCurrBlock());
		Operand src = new Operand(OperandType.MACRO, "RetReg");
		Operand dest = new Operand(OperandType.REGISTER, retVal);
		
		mvRetVal.setSrcOperand(0, src);
		mvRetVal.setDestOperand(0, dest);
		parent.getCurrBlock().appendOper(mvRetVal);
		
		setRegisterNum(retVal);
	}
}
