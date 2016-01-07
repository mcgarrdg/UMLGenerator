package problem.asm;

import java.util.ArrayList;

public class UMLGraph implements IGraphItem
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
		this.classes.get(this.classes.size()-1).addMethod(method);
	}
	
	//TODO Make the arrows draw. Will have to compare class extensions/implementations
	//against the names of the other classes that are being drawn.
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

		//TODO extract something like this into its own method to support different string types
		// maybe not...
		for(UMLClass c : this.classes)
		{
			ArrayList<String> implementations = c.getImplementations();
			for(String s : implementations)
			{
				for(UMLClass c2 : this.classes)
				{
					if(c2.getName().equals(s))
					{
						builder.append("\"" + c.getName() + "\" -> \"" + s + "\"" + " [arrowhead=\"onormal\", style=\"dashed\"];\n");
					}
				}
			}
			String extension = c.getExtension(); 
			for(UMLClass c2 : this.classes)
			{
				if(c2.getName().equals(extension))
				{
					builder.append("\"" + c.getName() + "\" -> \"" + extension + "\" [arrowhead=\"onormal\", style=\"\"];\n");
				}
			}
		}
		builder.append("\n}");
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
