package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import scanner.Token.TokenType;

import lowlevel.CodeItem;
import lowlevel.Data;
import lowlevel.FuncParam;
import lowlevel.Function;

/**
 * Represents parameters for a function definition.
 */
public class Params
{
	// the parameters
	List<VariableDeclaration> paramList;
	
	public Params(List<VariableDeclaration> list)
	{
		paramList = list;
	}
	
	public List<VariableDeclaration> getParameters()
	{
		return paramList;
	}
	
	public void print(int indent, BufferedWriter out) throws IOException
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		out.write(prefix + "<Params>\n");
		
		for (VariableDeclaration varDec : paramList)
		{
			varDec.print(indent + 1, out);
		}
		
		out.write(prefix + "</Params>\n");
	}
	
	public void genLLCode(Function parent) 
	{
		FuncParam start = null;
		FuncParam tail = null;
		
		for(int i = 0; i < paramList.size(); i++)
		{
			VariableDeclaration me = paramList.get(i);
			me.genLLCode(parent);
			FuncParam head = null;
			int type = -1;
			
			if(me.getType() == TokenType.INT)
			{
				type = Data.TYPE_INT;
			}
			else
			{
				type = Data.TYPE_VOID;
			}
			
			if(me.getArraySize() != -1)
			{
				head = new FuncParam(type, me.getId(), true);
			}
			else
			{
				head = new FuncParam(type,me.getId());
			}
			
			if(i == 0)
			{
				start = head;
				tail = head;
			}
			else
			{
				tail.setNextParam(head);
				tail = head;
			}
			
		}
		parent.setFirstParam(start);
	}
}
