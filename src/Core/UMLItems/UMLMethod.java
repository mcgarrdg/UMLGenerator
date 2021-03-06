package Core.UMLItems;

import java.util.ArrayList;
import java.util.Arrays;

import Core.SD.SDGraphMethodData;
import Core.TypeData;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class UMLMethod extends UMLGraphItem {

	/**
	 * An ArrayList of {@link TypeData} objects that represent all of the data
	 * for the arguments of this method.
	 */
	private ArrayList<TypeData> argData;

	/**
	 * The name of this method.
	 */
	private String name;

	/**
	 * A {@link TypeData} object representing all of the data for the return
	 * type for this method.
	 */
	private TypeData returnType;

	/**
	 * The access type of this field (public, private, etc), as an integer
	 * value. See {@link Opcodes}.
	 */
	private int accessType;

	/**
	 * Stores all of the types of classes that are used in this method.
	 */
	private ArrayList<TypeData> usedClasses;

	private String fullOwnerName;

	private ArrayList<UMLMethod> methodCalls;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            The {@link #name} of this method.
	 * @param accType
	 *            The {@link #accessType} of this method.
	 * @param desc
	 *            The description string. This is from an asm visitor.
	 * @param signature
	 *            The signature string. This is used to look at generic types of
	 *            the field. From an asm visitor.
	 */
	public UMLMethod(String name, int accType, String desc, String signature) {
		argData = new ArrayList<TypeData>();
		this.name = name;
		this.name = this.name.replaceAll("[^\\w]", "");
		this.accessType = accType;
		this.usedClasses = new ArrayList<TypeData>();
		this.methodCalls = new ArrayList<UMLMethod>();
		this.fullOwnerName = null;

		String retType = Type.getReturnType(desc).getClassName().replace('.', '/');
		returnType = new TypeData(retType.substring(retType.lastIndexOf('/') + 1), null, retType);

		Type[] argTypes = Type.getArgumentTypes(desc);

		for (Type t : argTypes) {
			argData.add(new TypeData(t.getClassName().substring(t.getClassName().lastIndexOf('.') + 1), null,
					t.getClassName().replace('.', '/')));
		}

		if (signature != null) {
			String s;
			// Split the arguments from the return type, then split the
			// arguments apart based on generics.
			String[] argList = signature.split("\\)")[0].split("\\>\\;");
			ArrayList<String> fullArgs = new ArrayList<String>();
			// Split the arguments more.
			for (String str : argList) {
				fullArgs.addAll(Arrays.asList(str.trim().split(";")));
			}
			fullArgs.removeAll(Arrays.asList("", " ", null));

			// Deal with the argument data.
			for (int i = 0; i < fullArgs.size(); i++) {
				// TODO Check if something breaks here
				if (i == argData.size()) {
					break;
				}
				s = fullArgs.get(i);
				if (s.contains("<")) {
					argData.get(i).setSubData(parseGenerics(s));

				}
			}

			// Deal with the return type data.
			s = Type.getType(signature).getReturnType().toString();
			s = s.substring(0, s.length() - 1);
			if (s.contains("<")) {
				this.returnType.setSubData(parseGenerics(s));
			}
		}

		for (TypeData d : this.argData) {
			if (!this.usedClasses.contains(d)) {
				this.usedClasses.add(d);
			}
		}
		if (!this.usedClasses.contains(this.returnType)) {
			this.usedClasses.add(this.returnType);
		}
	}

	/**
	 * This method is for testing purposes.
	 * 
	 * @param name
	 *            Name of the method.
	 * @param accType
	 *            The access type of the method (see asm.Opcodes)
	 * @param argumentData
	 *            An ArrayList containing the data for the method arguments.
	 * @param returnType
	 *            The return type of the method.
	 */
	public UMLMethod(String name, int accType, ArrayList<TypeData> argumentData, TypeData returnType) {
		this.name = name;
		this.accessType = accType;
		this.argData = argumentData;
		this.returnType = returnType;
		this.usedClasses = new ArrayList<TypeData>();
		this.methodCalls = new ArrayList<UMLMethod>();
		this.fullOwnerName = null;

	}

	/**
	 * Used to add a class to this method that has been used.
	 * 
	 * @param fullClassName
	 *            The full name of the class that was used.
	 */
	public void addUsedClass(String fullClassName) {
		TypeData type = new TypeData(fullClassName.substring(fullClassName.lastIndexOf('/') + 1), null, fullClassName);
		if (!this.usedClasses.contains(type)) {
			this.usedClasses.add(type);
		}
	}

	/**
	 * Used to parse out the generics of a string. A helper function for
	 * UMLMethod constructor.
	 * 
	 * @param s
	 *            The string to parse.
	 * @return A {@link TypeData} object that represents the fully parsed type,
	 *         with generics.
	 */
	private TypeData parseGenerics(String s) {
		String[] splitString = s.split("<");
		String temp = splitString[splitString.length - 1];
		TypeData data = null;// = new TypeData(temp.substring(temp.lastIndexOf("/") + 1), null, temp.substring(1));
		if(temp.contains("/"))
		{
			data = new TypeData(temp.substring(temp.lastIndexOf("/") + 1), null, temp.substring(1));
			for (int x = splitString.length - 2; x > 0; x--) {
//				System.out.println(x);
//				System.out.println(temp.lastIndexOf("/"));
//				System.out.println(temp);
//				System.out.println(splitString[x]);
//				String first = splitString[x].substring(temp.lastIndexOf("/") + 1);
//				String last = splitString[x].substring(1);
//				data = new TypeData(splitString[x].substring(temp.lastIndexOf("/") + 1), data, splitString[x].substring(1));
				data = new TypeData(splitString[x].substring(splitString[x].lastIndexOf("/") + 1), data, splitString[x].substring(1));
			}
		}
		else if(temp.contains("*")) //Special case.
		{
			data = new TypeData("*", null, "*");
		} else
		{
			//Some odd generic type that we don't account for
		}
		return data;
	}

	@Override
	public String toGraphVizString() {
		StringBuilder builder = new StringBuilder();

		builder.append(this.getAccessTypeSymbol(this.accessType));
		builder.append(" ");
		builder.append(this.name);
		builder.append("(");
		for (int i = 0; i < argData.size(); i++) {
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
	public ArrayList<TypeData> getArgumentData() {
		return new ArrayList<TypeData>(argData);
	}

	/**
	 * @return the {@link #name} of the method
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the {@link #returnType} of the method
	 */
	public TypeData getReturnType() {
		return this.returnType;
	}

	/**
	 * @return the {@link #accessType} of the method
	 */
	public int getAccessType() {
		return accessType;
	}

	/**
	 * @return ArrayList of TypeData containing the classes used in this method
	 */
	public ArrayList<TypeData> getClassesUsed() {
		return new ArrayList<TypeData>(this.usedClasses);
	}

	/**
	 * Checks to see if two UMLMethods have the same signature. (Same arguments,
	 * same return, same name)
	 * 
	 * @param other
	 * @return
	 */
	// Should I check the classes used here? I currently do not.
	public boolean sameSignature(UMLMethod other) {
		if (other == null) {
			return false;
		}

		if (!this.name.equals(other.name)) {
			return false;
		}

		if (!this.returnType.equals(other.returnType)) {
			return false;
		}

		if (this.argData.size() != other.argData.size()) {
			return false;
		}

		for (int i = 0; i < this.argData.size(); i++) {
			if (!this.argData.get(i).equals(other.argData.get(i))) {
				return false;
			}
		}
		return true;
	}

	public boolean sameFullQualifiedSignature(UMLMethod other) {
		if (other == null) {
			return false;
		}

		if (!this.name.equals(other.name)) {
			return false;
		}

		if (!this.fullOwnerName.equals(other.fullOwnerName)) {
			return false;
		}

		if (this.argData.size() != other.argData.size()) {
			return false;
		}

		for (int i = 0; i < this.argData.size(); i++) {
			if (!this.argData.get(i).getBaseName().equals(other.argData.get(i).getBaseName())) {
				return false;
			}
		}

		return true;
	}
	
	public SDGraphMethodData toSDGraphMethodData()
	{
		return new SDGraphMethodData(
				this.fullOwnerName.substring(this.fullOwnerName.lastIndexOf('/') + 1),
				this.name, this.fullOwnerName.substring(this.fullOwnerName.lastIndexOf('/') + 1), 
				this.returnType.getExtendedName(), this.getArgumentData());
	}
	
	public SDGraphMethodData toSDGraphMethodData(UMLMethod prevLevelMeth)
	{
		return new SDGraphMethodData(
				prevLevelMeth.fullOwnerName.substring(prevLevelMeth.fullOwnerName.lastIndexOf('/') + 1),
				this.name, this.fullOwnerName.substring(this.fullOwnerName.lastIndexOf('/') + 1), 
				this.returnType.getExtendedName(), this.getArgumentData());
	}

	public void setFullOwnerName(String owner) {
		this.fullOwnerName = owner;
	}

	public String getFullownerName() {
		return this.fullOwnerName;
	}

	public void addUsedMethodToMethod(String owner, String name, String desc) {
		//TODO Fix this so it is better.
		// Note, we don't care what the access type of the method is for the
		// sequence diagram, so I just always pass in public. We also do not care 
		// about the signature, so it is just null.
		UMLMethod newMethod = new UMLMethod(name, Opcodes.ACC_PUBLIC, desc, null);
		newMethod.fullOwnerName = owner;

		this.methodCalls.add(newMethod);
	}

	public ArrayList<UMLMethod> getMethodCalls() {
		return new ArrayList<UMLMethod>(this.methodCalls);
	}

}
