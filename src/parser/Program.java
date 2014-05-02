package parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lowlevel.*;

/**
 * Represents a program in a single file.
 */
public class Program
{
	// the top-level declarations in this program (var and func)
	private List<Declaration> declarations;
	
	public Program(List<Declaration> decls)
	{
		declarations = decls;
	}
	
	public List<Declaration> getDeclarations()
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

	public CodeItem genLLCode()
	{
		CodeItem head = null;
		CodeItem tail = null;
		for (Declaration decl : declarations)
		{
			CodeItem next = decl.genLLCode();
			if (head == null)
			{
				head = next;
				tail = next;
			}
			else
			{
				tail.setNextItem(next);
				tail = next;
			}
		}
		return head;
	}
}