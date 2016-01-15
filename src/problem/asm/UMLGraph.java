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
						if(!builder.toString().contains(getArrowString(firstClass.getName(), implementation, "onormal", "dashed")))
						{
							builder.append(getArrowString(firstClass.getName(), implementation, "onormal", "dashed"));
						}
					}
				}
				
				for(UMLField field : firstClass.getFields())
				{
					String type = field.getType().getFullBaseDataType();
					if(secondClass.getName().equals(type))
					{
						if(!builder.toString().contains(getArrowString(firstClass.getName(), type, "vee", "solid")))
						{
							builder.append(getArrowString(firstClass.getName(), type, "vee", "solid"));
						}
					}
				}
				
				for(UMLMethod meth : firstClass.getMethods())
				{
					for(TypeData data : meth.getArgumentData())
					{
						if(data.getFullBaseDataType().equals(secondClass.getName()))
						{
							if(!builder.toString().contains(getArrowString(firstClass.getName(), secondClass.getName() , "vee", "dashed")))
							{
								builder.append(getArrowString(firstClass.getName(), secondClass.getName() , "vee", "dashed"));
							}
						}
					}
					if(meth.getReturnType().getFullBaseDataType().equals(secondClass.getName()))
					{
//						meth.filterPossibleUses(this.classes);
//						ArrayList<TypeData> methUses = meth.getPossibleUses();
//						System.out.println(methUses.size());
//						UMLClass thirdClass = null;
//						for(int j = 0; j < classes.size(); j++) {
//							for(int i = 0; i < methUses.size(); i++) {
//								if(classes.get(i).getName().equals(methUses.get(i).getBaseDataType())) {
//									thirdClass = classes.get(j);
//									if(thirdClass.getExtension().equals(secondClass.getName()) || thirdClass.getImplementations().contains(secondClass.getName())) {
//									
//										if(!builder.toString().contains(getArrowString(firstClass.getName(), thirdClass.getName() , "vee", "dashed")))
//										{
//											System.out.println("whoa dude");
//											builder.append(getArrowString(firstClass.getName(), thirdClass.getName() , "vee", "dashed"));
//										}
//									}
//								}
//							}
//						}				
						
						
						
						if(!builder.toString().contains(getArrowString(firstClass.getName(), secondClass.getName() , "vee", "dashed")))
						{
							builder.append(getArrowString(firstClass.getName(), secondClass.getName() , "vee", "dashed"));
						}
					}
				}
				
				if(secondClass.getName().equals(firstClass.getExtension()))
				{
					if(!builder.toString().contains(getArrowString(firstClass.getName(), firstClass.getExtension(), "onormal", "")))
					{
						builder.append(getArrowString(firstClass.getName(), firstClass.getExtension(), "onormal", ""));
					}
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
<<<<<<< Updated upstream
	
	
	public UMLClass filterSingleClass(String name) {
		for(int i = 0; i < classes.size(); i++) {
			if(classes.get(i).getName() == name) {
				return classes.get(i);
=======

	public void addMethodUsedToMethod(String methodSig) {
		this.classes.get(this.classes.size() - 1).addUsedMethodToMethod(methodSig);
	}
	

	@Override
	public String toSDEditString() {
		return ret.toString();
	}

	StringBuilder ret = new StringBuilder();

	public void generateCallSequence(String method, String offset) throws IOException {
		// TODO Still need to check method by number of arguments. Currently, if we have doSomething() and doSomething(a), this will generate the sequence for both.
		
		
//		String[] methodPieces = method.split("\\(");
//		String methodName = methodPieces[0];
//		String[] args = {};
//		if(methodPieces.length > 1) {
//			methodPieces[1] = methodPieces[1].substring(0, methodPieces[1].length()-1);
//			if(!methodPieces[1].equals("")) {
//				args = methodPieces[1].split(",");
//			}
//		}
		
		
		//find method from UMLClass/UMLMethod: 
		
		UMLClass clazz = this.classes.get(this.classes.size()-1);
		for(UMLMethod meth : clazz.getMethods()) {
			
		}
		
	}
	private void generateCallSequenceHelper(UMLClass clazz, UMLMethod method, String offset) {
		
		for (UMLMethod meth : clazz.getMethods()) {
			if(meth.sameSignature(method)) {
				ret.append(offset);
				ret.append(clazz.toSDEditString());
				ret.append(meth.toSDEditString());
				ret.append("\n");
				for (String methSig : meth.getMethodCalls()) {
					if (!methSig.substring(methSig.lastIndexOf(".") + 1).equals(methodName)) {
						ClassReader reader = new ClassReader(methSig.substring(0, methSig.lastIndexOf(".")));

						ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, this);
						ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, this);
						ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, this);

						reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
							this.generateCallSequence(
									methSig.substring(methSig.lastIndexOf(".") + 1), " - " + offset);
					}
				}
>>>>>>> Stashed changes
			}
		}
		return null;
	}
}
