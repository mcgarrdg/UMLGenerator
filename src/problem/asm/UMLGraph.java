package problem.asm;

import java.util.ArrayList;

public class UMLGraph extends UMLGraphItem
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
	
	/**
	 * Adds a class that has been used in a method to the most recently added method.
	 * @param fullClassName	Full name of used class.
	 */
	public void addClassUsedToMethod(String fullClassName)
	{
		this.classes.get(this.classes.size() - 1).addUsedClassToMethod(fullClassName);
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
		
		for(UMLClass firstClass : this.classes)
		{
			firstClass.generateArrows(this.classes);
		}
		for(UMLClass firstClass : this.classes)
		{
			firstClass.removeExtraArrows();
			for(UMLArrow arrow : firstClass.getUMLArrows())
			{
				builder.append(arrow.toGraphVizString());
			}
		}
		builder.append("}");
		return builder.toString();
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
