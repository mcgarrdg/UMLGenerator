package Core.UMLItems;

import java.util.ArrayList;
import java.util.Arrays;

import Core.TypeData;
import org.objectweb.asm.Opcodes;

//TODO Move all of the things that build the GraphViz crap into its own class???
public class UMLClass extends UMLGraphItem {

	/**
	 * The full path name of the class. (example: problem/asm/UMLClass)
	 */
	private String fullName;

	/**
	 * Just the short name of the class. (example: UMLClass) Not currently used.
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
	 * The access type of this field (public, private, etc), as an integer
	 * value. See {@link Opcodes}.
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
	 * A string representing the color of the box on the graphViz.
	 * The string is in the format #ffffff where that is the
	 * hexidecimal representation of an RBG value.
	 */
	private String color;
	
	/**
	 * A list of the full names of the classes that this class implements.
	 */
	private ArrayList<String> implementations;

	/**
	 * Stores all of the UMLArrows originating from this class. Populated by the
	 * {@link #generateArrows(ArrayList)} method.
	 */
	private ArrayList<UMLArrow> arrows;
	
	/**
	 * The default color for classes.
	 */
	public static String DEFAULT_LINE_COLOR = "#000000";

	/**
	 * The color that this class box will be filled with in GraphViz.
	 */
	private String fillColor;

	public String getFillColor() {
		return fillColor;
	}

	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}

	/**
	 * The default color for the color of the box.
	 */
	public static String DEFAULT_FILL_COLOR = "#ffffff";
	
	/**
	 * Some predefined color strings for easy coloring.
	 */
	public static final String COLOR_BLUE = "#0000ff";
	public static final String COLOR_BLACK = "#000000";
	
	/**
	 * A list of all of the pattern names that should be appended after the class name in the UML Diagram.
	 * AKA Names of all of the specific patterns that this class is
	 */
	public ArrayList<String> patternNames; //TODO Should really be private, but tests use it currently.

	public ArrayList<String> getPatternCatagories() {
		return patternCatagories;
	}

	/**
	 * A list of all the pattern names that this class is a part of.
	 */
	private ArrayList<String> patternCatagories;

	public void addPatternCatagory(String name)
	{

		if(!this.patternCatagories.contains(name)) {
			this.patternCatagories.add(name);
		}
	}


	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	private boolean isActive;

	/**
	 * Constructor.
	 *
	 * @param className
	 *            The {@link #fullName} of the class.
	 * @param extension
	 *            Full name of the class this class extends. See
	 *            {@link #extension}.
	 * @param access
	 *            The {@link #accessType} of this class.
	 * @param implementations
	 *            List of the full names of classes that this class implements.
	 */
	public UMLClass(String className, String extension, int access, String[] implementations) {
		methods = new ArrayList<UMLMethod>();
		fields = new ArrayList<UMLField>();
		this.fullName = className;
		this.implementations = new ArrayList<String>();
		this.implementations.addAll(Arrays.asList(implementations));
		this.accessType = access;
		this.extension = extension;
		this.arrows = new ArrayList<UMLArrow>();
		this.patternNames = new ArrayList<String>();
		this.color = UMLClass.COLOR_BLACK;
		this.fillColor = UMLClass.DEFAULT_FILL_COLOR;
		shape = "\"record\"";
		this.setActive(true);
		patternCatagories = new ArrayList<>();
	}

	/**
	 * Adds a {@link UMLMethod method} to this class.
	 * 
	 * @param method
	 *            Method to be added.
	 */
	public void addMethod(UMLMethod method) {
		method.setFullOwnerName(this.fullName);
		this.methods.add(method);
	}

	/**
	 * Adds a class signifying that it has been used in the most recently added
	 * method.
	 * 
	 * @param fullClassName
	 *            The full name of the class that was used.
	 */
	public void addUsedClassToMethod(String fullClassName) {
		this.methods.get(methods.size() - 1).addUsedClass(fullClassName);
	}

	/**
	 * Adds a {@link UMLField} field to this class.
	 * 
	 * @param field
	 *            Field to be added.
	 */
	public void addField(UMLField field) {
		this.fields.add(field);
	}

	/**
	 * Gets the {@link #fullName} of the class that this class extends.
	 * 
	 * @return Name of the class that this class extends.
	 */
	public String getExtension() {
		return extension;
	}
	
	/**
	 * Sets the color that this class should be.
	 * @param color	A string representing the hexidecimal value of the color. See {@link #color}.
	 */
	public void setColor(String color)
	{
		this.color = color;
	}

	/**
	 * Gets an {@link ArrayList} of the {@link #fullName}s that this class
	 * implements.
	 * 
	 * @return ArrayList of names that this class implements.
	 */
	public ArrayList<String> getImplementations() {
		return implementations;
	}

	/**
	 * Gets the {@link #fullName} of this class.
	 * 
	 * @return The fullName of this class.
	 */
	public String getName() {
		return this.fullName;
	}

	/**
	 * Gets an ArrayList of the {@link UMLMethod}s that this class contains.
	 * 
	 * @return An ArrayList of the methods that this class contains.
	 */
	public ArrayList<UMLMethod> getMethods() {
		return this.methods;
	}

	/**
	 * Gets an ArrayList of the {@link UMLField}s that this class contains.
	 * 
	 * @return An ArrayList of the fields that this class contains.
	 */
	public ArrayList<UMLField> getFields() {
		return this.fields;
	}
	
	/**
	 * Adds the specified pattern name to the list of pattern names that this class has.
	 * @param name	Name of the pattern.
	 */
	public void addPatternName(String name)
	{
		if(!this.patternNames.contains(name)) {
			this.patternNames.add(name);
		}
	}
	
	/**
	 * Gets a list of all the pattern names that this class is.
	 * @return	ArrayList of the pattern names.
	 */
	public ArrayList<String> getPatternNames()
	{
		return this.patternNames;
	}
	
	public int getAccessType() {
		return this.accessType;
	}

	/**
	 * Should be called after all classes have been parsed. This generates the
	 * different arrow types. connecting the classes in the UML.
	 * 
	 * @param classes
	 *            A list of all classes in the UML.
	 */
	public void generateArrows(ArrayList<UMLClass> classes) {
		for (UMLClass secondClass : classes) {
			// Check for implements arrows
			for (String implementation : this.implementations) {
				if (secondClass.getName().equals(implementation)) {
					this.addArrow(secondClass, "onormal", "dashed");
				}
			}

			// Check for extends arrows
			if (secondClass.getName().equals(this.extension)) {
				this.addArrow(secondClass, "onormal", "");
			}

			// Check for association arrows
			// Keep in mind, if we do the diamonds this is technically
			// backwards.
			for (UMLField field : this.fields) {
				String type = field.getType().getFullBaseDataType();
				if (secondClass.getName().equals(type)) {
					this.addArrow(secondClass, "vee", "solid");
//					System.out.println("association from " + this.getName() + " " + type );
				}
			}
		}

		// Check for uses arrows
		for (UMLMethod meth : this.methods) {
			ArrayList<UMLClass> classUsedList = new ArrayList<UMLClass>();
			for (TypeData d : meth.getClassesUsed()) {
				for (UMLClass c : classes) {
					if (c.getName().equals(d.getFullBaseDataType())) {
						classUsedList.add(c);
					}
				}
			}
			for (UMLClass cls : classUsedList) {
				this.addArrow(cls, "vee", "dashed");
			}
		}
	}

	/**
	 * This method is only useful if the graph has been built, and
	 * {@link #generateArrows(ArrayList)} has been called on all classes. This
	 * method removes any arrows that point to classes that are super classes of
	 * classes that this class has arrows to.
	 */
	public void removeExtraArrows() {
		ArrayList<UMLClass> pointedTo = new ArrayList<UMLClass>();
		for (UMLArrow arrow : this.arrows) {
			pointedTo.add(arrow.getEndClass());
		}

		// If the used class is in a field, ignore it.
		for (UMLClass firstClass : new ArrayList<UMLClass>(pointedTo)) {
			for (UMLClass secondClass : new ArrayList<UMLClass>(pointedTo)) {
				if (firstClass.checkExtendsOrImplements(secondClass)) {
					pointedTo.remove(secondClass);
				} else if (secondClass.checkExtendsOrImplements(firstClass)) {
					pointedTo.remove(firstClass);
				}
			}
		}

		// Add back in extends/implements arrows
		for (UMLArrow arrow : this.arrows) {
			if (arrow.extendsOrImplements()) {
				pointedTo.add(arrow.getEndClass());
			}
		}

		for (UMLArrow arrow : new ArrayList<UMLArrow>(this.arrows)) {
			if ((!pointedTo.contains(arrow.getEndClass())) && arrow.isUsesArrow()) {
				this.arrows.remove(arrow);
			}
		}
	}

	/**
	 * This should be called after removeExtraArrows is called on everything
	 */
	// TODO Move these methods into UMLGraph where it makes more sense?
	// TODO Have a seperate array stored for all of the arrows?
	public void removeRedundantUsesArrows() {
		ArrayList<UMLClass> extendsOrImplements = this.getAllExtendsOrImplements();
		ArrayList<UMLClass> thisUsed = this.getAllUsedClasses();

		// Go through everything this class extends or implements (directly or
		// indirectly)
		for (UMLClass extendedClass : extendsOrImplements) {
			ArrayList<UMLClass> otherUsed = extendedClass.getAllUsedClasses();
			// Go through all of the classes that this class uses
			for (UMLClass usedClass : thisUsed) {
				boolean removeArrow = true;
				// If the other class uses the same thing as this class, check
				// to see
				// if all instances where that is used is the same in both
				// classes
				// or not used in this class. If so, remove the arrow from this
				// class.
				if (otherUsed.contains(usedClass)) {
					// Check if the used class is within a field
					boolean foundField = false;
					for (UMLField field : this.getFields()) {
						if (field.getType().getFullBaseDataType().equals(usedClass.getName())) {
							foundField = true;
							removeArrow = false;
						}
					}
					// If the used class is in a field, don't remove the arrow,
					// so skip the arrow checks.
					if (!foundField) {
						// Check everything for type, and if they are the same
						// in the two
						// If everything is the same, remove this class' used
						// arrow for that thing
						for (UMLMethod thisMethod : this.methods) {
							boolean wasUsed = false;
							// Check to see if this method uses the used class
							// at all
							for (TypeData d : thisMethod.getClassesUsed()) {
								if (d.getFullBaseDataType().equals(usedClass.getName())) {
									wasUsed = true;
									break;
								}
							}
							// The class was used in the method. Check to see if
							// there is an identical method in the other.
							if (wasUsed) {
								boolean isSame = false;
								for (UMLMethod extendedMethod : extendedClass.getMethods()) {
									// If the methods have the same signature
									if (thisMethod.sameSignature(extendedMethod)) {
										isSame = true;
										break;
									}
								}
								if (!isSame) {
									removeArrow = false;
									break;
								}
							}
						}
					}
					if (removeArrow) {
						for (UMLArrow arrow : new ArrayList<UMLArrow>(this.arrows)) {
							if (arrow.isUsesArrow() && arrow.getEndClass().getName().equals(usedClass.getName())) {
								this.arrows.remove(arrow);
								break;
							}
						}
					}
				}
			}
		}
	}

	public ArrayList<UMLClass> getAllUsedClasses() {
		ArrayList<UMLClass> usedClasses = new ArrayList<UMLClass>();
		for (UMLArrow arrow : this.arrows) {
			if (arrow.isUsesArrow()) {
				usedClasses.add(arrow.getEndClass());
			}
		}
		return usedClasses;
	}

	public ArrayList<UMLClass> getAllExtendsOrImplements() {
		ArrayList<UMLClass> finalList = new ArrayList<UMLClass>();
		ArrayList<UMLClass> extendsOrImplements = new ArrayList<UMLClass>();

		for (UMLArrow arrow : this.arrows) {
			if (arrow.extendsOrImplements()) {
				extendsOrImplements.add(arrow.getEndClass());
			}
		}

		for (UMLClass uClass : extendsOrImplements) {
			finalList.addAll(uClass.getAllExtendsOrImplementsHelper());
		}

		return finalList;
	}

	private ArrayList<UMLClass> getAllExtendsOrImplementsHelper() {
		ArrayList<UMLClass> finalList = new ArrayList<UMLClass>();
		ArrayList<UMLClass> extendsOrImplements = new ArrayList<UMLClass>();

		for (UMLArrow arrow : this.arrows) {
			if (arrow.extendsOrImplements()) {
				extendsOrImplements.add(arrow.getEndClass());
			}
		}

		for (UMLClass uClass : extendsOrImplements) {
			ArrayList<UMLClass> temp = uClass.getAllExtendsOrImplementsHelper();
			for (UMLClass uClass2 : temp) {
				if (!finalList.contains(uClass2)) {
					finalList.add(uClass2);
				}
			}
		}

		finalList.add(this);
		return finalList;
	}

	/**
	 * This method is only useful if the graph has been built, and
	 * {@link #generateArrows(ArrayList)} has been called on all classes.
	 * 
	 * @param c
	 *            The class to check if this class (directly or indirectly)
	 *            extends or implements.
	 * @return Whether or not this class extends or implements c.
	 */
	public boolean checkExtendsOrImplements(UMLClass c) {
		if ((this.extension == null || this.extension.isEmpty()) && this.implementations.isEmpty()) {
			return false;
		}

		ArrayList<UMLClass> extImpl = new ArrayList<UMLClass>();
		for (UMLArrow arrow : this.arrows) {
			if (arrow.extendsOrImplements()) {
				extImpl.add(arrow.getEndClass());
			}
		}

		for (UMLClass cls : extImpl) {
			if (cls.getName().equals(c.getName())) {
				return true;
			} else if (cls.checkExtendsOrImplements(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method is only useful if the graph has been built, and
	 * {@link #generateArrows(ArrayList)} has been called on all classes.
	 * 
	 * @return The ArrayList of UMLArrows that come from this class.
	 */
	public ArrayList<UMLArrow> getUMLArrows() {
		return new ArrayList<UMLArrow>(this.arrows);
	}

	/**
	 * Helper method for {@link #toGraphVizString()}. Adds an arrow to an
	 * ArrayList if there isn't an arrow that already connects the two.
	 * 
	 * @param arrows
	 *            ArrayList of UMLArrows that the arrow (may) be added to.
	 * @param firstName
	 *            Full name of the class the arrow will start from.
	 * @param secondName
	 *            Full name of the class the arrow will end at.
	 * @param arrowType
	 *            The type of arrowhead. See GraphViz documentation.
	 * @param lineType
	 *            The type of line. See GraphViz documentation.
	 */
	private void addArrow(UMLClass end, String arrowType, String lineType) {
		// TODO Instead of just checking to see if the arrow exists, check the priority of the arrow.
		// eg association overwrites uses so it's no longer dependant on what arrows we make first.
		boolean isConnected = false;
		for (UMLArrow arrow : arrows) {
			if (arrow.connects(this, end)) {
//				if(		(arrowType.equals("onormal") && arrow.extendsOrImplements()) {
//						||
//						(arrowType.equals("vee") && lineType.equals("dashed")) ||
//						(arrow.isAssociationArrow() && arrowType.equals("vee") && lineType.equals("dashed"))) {
					isConnected = true;
					break;
//				}
			}
		}
		if (!isConnected) {
			arrows.add(new UMLArrow(this, end, arrowType, lineType));
		}
	}

	private static int MAX_ROWS = 10;
	// TODO Have an input for number of tabs?
	@Override
	public String toGraphVizString() {
		StringBuilder builder = new StringBuilder();
		int i = 0;

		builder.append("\"");
		builder.append(this.fullName);
		builder.append("\" [\n\tshape = ");
		builder.append(this.shape);
		builder.append(",\n\tlabel = \"{");
		// TODO Change for other types of classes.
		if ((this.accessType & Opcodes.ACC_INTERFACE) == Opcodes.ACC_INTERFACE) {
			builder.append("\\<\\<");
			builder.append(this.fullName);
			builder.append("\\>\\>");
		} else {
			builder.append(this.fullName);
		}
		
		for(String s : this.patternNames)
		{
			builder.append("\\n\\<\\<");
			builder.append(s);
			builder.append("\\>\\>");
		}

		if (this.fields.size() != 0 || this.methods.size() != 0) { //TODO Shouldn't this always do this?
			builder.append("|");
		}
		i = 0;
		for (UMLField f : this.fields) {
			if(i > MAX_ROWS) {
				builder.append("...");
				break;
			}
			builder.append(f.toGraphVizString());
			i++;
		}
		builder.append("|");
		i = 0;
		for (UMLMethod m : this.methods) {
			if(i > MAX_ROWS) {
				builder.append("...");
				break;
			}
			builder.append(m.toGraphVizString());
			i++;
		}
		builder.append("}\"\nstyle=filled\nfillcolor=\"");
		builder.append(this.fillColor);
		builder.append("\"");
		builder.append("\ncolor=\"").append(this.color).append("\"\n];\n");
		for (UMLArrow arrow : this.arrows) {
			builder.append(arrow.toGraphVizString());
		}
		return builder.toString();
	}

	public void addUsedMethodToMethod(String owner, String name, String desc) {
		this.methods.get(methods.size() - 1).addUsedMethodToMethod(owner, name, desc);
	}
}
