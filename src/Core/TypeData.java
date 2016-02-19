package Core;

import Core.UMLItems.UMLGraphItem;

public class TypeData extends UMLGraphItem {
	/**
	 * Just the short name of the class. (Example: TypeData or TypeData[] if it is an array)
	 */
	private String baseName;

	/**
	 * The full path name of the type. (example: problem/asm/TypeData)
	 * The [] are not present if it is an array, this name is simply a path to the class type.
	 */
	private final String fullName;

	/**
	 * A {@link TypeData} object that represents generic types of this object.
	 */
	private TypeData subData;

	/**
	 * Gets whether or not this {@link TypeData} object represents an array. See {@link #isArray}.
	 * @return
	 */
	public boolean isArray() {
		return isArray;
	}

	/**
	 * A boolean indicating if this typedata is an array (the {@link #baseName} has [].)
	 */
	private boolean isArray;

	/**
	 * 
	 * @param type
	 *            The name of the type of data that it is. (Example: TypeData).
	 * @param subData
	 *            Any sub data (generic type) this data has. Null for no
	 *            generics.
	 */
	public TypeData(String type, TypeData subData, String fullName) {
		//TODO Check for [] and declare it as an array or something. Remove the brackets for the type?
		this.baseName = type;
		this.subData = subData;

		if(fullName.contains("[]"))
		{
			this.fullName = fullName.substring(0, fullName.length()-2);
			isArray = true;
		} else {
			this.fullName = fullName;
			isArray = false;
		}
	}

	/**
	 * Sets the {@link #baseName} of this TypeData.
	 * 
	 * @param type
	 *            The name.
	 */
	public void setType(String type) {
		this.baseName = type;
	}

	/**
	 * Returns the {@link #baseName} of this TypeData.
	 * @return
	 */
	public String getBaseName() {
		return this.baseName;
	}

	/**
	 * Sets the {@link #subData} of this TypeData.
	 * 
	 * @param d
	 *            The TypeData to be set as the subData.
	 */
	public void setSubData(TypeData d) {
		this.subData = d;
	}

	/**
	 * Gets the base type of data of this type. In other words, the last type of
	 * generic data associated with this TypeData that has no further generic
	 * types. Example: The baseDataType of ArrayList<LinkedList<String>> would
	 * be String.
	 * 
	 * @return The base data type.
	 */
	public String getBaseDataType() {
		if (this.subData == null) {
			return this.baseName;
		}
		return this.subData.getBaseDataType();
	}

	/**
	 * Finds the full name of the base data type of this typedata. Eg List<List<String>> would return java/lang/String.
	 * 
	 * @return The full name of the base data type.
	 */
	public String getFullBaseDataType() {
		if (this.subData == null) {
			return this.fullName;
		}
		return this.subData.getFullBaseDataType();
	}
	
	/**
	 * Gets The name of this type, appended with all subtypes. Eg "ArrayList<String>"
	 * @return
	 */
	public String getExtendedName()
	{
		if(this.subData == null)
		{
			return this.baseName;
		}
		return this.baseName + "<" + this.subData.getExtendedName() + ">";
	}

	/**
	 * Gets the {@link #fullName} of this TypeData.
	 * @return
	 */
	public String getFullName() {
		return this.fullName;
	}

	@Override
	public String toGraphVizString() {
		if (subData != null) {
			return (this.baseName + "\\<" + subData.toGraphVizString() + "\\>");
		}
		return this.baseName;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}

		if (this.getClass() != other.getClass()) {
			return false;
		}
		TypeData oD = ((TypeData) other);

		if (!this.fullName.equals(oD.fullName)) {
			return false;
		}
		if (this.subData == null) {
			return oD.subData == null;
		}
		return this.subData.equals(oD.subData);
	}

}
