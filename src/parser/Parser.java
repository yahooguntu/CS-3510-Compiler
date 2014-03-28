package parser;

/**
 * Parser interface.
 * Meets the specifications for the project.
 */
public interface Parser
{
	public void printTree(String outFile);
	public Program parse();
}
