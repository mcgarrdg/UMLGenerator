package problem.asm;

import java.util.ArrayList;
import java.util.Arrays;

public class UMLClass implements IGraphItem{
	
	/**
	 * The full path name of the class. (example: problem/asm/UMLClass)
	 */
	private String fullName;
	
	/**
	 * Just the short name of the class. (example: UMLClass)
	 * Not currently used.
	 */
	private String baseName;
	
	/**
	 * The shape of the graph. See Graphviz shape.
	 */
	private String shape;
	
	/**
	 * The full name of the class that this class extends.
	 */
	private String extension;
	
	/**
	 * A list of UMLMethods that this class has.
	 */
	private ArrayList<UMLMethod> methods;
	
	/**
	 * A list of UMLFields that this class has.
	 */
	private ArrayList<UMLField> fields;
	
	/**
	 * A list of the full names of the classes that this class implements.
	 */
	private ArrayList<String> implementations;

	/**
	 * Constructor.
	 * @param className			The {@link #fullName} of the class.
	 * @param extension			Full name of the class this class extends. See {@link #extension}.
	 * @param implementations	List of the full names of classes that this class implements.
	 */
	public UMLClass(String className, String extension, String[] implementations) 
	{
		methods = new ArrayList<UMLMethod>();
		fields = new ArrayList<UMLField>();
		this.fullName = className;
		this.implementations = new ArrayList<String>();
		this.implementations.addAll(Arrays.asList(implementations));
		this.extension = extension;
		shape = "\"record\"";
	}
	
	/**
	 * Adds a {@link UMLMethod method} to this class.
	 * @param method	Method to be added.
	 */
	public void addMethod(UMLMethod method)
	{
		this.methods.add(method);
	}
	
	/**
	 * Adds a {@link UMLField} field to this class.
	 * @param field	Field to be added.
	 */
	public void addField(UMLField field)
	{
		this.fields.add(field);
	}
	
	/**
	 * Gets the {@link #fullName} of the class that this class extends.
	 * @return	Name of the class that this class extends.
	 */
	public String getExtension()
	{
		return extension;
	}
	
	/**
	 * Gets an {@link ArrayList} of the {@link #fullName}s that this class implements.
	 * @return	ArrayList of names that this class implements.
	 */
	public ArrayList<String> getImplementations()
	{
		return implementations;
	}
	
	/**
	 * Gets the {@link #fullName} of this class.
	 * @return	The fullName of this class.
	 */
	public String getName()
	{
		return this.fullName;
	}
	
	/**
	 * Gets an ArrayList of the {@link UMLMethod}s that this class contains.
	 * @return	An ArrayList of the methods that this class contains.
	 */
	public ArrayList<UMLMethod> getMethods() 
	{
		return this.methods;
	}
	
	/**
	 * Gets an ArrayList of the {@link UMLFields}s that this class contains.
	 * @return	An ArrayList of the fields that this class contains.
	 */
	public ArrayList<UMLField> getFields() 
	{
		return this.fields;
	}
	
	//TODO signify if something is an interface or abstract
	//TODO Have an input for number of tabs?
	public String toGraphVizString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("\"" + this.fullName + "\" [\n\tshape = " + this.shape + ",\n\tlabel = \"{" + this.fullName);
		
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
		builder.append("}\"\n];");
		return builder.toString();
	}
}
