package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import lowlevel.*;
import parser.statement.CompoundStatement;
import scanner.Token.TokenType;

/**
 * Represents a function declaration.
 */
public class FunctionDeclaration extends Declaration
{
	// the function's return type (INT or VOID)
	private TokenType returnType;
	// expected parameters
	private Params parameters;
	// function name
	private String name;
	// function body
	private CompoundStatement body;
	
	public FunctionDeclaration(TokenType returnType, String functionName, Params params, CompoundStatement body)
	{
		this.returnType = returnType;
		parameters = params;
		name = functionName;
		this.body = body;
	}

	public String getName()
	{
		return name;
	}
	
	public Params getParams()
	{
		return parameters;
	}
	
	public CompoundStatement getBody()
	{
		return body;
	}
	
	@Override
	public void print(int indent, BufferedWriter out) throws IOException
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		out.write(prefix + "<FunctionDeclaration>\n");
		
		out.write(prefix + "\t<Name>" + name + "</Name>\n");
		out.write(prefix + "\t<ReturnType>" + returnType.name() + "</ReturnType>\n");
		
		parameters.print(indent + 1, out);
		
		body.print(indent + 1, out);
		out.write(prefix + "</FunctionDeclaration>\n");
	}

	@Override
	public CodeItem genLLCode()
	{
		int funcReturnType = convertType(returnType);
		
		Function myFunc = null;
		
		if (parameters.paramList.size() == 0)
		{
			// function has no parameters
			myFunc = new Function(funcReturnType, name);
		}
		else
		{
			// function has parameters
			myFunc = new Function(funcReturnType, name);
			parameters.genLLCode(myFunc);
			/*Iterator<VariableDeclaration> it = parameters.paramList.iterator();
			VariableDeclaration curr = it.next();
			FuncParam head = buildFuncParam(curr);
			FuncParam tail = head;
			myFunc = new Function(funcReturnType, name, head);
			HashMap table = myFunc.getTable();
			table.put(curr.getId(), curr.getRegisterNum());
			
			while (it.hasNext())
			{
				FuncParam nxt = buildFuncParam(it.next());
				table.put(nxt.getName(), nxt);
				tail.setNextParam(nxt);
				tail = nxt;
			}*/			
		}
		
		myFunc.createBlock0();
		BasicBlock newBlock = new BasicBlock(myFunc);
		myFunc.appendBlock(newBlock);
		myFunc.setCurrBlock(newBlock);
		
		body.genLLCode(myFunc);
		
		BasicBlock return_block = myFunc.getReturnBlock();
		myFunc.appendBlock(return_block);
		
		if (myFunc.getFirstUnconnectedBlock() != null)
		{
			myFunc.appendBlock(myFunc.getFirstUnconnectedBlock());
		}
		
		return myFunc;
	}
	
	/*public static FuncParam buildFuncParam(VariableDeclaration varDecl)
	{
		FuncParam toReturn;
		int paramType = convertType(varDecl.getType());
		
		if (varDecl.getArraySize() == -1)
		{
			// not an array
			toReturn = new FuncParam(paramType, varDecl.getId());
		}
		else
		{
			// this is an array
			toReturn = new FuncParam(paramType, varDecl.getId(), true);
		}
		
		return toReturn;
	}*/
	
	public static int convertType(TokenType type)
	{
		int toReturn;
		
		if (type == TokenType.INT)
		{
			toReturn = Data.TYPE_INT;
		}
		else
		{
			toReturn = Data.TYPE_VOID;
		}
		
		return toReturn;
	}
}
