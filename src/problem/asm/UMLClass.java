package problem.asm;

import java.util.ArrayList;
import java.util.Arrays;

public class UMLClass implements IGraphItem{
	
	private String name;
	private String shape;
	private String extension;
	private ArrayList<UMLMethod> methods;
	private ArrayList<UMLField> fields;
	private ArrayList<String> implementations;

	public UMLClass(String className, String extension, String[] implementations) {
		methods = new ArrayList<UMLMethod>();
		fields = new ArrayList<UMLField>();
		this.name = className;
		this.implementations = new ArrayList<String>();
		this.implementations.addAll(Arrays.asList(implementations));
		this.extension = extension;
		shape = "\"record\"";
	}
	
	public void addMethod(UMLMethod method)
	{
		this.methods.add(method);
	}
	
	public void addField(UMLField field)
	{
		this.fields.add(field);
	}
	
	public String getExtension()
	{
		return extension;
	}
	
	public ArrayList<String> getImplementations()
	{
		return implementations;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public ArrayList<UMLMethod> getMethods() {
		return this.methods;
	}
	
	public ArrayList<UMLField> getFields() {
		return this.fields;
	}
	//TODO signify if something is an interface or abstract
	//TODO Have an input for number of tabs?
	public String toGraphVizString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("\"");
		builder.append(this.name);
		builder.append("\"");
		builder.append(" [\n");
		builder.append("\tshape = ");
		builder.append(shape);
		builder.append(",\n");
		builder.append("\tlabel = \"{");
		builder.append(this.name);
		
		if(this.fields.size() != 0 || this.methods.size() != 0)
		{
			builder.append("|");
		}
		
		for(UMLField f : this.fields)
		{
			builder.append(f.toGraphVizString());
		}
		builder.append("|");
		for(UMLMethod m : this.methods)
		{
			builder.append(m.toGraphVizString());
		}
		builder.append("}\"\n");
		builder.append("];");
		
		return builder.toString();
	}
}
