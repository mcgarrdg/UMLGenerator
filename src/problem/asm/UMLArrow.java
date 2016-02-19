package problem.asm;

public class UMLArrow extends UMLGraphItem {
	private final UMLClass startClass;
	private final UMLClass endClass;
	private final String lineType;
	private final String arrowType;
	private String label;

	/**
	 * Creates a new UMLArrow object
	 * 
	 * @param startClassName
	 *            The full name of the class the arrow will start from
	 * @param endClassName
	 *            The full name of the class the arrow will end at
	 * @param arrowHeadType
	 *            The type of arrow head. See GraphViz documentation.
	 * @param lineType
	 *            The type of line for the arrow. See GraphViz documentation.
	 */
	public UMLArrow(UMLClass startClass, UMLClass endClass, String arrowHeadType, String lineType) {
		this.startClass = startClass;
		this.endClass = endClass;
		this.arrowType = arrowHeadType;
		this.lineType = lineType;
		this.label = "";
	}

	@Override
	public String toGraphVizString() {
		//TODO If we don't want to draw arrows to non-active classes, check to see if they arent then dont draw. Maybe this check is better in the UMLClass?
		if(!this.getEndClass().isActive() || !this.getStartClass().isActive())
		{
			return "";
		}
		String returnLabel;
		if (label.isEmpty()) {
			returnLabel = label;
		} else {
			returnLabel = "\", label=\"\\<\\<" + label + "\\>\\>";
		}
		return ("\"" + startClass.getName() + "\" -> \"" + endClass.getName() + "\"" + " [arrowhead=\"" + arrowType
				+ returnLabel + "\", style=\"" + lineType + "\"];\n");
	}

	/**
	 * Tells whether or not this UMLArrow connects two classes in the specified
	 * direction.
	 * 
	 * @param startClass
	 *            Class the arrow would start at.
	 * @param endClass
	 *            Class the arrow would end at.
	 * @return true if this arrow starts at start class and ends at end class.
	 *         false otherwise.
	 */
	public boolean connects(UMLClass startClass, UMLClass endClass) {
		return (this.startClass.equals(startClass) && this.endClass.equals(endClass));
	}

	public boolean isUsesArrow() {
		if (this.arrowType.equals("vee")) {
			if (this.lineType.equals("dashed"))
				return true;
		}
		return false;
	}

	/**
	 * @return true if this is an extends or implements arrow, false otherwise.
	 */
	public boolean extendsOrImplements() {
		if (this.arrowType.equals("onormal")) {
			if (this.lineType.equals("dashed") || this.lineType.equals("") || this.lineType.equals("solid"))
				return true;
		}
		return false;
	}

	/**
	 * @return The class that this arrow starts at.
	 */
	public UMLClass getStartClass() {
		return this.startClass;
	}

	/**
	 * @return THe class that this arrow ends at.
	 */
	public UMLClass getEndClass() {
		return this.endClass;
	}

	public boolean isAssociationArrow() {
		if (this.arrowType.equals("vee")) {
			if (this.lineType.equals("solid"))
				return true;
		}
		return false;
	}

	public void setLabel(String label) {
		if (this.label.isEmpty()) {
			this.label = label;
		}
		else {
			this.label += ","+label;
		}
	}

	public String getLabel() {
		return this.label;
	}
}
