package problem.asm;

import java.util.ArrayList;

public class UMLGraph extends GraphItem
{

	/**
	 * The list of classes that this UMLGraph will contain.
	 */
	private ArrayList<UMLClass> classes;
	
	/**
	 * The name of the UMLGraph.
	 */
	private String name;
	
	/**
	 * The rankdir of the UMLGraph. See Graphviz rankdir documentation.
	 */
	private String rankdir;
	
	public UMLGraph(String name, String rankdir)
	{
		this.name = name;
		this.rankdir = rankdir;
		classes = new ArrayList<UMLClass>();
	}
	
	/**
	 * Adds a class to the UMLGraph.
	 * @param clss	The class to be added.
	 */
	public void addClass(UMLClass clss)
	{
		this.classes.add(clss);
	}
	
	/**
	 * Adds a field to the most recently added class on this graph.
	 * @param field	The field to be added.
	 */
	public void addField(UMLField field)
	{
		this.classes.get(this.classes.size()-1).addField(field);
	}
	
	/**
	 * Adds a method to the most recently added class on this graph.
	 * @param method
	 */
	public void addMethod(UMLMethod method)
	{
		this.classes.get(this.classes.size() - 1).addMethod(method);
	}
	
	@Override
	public String toGraphVizString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("digraph \"" + this.name + "\"{\n\trankdir = " + this.rankdir);
		
		for(UMLClass c : this.classes)
		{
			builder.append("\n\t");
			builder.append(c.toGraphVizString());
		}
		builder.append("\n");

		// Draw arrows
		for(UMLClass firstClass : this.classes)
		{
			for(UMLClass secondClass : this.classes)
			{
				for(String implementation : firstClass.getImplementations())
				{
					if(secondClass.getName().equals(implementation))
					{
						builder.append(getArrowString(firstClass.getName(), implementation, "onormal", "dashed"));
					}
				}
				
				for(UMLField field : firstClass.getFields())
				{
					String type = field.getType().getFullBaseDataType();
					if(secondClass.getName().equals(type))
					{
						builder.append(getArrowString(firstClass.getName(), type, "vee", "solid"));
					}
				}
				
				if(secondClass.getName().equals(firstClass.getExtension()))
				{
					builder.append(getArrowString(firstClass.getName(), firstClass.getExtension(), "onormal", ""));
				}
			}
		}
		builder.append("\n}");
		return builder.toString();
	}
	
	/**
	 * Helper method for {@link #toGraphVizString()} that make a string that specifies an arrow between two classes.
	 * @param nameOne		The fullName of the class the arrow will come from.
	 * @param nameTwo		The fullName of the class the arrow will end at.
	 * @param arrowHeadType	The type of arrow head. See GraphViz documentation.
	 * @param lineType		The type of line for the arrow. See GraphViz documentation.
	 * @return				The GraphViz formatted string drawing an arrow between two classes.
	 */
	private String getArrowString(String nameOne, String nameTwo, String arrowHeadType, String lineType)
	{
		return ("\"" + nameOne + "\" -> \"" + nameTwo + "\"" + " [arrowhead=\"" + arrowHeadType + "\", style=\"" + lineType + "\"];\n");
	}
	
	/**
	 * @return the classes of the graph
	 */
	public ArrayList<UMLClass> getClasses() 
	{
		return classes;
	}

	/**
	 * @return the name of the graph
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * @return the rankdir of the graph
	 */
	public String getRankdir() 
	{
		return rankdir;
	}
}
