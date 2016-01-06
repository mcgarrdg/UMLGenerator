package problem.asm;

import java.util.ArrayList;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class UMLMethod implements IGraphItem{
	
	private ArrayList<String> argumentTypes;
	private String name;
	private String returnType;
	private String returnGenericType;
	private int accessType;
	
	public UMLMethod(String name, int accType, String desc, String signature)
	{
		argumentTypes = new ArrayList<String>();
		this.name = name;
		this.name = this.name.replaceAll("[^\\w]", "");
		this.accessType = accType;
		
		returnType = Type.getReturnType(desc).getClassName();
		
		Type[] argTypes = Type.getArgumentTypes(desc);

		for(Type t:argTypes)
		{
			argumentTypes.add(t.getClassName());
		}
		 
		String s = null;
		if(signature != null)
		{
			s = Type.getType(signature).getElementType().toString();
			s = s.substring(s.lastIndexOf('/') + 1, s.indexOf(';'));
		}
		returnGenericType = s;
	}
	
	/**
	 * This method is for testing purposes.
	 * @param name			Name of the method.
	 * @param accType		The access type of the method (see asm.Opcodes)
	 * @param argumentTypes	An ArrayList of what the types of the arguments are.
	 * @param genericType	If this method returns a type with a generic, it is specified here. null if no generic type.
	 * @param returnType    The return type of the method.
	 */
	public UMLMethod(String name, int accType, ArrayList<String> argumentTypes, String genericType, String returnType)
	{
		this.name = name;
		this.accessType = accType;
		this.argumentTypes = argumentTypes;
		this.returnGenericType = genericType;
		this.returnType = returnType;
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
		builder.append("(");
		for (int i = 0; i < argumentTypes.size(); i++)
		{
			String s = argumentTypes.get(i);
			builder.append(s.substring(s.lastIndexOf('.') + 1));
			if (i < argumentTypes.size() - 1)
				builder.append(", ");
		}

		builder.append(") : ");
		builder.append(returnType.substring(returnType.lastIndexOf('.') + 1));
		if (returnGenericType != null)
		{
			builder.append("\\<");
			builder.append(returnGenericType);
			builder.append("\\>");
		}
		builder.append("\\l");
		
		
		return builder.toString();
	}

	/**
	 * @return the argumentTypes of the method
	 */
	public ArrayList<String> getArgumentTypes() {
		return argumentTypes;
	}

	/**
	 * @return the name of the method
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the returnType of the method
	 */
	public String getReturnType() {
		return returnType;
	}

	/**
	 * @return the returnGenericType of the method
	 */
	public String getReturnGenericType() {
		return returnGenericType;
	}

	/**
	 * @return the accessType of the method 
	 */
	public int getAccessType() {
		return accessType;
	}
}
