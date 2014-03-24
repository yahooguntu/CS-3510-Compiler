package parser;

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
	public void print(int indent)
	{
		String prefix = "";
		for (int i = 0; i < indent; i++)
			prefix += "\t";
		
		System.out.print(prefix + "<VariableDeclaration>");
		
		System.out.println(prefix + "\t<Name>" + id + "</Name>");
		System.out.println(prefix + "\t<Type>" + "INT" + "</Type>");
		
		if (arraySize == -1)
			System.out.print(prefix + "\t<Size>" + arraySize + "</Size>");
		
		System.out.println("</VariableDeclaration>");
	}
}
