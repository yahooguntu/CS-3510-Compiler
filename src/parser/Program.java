package parser;

import java.io.BufferedWriter;
import java.io.IOException;
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
	
	public void print(int indent, BufferedWriter out) throws IOException
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		out.write(prefix + "<Program>\n");
		
		for (Declaration decl : declarations)
			decl.print(indent + 1, out);
		
		out.write(prefix + "</Program>\n");
	}
}