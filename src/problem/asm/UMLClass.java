package problem.asm;

import java.util.ArrayList;
import java.util.Arrays;

import org.objectweb.asm.Opcodes;

public class UMLClass extends UMLGraphItem{
	
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
	 * The access type of this field (public, private, etc), as an integer value. See {@link Opcodes}.
	 */
	private int accessType;
	
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
	 * Stores all of the UMLArrows originating from this class.
	 * Populated by the {@link #generateArrows(ArrayList)} method.
	 */
	private ArrayList<UMLArrow> arrows;

	/**
	 * Constructor.
	 * @param className			The {@link #fullName} of the class.
	 * @param extension			Full name of the class this class extends. See {@link #extension}.
	 * @param access			The {@link #accessType} of this class. 
	 * @param implementations	List of the full names of classes that this class implements.
	 */
	public UMLClass(String className, String extension, int access, String[] implementations) 
	{
		methods = new ArrayList<UMLMethod>();
		fields = new ArrayList<UMLField>();
		this.fullName = className;
		this.implementations = new ArrayList<String>();
		this.implementations.addAll(Arrays.asList(implementations));
		this.accessType = access;
		this.extension = extension;
		this.arrows = new ArrayList<UMLArrow>();
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
	 * Adds a class signifying that it has been used in the most recently added method.
	 * @param fullClassName	The full name of the class that was used.
	 */
	public void addUsedClassToMethod(String fullClassName)
	{
		this.methods.get(methods.size() - 1).addUsedClass(fullClassName);
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
	
	/**
	 * Should be called after all classes have been parsed. This generates the different arrow types
	 * connecting the classes in the UML.
	 * @param classes	A list of all classes in the UML.
	 */
	public void generateArrows(ArrayList<UMLClass> classes)
	{
		for(UMLClass secondClass : classes)
		{
			//Check for implements arrows
			for(String implementation : this.implementations)
			{
				if(secondClass.getName().equals(implementation))
				{
					this.addArrow(secondClass, "onormal", "dashed");
				}
			}
			
			//Check for extends arrows
			if(secondClass.getName().equals(this.extension))
			{
				this.addArrow(secondClass, "onormal", "");
			}
			
			//TODO Are these technically backwards?
			//Check for association arrows
			for(UMLField field : this.fields)
			{
				String type = field.getType().getFullBaseDataType();
				if(secondClass.getName().equals(type))
				{
					this.addArrow(secondClass, "vee", "solid");
				}
			}
		}
		
		//Check for uses arrows
		for(UMLMethod meth : this.methods)
		{
			ArrayList<UMLClass> classUsedList = new ArrayList<UMLClass>();
			for(TypeData d : meth.getClassesUsed())
			{
				for(UMLClass c : classes)
				{
					if(c.getName().equals(d.getFullBaseDataType()))
					{
						classUsedList.add(c);
					}
				}
			}
			for(UMLClass cls : classUsedList)
			{
				this.addArrow(cls, "vee", "dashed");
			}
		}
	}
	
	/**
	 * This method is only useful if the graph has been built, and {@link #generateArrows(ArrayList)} has been called on all classes.
	 * This method removes any arrows that point to classes that are super classes of classes that this class has arrows to.
	 */
	public void removeExtraArrows()
	{
		ArrayList<UMLClass> pointedTo = new ArrayList<UMLClass>();
		for(UMLArrow arrow : this.arrows)
		{
			pointedTo.add(arrow.getEndClass());
		}
		
		for(UMLClass firstClass : new ArrayList<UMLClass>(pointedTo))
		{
			for(UMLClass secondClass : new ArrayList<UMLClass>(pointedTo))
			{
				if(firstClass.checkExtendsOrImplements(secondClass))
				{
					pointedTo.remove(secondClass);
				}
				else if(secondClass.checkExtendsOrImplements(firstClass))
				{
					pointedTo.remove(firstClass);
				}
			}
		}
		
		for(UMLArrow arrow : this.arrows)
		{
			if(arrow.extendsOrImplements())
			{
				pointedTo.add(arrow.getEndClass());
			}
		}
		
		for(UMLArrow arrow : new ArrayList<UMLArrow>(this.arrows))
		{
			if(!pointedTo.contains(arrow.getEndClass()))
			{
				this.arrows.remove(arrow);
			}
		}
	}
	
	/**
	 * This method is only useful if the graph has been built, and {@link #generateArrows(ArrayList)} has been called on all classes.
	 * @param c	The class to check if this class (directly or indirectly) extends or implements.
	 * @return	Whether or not this class extends or implements c.
	 */
	public boolean checkExtendsOrImplements(UMLClass c)
	{
		if((this.extension == null || this.extension.isEmpty()) && this.implementations.isEmpty())
		{
			return false;
		}
		
		ArrayList<UMLClass> extImpl = new ArrayList<UMLClass>();
		for(UMLArrow arrow : this.arrows)
		{
			if(arrow.extendsOrImplements())
			{
				extImpl.add(arrow.getEndClass());
			}
		}
		
		for(UMLClass cls : extImpl)
		{
			if(cls.getName().equals(c.getName()))
			{
				return true;
			}
			else if(cls.checkExtendsOrImplements(c))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method is only useful if the graph has been built, and {@link #generateArrows(ArrayList)} has been called on all classes.
	 * @return The ArrayList of UMLArrows that come from this class.
	 */
	public ArrayList<UMLArrow> getUMLArrows()
	{
		return new ArrayList<UMLArrow>(this.arrows);
	}
	
	/**
	 * Helper method for {@link #toGraphVizString()}. Adds an arrow to an ArrayList if there isn't an arrow that already connects the two.
	 * @param arrows		ArrayList of UMLArrows that the arrow (may) be added to.
	 * @param firstName		Full name of the class the arrow will start from.
	 * @param secondName	Full name of the class the arrow will end at.
	 * @param arrowType		The type of arrowhead. See GraphViz documentation.
	 * @param lineType		The type of line. See GraphViz documentation.
	 */
	private void addArrow(UMLClass end, String arrowType, String lineType)
	{
		//TODO Instead of just checking to see if the arrow exists, check the priority of the arrow.
		//eg association overwrites uses
		boolean isConnected = false;
		for(UMLArrow arrow : arrows)
		{
			if(arrow.connects(this, end))
			{
				isConnected = true;
				break;
			}
		}
		if(!isConnected)
		{
			arrows.add(new UMLArrow(this, end, arrowType, lineType));
		}
	}
	
	//TODO Have an input for number of tabs?
	@Override
	public String toGraphVizString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("\"" + this.fullName + "\" [\n\tshape = " + this.shape + ",\n\tlabel = \"{");
		//TODO Change for other types of classes.
		if ((this.accessType & Opcodes.ACC_INTERFACE) == Opcodes.ACC_INTERFACE)
		{
			builder.append("\\<\\<" + this.fullName + "\\>\\>");
		}
		else
		{
			builder.append(this.fullName);
		}
		
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
