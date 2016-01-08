package problem.asm;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class UMLField extends GraphItem 
{
	/**
	 * The name of this field.
	 */
	private String name;
	
	/**
	 * The TypeData that represents this field.
	 */
	private TypeData type;
	
	/**
	 * The access type of this field (public, private, etc), as an integer value. See {@link Opcodes}.
	 */
	private int accessType;
	
	/**
	 * Constructor.
	 * @param name			Name of the field.
	 * @param accessType	The access type of the field. See {@link #accessType}
	 * @param desc			The description string. This is from an asm visitor.
	 * @param signature		The signature string. This is used to look at generic types of the field. From an asm visitor.
	 */
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
	
	@Override
	public String toGraphVizString()
	{
		return (this.getAccessTypeSymbol(this.accessType) + " " + this.name + " : " + this.type.toGraphVizString() + "\\l");
	}

	/**
	 * @return The {@link #name} of the field.
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * @return The {@link #type} of the field.
	 */
	public TypeData getType() 
	{
		return type;
	}

	/**
	 * @return The {@link #accessType} of the field.
	 */
	public int getAccessType()
	{
		return accessType;
	}

}
