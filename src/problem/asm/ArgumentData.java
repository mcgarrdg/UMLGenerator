package problem.asm;

public class ArgumentData {
	
	private String type;
//	private String genericType;
	private ArgumentData subData;
	
	public ArgumentData(String type, ArgumentData subData) 
	{
		this.type = type;
//		this.genericType = generic;
		this.subData = subData;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
//	public void setGenericType(String generic)
//	{
//		this.genericType = generic;
//	}
	public void setSubData(ArgumentData d)
	{
		this.subData = d;
	}
	
	public String getArgument()
	{
		StringBuilder b = new StringBuilder();
		b.append(type);
//		if (genericType != null || subData != null)
		if (subData != null)
		{
			b.append("\\<");
			b.append(subData.getArgument());
//			if (subData != null)
//			{
//				b.append(subData.getArgument());
//			}
//			if (genericType != null)
//			{
//				b.append(genericType);
//			}
			b.append("\\>");
		}
		return b.toString();
	}

}
