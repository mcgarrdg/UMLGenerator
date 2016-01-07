package problem.asm;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class UMLField implements IGraphItem {
	private String name;
	private TypeData type;
	private int accessType;
	
	public UMLField(String name, int accessType, String desc, String signature)
	{
		this.name = name;
		this.accessType = accessType;
		
		String fieldT = Type.getReturnType(desc).getClassName();
		fieldT = fieldT.substring(fieldT.lastIndexOf('.') + 1);
		type = new TypeData(fieldT, null);
		
		String s = null;
		if (signature != null)
		{
			// I don't think the extra precautions are needed here, because if the signature
			// isn't null in a field, it should have generics, but I'm leaving them just in case.
			s = Type.getType(signature).getReturnType().toString();
			if (s.contains("<"))
			{
				s = s.substring(0, s.length() - 1);
				String[] splitString = s.split("<");
				String temp = splitString[splitString.length - 1];
				TypeData tempData = new TypeData(temp.substring(temp.lastIndexOf("/") + 1), null);
				for (int x = splitString.length - 2; x > 0; x--)
				{
					TypeData secondData = new TypeData(splitString[x].substring(temp.lastIndexOf("/") + 1), tempData);
					tempData = secondData;
				}
				this.type.setSubData(tempData);
			}
		}
	}
	
	/**
	 * This method is for testing purposes.
	 * @param name 			Name of the field
	 * @param type 			The type data for the field.
	 * @param accessType	The access type of the method (see asm.Opcodes)
	 */
	public UMLField(String name, TypeData type, int accessType)
	{
		this.name = name;
		this.type = type;
		this.accessType = accessType;
	}
	
	public String toGraphVizString()
	{
		String access = "";
		//TODO Extract this out into its own method, will all the access types.
		if((accessType & Opcodes.ACC_PUBLIC) != 0)
		{
			access = "+ ";
		} else if((accessType & Opcodes.ACC_PRIVATE) != 0)
		{
			access = "- ";
		}
		else if((accessType & Opcodes.ACC_PROTECTED) == Opcodes.ACC_PROTECTED)
		{
			access = "# ";
		}
				
		return (access + this.name + " : " + this.type.toGraphVizString() + "\\l");
	}

	/**
	 * @return the name of the Field
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the type of the Field
	 */
	public TypeData getType() {
		return type;
	}

	/**
	 * @return the accessType of the Field
	 */
	public int getAccessType() {
		return accessType;
	}

}
