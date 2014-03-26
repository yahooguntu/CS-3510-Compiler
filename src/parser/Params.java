package parser;

import java.util.List;

public class Params
{
	List<VariableDeclaration> paramList;
	
	public Params(List<VariableDeclaration> list)
	{
		paramList = list;
	}
	
	public List<VariableDeclaration> getParameters()
	{
		return paramList;
	}
	
	public void print(int indent)
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		System.out.println(prefix + "<Params>");
		
		for (VariableDeclaration varDec : paramList)
		{
			varDec.print(indent + 1);
		}
		
		System.out.println(prefix + "</Params>");
	}
}
