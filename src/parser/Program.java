package parser;

import java.util.ArrayList;
import java.util.List;

public class Program
{
	private List<Declaration> declarations;
	
	public Program(List<Declaration> decls)
	{
		declarations = decls;
	}
	
	public List<Declaration> getProgram()
	{
		return declarations;
	}
	
	public void print(int indent)
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		System.out.println(prefix + "<Program>");
		
		for (Declaration decl : declarations)
			decl.print(indent++);
		
		System.out.println(prefix + "</Program>");
	}
}