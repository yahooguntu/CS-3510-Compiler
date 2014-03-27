package parser.expression;

import java.io.BufferedWriter;
import java.io.IOException;

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
	public void print(int index, BufferedWriter out) throws IOException
	{
		String prefix = "";
		for (int i = 0; i < index; i++)
			prefix += "\t";
		
		out.write(prefix + "<NumberExpression>" + value + "</NumberExpression>\n");
	}
}
