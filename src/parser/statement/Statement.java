package parser.statement;

import java.io.BufferedWriter;
import java.io.IOException;

public abstract class Statement
{
	public abstract void print(int indent, BufferedWriter out) throws IOException;
}
