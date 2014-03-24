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
		for (int i = 0; i < indent; i++)
			System.out.print("\t");
		
		System.out.print("<VariableDeclaration type=\"int\"");
		
		if (arraySize == -1)
			System.out.print(" size=\"" + arraySize + "\"");
		
		System.out.println(">" + id + "</VariableDeclaration>");
	}
}
