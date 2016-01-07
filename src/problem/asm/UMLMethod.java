package problem.asm;

import java.util.ArrayList;
import java.util.Arrays;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class UMLMethod implements IGraphItem
{
	
	//TODO Perhaps these should be stored as types, not as strings? (Same for all others?)
	//TODO Store both the full path to the class and the shortened one (for drawing arrows and stuff)
	/**
	 * An ArrayList of {@link TypeData} objects that represent all of the data for the arguments of this method.
	 */
	private ArrayList<TypeData> argData;
	
	/**
	 * The name of this method.
	 */
	private String name;
	
	/**
	 * A {@link TypeData} object representing all of the data for the return type for this method.
	 */
	private TypeData returnType;
	
	/**
	 * The access type of this field (public, private, etc), as an integer value. See {@link Opcodes}.
	 */
	private int accessType;
	
	/**
	 * Constructor.
	 * @param name		The {@link #name} of this method.
	 * @param accType	The {@link #accessType} of this method.
	 * @param desc		The description string. This is from an asm visitor.
	 * @param signature	The signature string. This is used to look at generic types of the field. From an asm visitor.
	 */
	public UMLMethod(String name, int accType, String desc, String signature)
	{
		argData = new ArrayList<TypeData>();
		this.name = name;
		this.name = this.name.replaceAll("[^\\w]", "");
		this.accessType = accType;
		
		String retType = Type.getReturnType(desc).getClassName();
		retType = retType.substring(retType.lastIndexOf('.') + 1);
		returnType = new TypeData(retType, null);
		
		Type[] argTypes = Type.getArgumentTypes(desc);

		for(Type t:argTypes)
		{
			argData.add(new TypeData(t.getClassName().substring(t.getClassName().lastIndexOf('.') + 1), null));
		}
		
		String s = null;
		if(signature != null)
		{
			s = Type.getType(signature).getElementType().toString();
			
			//TODO Clean this up
			String args = signature.split("\\)")[0];
			String[] argList = args.split("\\>\\;");
			ArrayList<String> fullArgs = new ArrayList<String>();
			for (String str : argList)
			{
				if (str.contains(";"))
				{
					ArrayList<String> tempList = new ArrayList<String>();
					tempList.addAll(Arrays.asList(str.trim().split(";")));
					for (String str2 : tempList)
					{
						if(!str2.isEmpty())
						{
							fullArgs.add(str2);
						}
					}
				}
				else
				{
					if (!str.isEmpty())
					{
						fullArgs.add(str);
					}
				}
			}
			for (int i = 0; i < fullArgs.size(); i++)
			{
				String str = fullArgs.get(i);
				if (str.contains("<"))
				{
					String[] splitString = str.split("<");
					String temp = splitString[splitString.length - 1];
					TypeData tempData = new TypeData(temp.substring(temp.lastIndexOf("/") + 1), null);
					for (int x = splitString.length - 2; x > 0; x--)
					{
						TypeData secondData = new TypeData(splitString[x].substring(temp.lastIndexOf("/") + 1), tempData);
						tempData = secondData;
					}
					argData.get(i).setSubData(tempData);
				}
			}
			
			// Deal with the return type data
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
				this.returnType.setSubData(tempData);
			}
		}
	}
	
	/**
	 * This method is for testing purposes.
	 * @param name			Name of the method.
	 * @param accType		The access type of the method (see asm.Opcodes)
	 * @param argumentData	An ArrayList containing the data for the method arguments.
	 * @param returnType    The return type of the method.
	 */
	public UMLMethod(String name, int accType, ArrayList<TypeData> argumentData, TypeData returnType)
	{
		this.name = name;
		this.accessType = accType;
		this.argData = argumentData;
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
		else if((accessType & Opcodes.ACC_PROTECTED) == Opcodes.ACC_PROTECTED)
		{
			builder.append("# ");
		}
		builder.append(name);
		builder.append("(");
		for (int i = 0; i < argData.size(); i++)
		{
			builder.append(argData.get(i).toGraphVizString());
			if (i < argData.size() - 1)
				builder.append(", ");
		}

		builder.append(") : ");
		builder.append(this.returnType.toGraphVizString());
		builder.append("\\l");
		
		return builder.toString();
	}

	/**
	 * @return the {@link #argData argument data} of the method
	 */
	public ArrayList<TypeData> getArgumentData()
	{
		return argData;
	}

	/**
	 * @return the {@link #name} of the method
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * @return the {@link #returnType} of the method
	 */
	public TypeData getReturnType() 
	{
		return this.returnType;
	}

	/**
	 * @return the {@link #accessType} of the method 
	 */
	public int getAccessType() 
	{
		return accessType;
	}
}
