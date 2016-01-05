package problem.asm;

import java.util.ArrayList;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class UMLMethod implements IGraphItem{
	
	private ArrayList<String> argumentTypes;
	private String name;
	private String returnType;
	private int accessType;
	
	public UMLMethod(String name, int accType, String desc)
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
		builder.append(argumentTypes.toString());
		builder.append(") : ");
		builder.append(returnType);
		builder.append("\\l");
		
		
		return builder.toString();
	}
}
