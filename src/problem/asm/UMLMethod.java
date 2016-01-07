package problem.asm;

import java.util.ArrayList;
import java.util.Arrays;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class UMLMethod implements IGraphItem{
	
	//TODO Perhaps these should be stored as types, not as strings? (Same for all others?)
	//TODO Store both the full path to the class and the shortened one (for drawing arrows and stuff)
	//private ArrayList<String> argumentTypes;
	//private ArrayList<String> argumentGenerics;
	private ArrayList<ArgumentData> argData;
	private String name;
	private String returnType;
	private String returnGenericType;
	private int accessType;
	
	public UMLMethod(String name, int accType, String desc, String signature)
	{
//		argumentTypes = new ArrayList<String>();
//		argumentGenerics = new ArrayList<String>();
		argData = new ArrayList<ArgumentData>();
		this.name = name;
		this.name = this.name.replaceAll("[^\\w]", "");
		this.accessType = accType;
		
		returnType = Type.getReturnType(desc).getClassName();
		
		Type[] argTypes = Type.getArgumentTypes(desc);

		for(Type t:argTypes)
		{
//			argumentTypes.add(t.getClassName());
			argData.add(new ArgumentData(t.getClassName().substring(t.getClassName().lastIndexOf('.') + 1), null));
//			String s = argumentTypes.get(i);
//			builder.append(s.substring(s.lastIndexOf('.') + 1));
		}
		
//		if (signature != null)
//		{
//			String[] args = signature.split(arg0)
//		}
		
		String s = null;
		if(signature != null)
		{
//			Type[] types = Type.get;
//			for (Type t : types)
//			{
//				System.out.println(t.getElementType());
//			}
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
//					fullArgs.addAll(Arrays.asList(str.trim().split(";")));
				}
				else
				{
					if (!str.isEmpty())
					{
						fullArgs.add(str);
					}
				}
			}
			
			//for (String str : fullArgs)
			for (int i = 0; i < fullArgs.size(); i++)
			{
				String str = fullArgs.get(i);
				if (str.contains("<"))
				{
					String[] splitString = str.split("<");
//					if (splitString.length > 2)
//					{
						String temp = splitString[splitString.length - 1];
						ArgumentData tempData = new ArgumentData(temp.substring(temp.lastIndexOf("/") + 1), null);
						for (int x = splitString.length - 2; x > 0; x--)
						{
							ArgumentData secondData = new ArgumentData(splitString[x].substring(temp.lastIndexOf("/") + 1), tempData);
							tempData = secondData;
						}
						argData.get(i).setSubData(tempData);
//					}
					//String temp = splitString[splitString.length - 1];
					//(Ljava/util/ArrayList<Ljava/util/ArrayList<Ljava/lang/Integer
//					argumentGenerics.add(temp.substring(temp.lastIndexOf("/") + 1));
//					argData.get(i).setGenericType(temp.substring(temp.lastIndexOf("/") + 1));
				}
//				else
//				{
//					argumentGenerics.add(null);
//				}
			}
			
			System.out.println(name);
			System.out.println("-----");
			System.out.println(signature);
			System.out.println("-----");
			System.out.println(Type.getType(signature).getReturnType().toString());
			System.out.println("-----");
			System.out.println(s);
			System.out.println("-----\n");
			
			//TODO Maybe looking at return type, then doing this, is better?
			s = Type.getType(signature).getReturnType().toString();
			//if (s.compareTo("V") != 0)
			if (s.contains("<"))
			{
				s = s.substring(s.lastIndexOf('/') + 1, s.indexOf(';'));
			}
			else
			{
				s = null;
			}
		}
//		else
//		{
//			for (int i = 0; i < argumentTypes.size(); i++)
//				argumentGenerics.add(null);
//		}
		returnGenericType = s;
	}
	
	public ArrayList<String> testMethod(ArrayList<ArrayList<Integer>> ints, int l)
	{
		return null;
	}
	
	public int testMethod2(ArrayList<String> l, int m, int o)
	{
		return 0;
	}
	
	/**
	 * This method is for testing purposes.
	 * @param name			Name of the method.
	 * @param accType		The access type of the method (see asm.Opcodes)
	 * @param argumentData	An ArrayList containing the data for the method arguments.
	 * @param genericType	If this method returns a type with a generic, it is specified here. null if no generic type.
	 * @param returnType    The return type of the method.
	 */
	public UMLMethod(String name, int accType, ArrayList<ArgumentData> argumentData, String genericType, String returnType)
	{
		this.name = name;
		this.accessType = accType;
		this.argData = argumentData;
//		this.argumentTypes = argumentTypes;
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
		else if((accessType & Opcodes.ACC_PROTECTED) == Opcodes.ACC_PROTECTED)
		{
			builder.append("# ");
		}
		builder.append(name);
		builder.append("(");
//		for (int i = 0; i < argumentTypes.size(); i++)
		for (int i = 0; i < argData.size(); i++)
		{
//			String s = argumentTypes.get(i);
//			builder.append(s.substring(s.lastIndexOf('.') + 1));
//			if (argumentGenerics.get(i) != null)
//			{
//				builder.append("<");
//				builder.append(argumentGenerics.get(i));
//				builder.append(">");
//			}
			builder.append(argData.get(i).getArgument());
//			if (i < argumentTypes.size() - 1)
			if (i < argData.size() - 1)
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
//	public ArrayList<String> getArgumentTypes() {
//		return argumentTypes;
	public ArrayList<ArgumentData> getArgumentData()
	{
		return argData;
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
