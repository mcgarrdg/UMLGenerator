package problem.asm;

public class TypeData implements IGraphItem
{
	private String type;
	private TypeData subData;
	
	/**
	 * 
	 * @param type		The type of data this is.
	 * @param subData	Any sub data (generic type) this data has. Null for no generics.
	 */
	public TypeData(String type, TypeData subData) 
	{
		this.type = type;
		this.subData = subData;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}

	public void setSubData(TypeData d)
	{
		this.subData = d;
	}
	
	public String getBaseDataType()
	{
		if (this.subData == null)
		{
			return this.type;
		}
		return this.subData.getBaseDataType();
	}
	
	public String toGraphVizString()
	{
		if (subData != null)
		{
			return (this.type + "\\<" + subData.toGraphVizString() + "\\>");
		}
		return this.type;
	}

}
