package problem.asm;

public class ArgumentData {
	
	private String type;
	private String genericType;
	public ArgumentData(String type, String generic) 
	{
		this.type = type;
		this.genericType = generic;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public void setGenericType(String generic)
	{
		this.genericType = generic;
	}
	
	public String getArgument()
	{
		StringBuilder b = new StringBuilder();
		b.append(type);
		if (genericType != null)
		{
			b.append("\\<");
			b.append(genericType);
			b.append("\\>");
		}
		return b.toString();
	}

}
