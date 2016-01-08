package problem.asm;

import org.objectweb.asm.Opcodes;

public abstract class GraphItem {

	/**
	 * This should take the information in this class, and return a string in a format
	 * that describes the data in a way that GraphViz can recognize.
	 * @return	String describing data in a GraphViz format.
	 */
	public String toGraphVizString() 
	{
		return null;
	}

	/**
	 * Returns the string representation of the appropriate access symbol for the given accessType.
	 * @param accessType	An integer representing the access type. See See {@link Opcodes}.
	 * @return	The string representation of the access symbol.
	 */
	public String getAccessTypeSymbol(int accessType)
	{
		
		if((accessType & Opcodes.ACC_PUBLIC) == Opcodes.ACC_PUBLIC)
		{
			return "+";
		} else if((accessType & Opcodes.ACC_PRIVATE) == Opcodes.ACC_PRIVATE)
		{
			return "-";
		}
		else if((accessType & Opcodes.ACC_PROTECTED) == Opcodes.ACC_PROTECTED)
		{
			return "#";
		}
		return "~";
	}
}
