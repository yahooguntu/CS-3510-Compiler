package parser;

import java.io.BufferedWriter;
import java.io.IOException;

public class VariableDeclaration extends Declaration
{
	private String id;
	// -1 indicates this isn't an array
	private int arraySize;
	
	/**
	 * Makes a variable of type int.
	 * @param id
	 */
	public VariableDeclaration(String id)
	{
		this.id = id;
		arraySize = -1;
	}
	
	/**
	 * Makes an array of type int.
	 * @param id
	 * @param size
	 */
	public VariableDeclaration(String id, int size)
	{
		this.id = id;
		arraySize = size;
	}
	
	public String getId()
	{
		return id;
	}
	
	/**
	 * Gets the size of this variable array.
	 * -1 indicates it is not an array.
	 * @return
	 */
	public int arraySize()
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
}
