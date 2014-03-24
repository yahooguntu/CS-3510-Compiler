package parser.expression;

public class NumberExpression extends Expression
{
	private int value;
	
	public NumberExpression(int val)
	{
		value = val;
	}
	
	public int getValue()
	{
		return value;
	}
	
	@Override
	public void print(int index)
	{
		String prefix = "";
		for (int i = 0; i < index; i++)
			prefix += "\t";
		
		System.out.println(prefix + "<NumberExpression>" + value + "</NumberExpression>");
	}
}
