package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

import compiler.CMinusCompiler;

import scanner.Token.TokenType;
import lowlevel.*;
import lowlevel.Data;
import lowlevel.Operand.OperandType;
import lowlevel.Operation.OperationType;

/**
 * Represents a variable declaration.
 */
public class VariableDeclaration extends Declaration
{
	// the name of the variable
	private String id;
	private TokenType type;
	
	// -1 indicates this isn't an array
	// 0 indicates unknown size (for function params)
	private int arraySize;
	
	/**
	 * Makes a variable of type int.
	 * @param id
	 */
	public VariableDeclaration(TokenType type, String id)
	{
		this.id = id;
		this.type = type;
		arraySize = -1;
	}
	
	/**
	 * Makes an array of type int.
	 * @param id
	 * @param size
	 */
	public VariableDeclaration(TokenType type, String id, int size)
	{
		this.type = type;
		this.id = id;
		arraySize = size;
	}
	
	public String getId()
	{
		return id;
	}
	
	public TokenType getType()
	{
		return type;
	}
	
	/**
	 * Gets the size of this variable array.
	 * -1 indicates it is not an array.
	 * @return
	 */
	public int getArraySize()
	{
		return arraySize;
	}
	
	@Override
	public void print(int indent, BufferedWriter out) throws IOException
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		out.write(prefix + "<VariableDeclaration>\n");
		
		out.write(prefix + "\t<Name>" + id + "</Name>\n");
		out.write(prefix + "\t<Type>" + "INT" + "</Type>\n");
		
		if (arraySize != -1)
			out.write(prefix + "\t<Size>" + arraySize + "</Size>\n");
		
		out.write(prefix + "</VariableDeclaration>\n");
	}

	public CodeItem genLLCode(Function parent) {
		// Seth
		// we are a function variable
		int regNum = parent.getNewRegNum();
		setRegisterNum(regNum);
		
		HashMap<String, Integer> symbol = parent.getTable();
		symbol.put(id, regNum);
		
		return null;
	}
	
	@Override
	public CodeItem genLLCode() {		
		// we are a global variable
		HashMap global =  CMinusCompiler.globalHash;
		Data var = null;
		if(arraySize == -1)
		{
			if(type.compareTo(TokenType.INT) == 0)
			{
				var = new Data(Data.TYPE_INT, id);
			}
			else
			{
				var = new Data(Data.TYPE_VOID, id);
			}
		}
		else
		{
			if(type.compareTo(TokenType.INT) == 0)
			{
				var = new Data(Data.TYPE_INT, id, true, arraySize);
			}
			else
			{
				var = new Data(Data.TYPE_VOID, id, true, arraySize);
			}
		}
		global.put(id, var);
		return var;
	}
}
