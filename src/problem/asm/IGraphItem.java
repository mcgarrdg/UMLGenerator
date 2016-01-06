package problem.asm;

public interface IGraphItem {

	/**
	 * This should take the information in this class, and return a string in a format
	 * that describes the data in a way that GraphViz can recognize.
	 * @return	String describing data in a GraphViz format.
	 */
	public String toGraphVizString();

}
