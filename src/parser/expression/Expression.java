package parser.expression;

import java.io.BufferedWriter;
import java.io.IOException;

public abstract class Expression
{
	public abstract void print(int indent, BufferedWriter out) throws IOException;
}
