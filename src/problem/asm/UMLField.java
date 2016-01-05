package problem.asm;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class UMLField implements IGraphItem {
	private String name;
	private String type;
	private int accessType;
	private String genericType;
	
	public UMLField(String name, int accessType, String desc, String signature)
	{
		this.name = name;
		this.accessType = accessType;
		type = Type.getType(desc).getClassName();
		
		String s = null;
		if (signature != null)
		{
			s = Type.getType(signature).getElementType().toString();
			s = s.substring(s.lastIndexOf('/') + 1, s.indexOf(';'));
		}
		this.genericType = s;
	}
	
	public String toGraphVizString()
	{
		StringBuilder builder = new StringBuilder();
		
		//TODO Extract this out into its own method, will all the access types.
		if((accessType & Opcodes.ACC_PUBLIC) != 0)
		{
			builder.append("+ ");
		} else if((accessType & Opcodes.ACC_PRIVATE) != 0)
		{
			builder.append("- ");
		}
		builder.append(name);
		builder.append(" : ");
		String s = type.substring(type.lastIndexOf('.') + 1);
		builder.append(s);
		if (genericType != null)
		{
			builder.append("\\<");
			builder.append(genericType);
			builder.append("\\>");
		}
		//builder.append(type);
		builder.append("\\l");
				
		return builder.toString();
	}

}
