package problem.asm;

public class TypeData implements IGraphItem
{
	/**
	 * Just the short name of the class. (Example: TypeData)
	 */
	private String baseName;
	
	/**
	 * The full path name of the type. (example: problem/asm/TypeData)
	 * Not currently used.
	 */
	private String fullName;
	
	/**
	 * A {@link TypeData} object that represents generic types of this object.
	 */
	private TypeData subData;
	
	/**
	 * 
	 * @param type		The name of the type of data that it is. (Example: TypeData).
	 * @param subData	Any sub data (generic type) this data has. Null for no generics.
	 */
	public TypeData(String type, TypeData subData) 
	{
		this.baseName = type;
		this.subData = subData;
	}
	
	/**
	 * Sets the {@link #baseName} of this TypeData.
	 * @param type	The name.
	 */
	public void setType(String type)
	{
		this.baseName = type;
	}
	
	/**
	 * Sets the {@link #subData} of this TypeData.
	 * @param d	The TypeData to be set as the subData.
	 */
	public void setSubData(TypeData d)
	{
		this.subData = d;
	}
	
	/**
	 * Gets the base type of data of this type. In other words, the last type of generic data
	 * associated with this TypeData that has no further generic types.
	 * Example: The baseDataType of ArrayList<LinkedList<String>> would be String.
	 * @return	The base data type.
	 */
	public String getBaseDataType()
	{
		if (this.subData == null)
		{
			return this.baseName;
		}
		return this.subData.getBaseDataType();
	}
	
	public String toGraphVizString()
	{
		if (subData != null)
		{
			return (this.baseName + "\\<" + subData.toGraphVizString() + "\\>");
		}
		return this.baseName;
	}

}
