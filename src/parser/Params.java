package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import lowlevel.CodeItem;

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
	
	public CodeItem genLLCode() {
		// TODO Auto-generated method stub
		return null;
	}
}
