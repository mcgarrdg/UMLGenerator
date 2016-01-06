package problem.asm;

import java.util.ArrayList;

public class UMLGraph implements IGraphItem{

	private ArrayList<UMLClass> classes;
	private String name;
	private String rankdir;
	
	public UMLGraph(String name, String rankdir)
	{
		this.name = name;
		this.rankdir = rankdir;
		classes = new ArrayList<UMLClass>();
		// TODO Auto-generated constructor stub
	}
	
	public void addClass(UMLClass clss)
	{
		this.classes.add(clss);
	}
	
	public void addField(UMLField field)
	{
		try
		{
			this.classes.get(this.classes.size()-1).addField(field);
		}
		catch (IndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
	}
	
	public void addMethod(UMLMethod method)
	{
		try
		{
			this.classes.get(this.classes.size()-1).addMethod(method);
		}
		catch (IndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
	}
	
	//TODO Make the arrows draw. Will have to compare class extensions/implementations
	//against the names of the other classes that are being drawn.
	public String toGraphVizString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("digraph ");
		builder.append("\"");
		builder.append(this.name);
		builder.append("\"");
		builder.append("{\n");
		builder.append("\trankdir = ");
		builder.append(this.rankdir);
		
		for(UMLClass c : this.classes)
		{
			builder.append("\n\t");
			builder.append(c.toGraphVizString());
		}
		builder.append("\n");

		//extract something like this into its own method to support different string types
		for(UMLClass c : this.classes)
		{
			ArrayList<String> implementations = c.getImplementations();
			for(String s : implementations)
			{
				for(UMLClass c2 : this.classes)
				{
					if(c2.getName().equals(s))
					{
						builder.append("\"");
						builder.append(c.getName());
						builder.append("\"");
						builder.append(" -> ");
						builder.append("\"");
						builder.append(s);
						builder.append("\"");
						builder.append(" [arrowhead=\"onormal\", style=\"dashed\"];\n");
					}
				}
			}
			String extension = c.getExtension(); 
			for(UMLClass c2 : this.classes)
			{
				if(c2.getName().equals(extension))
				{
					builder.append("\"");
					builder.append(c.getName());
					builder.append("\"");
					builder.append(" -> ");
					builder.append("\"");
					builder.append(extension);
					builder.append("\"");
					builder.append(" [arrowhead=\"onormal\", style=\"\"];\n");
				}
			}
		}
		builder.append("\n}");
		return builder.toString();
	}

	/**
	 * @return the classes of the graph
	 */
	public ArrayList<UMLClass> getClasses() {
		return classes;
	}

	/**
	 * @return the name of the graph
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the rankdir of the graph
	 */
	public String getRankdir() {
		return rankdir;
	}
}
