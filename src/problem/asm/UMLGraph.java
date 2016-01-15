package problem.asm;

import java.io.IOException;
import java.util.ArrayList;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class UMLGraph extends UMLGraphItem implements SDGraphItem {

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
	
	private ArrayList<SDGraphMethodData> sdEditMethodData;

	public UMLGraph(String name, String rankdir) {
		this.name = name;
		this.rankdir = rankdir;
		this.sdEditMethodData = new ArrayList<SDGraphMethodData>();
		classes = new ArrayList<UMLClass>();
	}

	/**
	 * Adds a class to the UMLGraph.
	 * 
	 * @param clss
	 *            The class to be added.
	 */
	public void addClass(UMLClass clss) {
		this.classes.add(clss);
	}

	/**
	 * Adds a field to the most recently added class on this graph.
	 * 
	 * @param field
	 *            The field to be added.
	 */
	public void addField(UMLField field) {
		this.classes.get(this.classes.size() - 1).addField(field);
	}

	/**
	 * Adds a method to the most recently added class on this graph.
	 * 
	 * @param method
	 */
	public void addMethod(UMLMethod method) {
		this.classes.get(this.classes.size() - 1).addMethod(method);
	}

	/**
	 * Adds a class that has been used in a method to the most recently added
	 * method.
	 * 
	 * @param fullClassName
	 *            Full name of used class.
	 */
	public void addClassUsedToMethod(String fullClassName) {
		this.classes.get(this.classes.size() - 1).addUsedClassToMethod(fullClassName);
	}

	@Override
	public String toGraphVizString() {
		StringBuilder builder = new StringBuilder();

		builder.append("digraph \"" + this.name + "\"{\n\trankdir = " + this.rankdir);

		for (UMLClass firstClass : this.classes) {
			firstClass.generateArrows(this.classes);
		}

		for (UMLClass firstClass : this.classes) {
			firstClass.removeExtraArrows();
		}

		for (UMLClass firstClass : this.classes) {
			firstClass.removeRedundantUsesArrows();
		}

		for (UMLClass c : this.classes) {
			builder.append("\n\t");
			builder.append(c.toGraphVizString());
		}
		builder.append("\n");
		builder.append("}");
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

	public void addMethodUsedToMethod(String owner, String name, String desc) {
		this.classes.get(this.classes.size() - 1).addUsedMethodToMethod(owner, name, desc);
	}

	@Override
	public String toSDEditString() 
	{
		StringBuilder builder = new StringBuilder();
//		ArrayList<String> begin = new ArrayList<String>();
		builder.append("\n");
		for(SDGraphMethodData data : this.sdEditMethodData)
		{
			if((!builder.toString().contains("\n" + data.getClassCalledOn() + ":")) && (!builder.toString().contains("\n/" + data.getClassCalledOn() + ":")))
			{
				if(data.getMethodName().equals("init"))
				{
					builder.append("/" + data.getClassCalledOn() + ":" + data.getClassCalledOn() + "[a]\n");
				}
				else
				{
					builder.append(data.getClassCalledOn() + ":" + data.getClassCalledOn() + "[a]\n");
				}
			}
			else
			{
				if(data.getMethodName().equals("init"))
				{
					data.setMethodName("new2");
				}
			}
		}
		
		builder.append("\n");
		
		for(SDGraphMethodData data : this.sdEditMethodData)
		{
			builder.append(data.toSDEditString());
		}
		return builder.toString();
	}

	//callDepth of 0 will still print out the initial call specified.
	public void generateCallSequence(String fullQualMethodSig, int callDepth) throws IOException {
		UMLClass clazz = this.classes.get(this.classes.size() - 1);
		String fullOwnerName = fullQualMethodSig.substring(0, fullQualMethodSig.lastIndexOf('.'));
		fullOwnerName = fullOwnerName.replace('.', '/');
		String methodName = fullQualMethodSig.substring(fullQualMethodSig.lastIndexOf('.') + 1,
				fullQualMethodSig.indexOf('('));

		ArrayList<TypeData> tempArgs = new ArrayList<TypeData>();

		String arguments = fullQualMethodSig.substring(fullQualMethodSig.indexOf('(') + 1,
				fullQualMethodSig.indexOf(')'));
		String[] splitArgs = arguments.split(",");
		for (int i = 0; i < splitArgs.length; i++) {
			splitArgs[i] = splitArgs[i].trim().split(" ")[0];
		}
		for (int i = 0; i < splitArgs.length; i++) {
			if (splitArgs[i].contains("<")) {
				splitArgs[i] = splitArgs[i].split("<")[0];
			}
		}

		for (String argName : splitArgs) {
			// I can't get the full name path of the arguments, so I just pass in the base name twice.
			tempArgs.add(new TypeData(argName, null, argName));
		}

		// We don't care about the access type or the return type, this is just
		// a temporary method
		// to go through the class and find the method that we want to build the
		// call sequence for
		UMLMethod newMethod = new UMLMethod(methodName, Opcodes.ACC_PUBLIC, tempArgs, new TypeData("temp", null, "temp"));
		newMethod.setFullOwnerName(fullOwnerName);

		for (UMLMethod meth : clazz.getMethods()) {
			if (meth.sameFullQualifiedSignature(newMethod)) {
				this.sdEditMethodData.add(meth.toSDGraphMethodData());

				for (UMLMethod usedMethod : meth.getMethodCalls()) {
					if (!usedMethod.sameFullQualifiedSignature(meth)) {
						boolean alreadyHasClass = false;
						for (UMLClass tempClass : this.classes) {
							if (usedMethod.getFullownerName().equals(tempClass.getName())) {
								alreadyHasClass = true;
								break;
							}
						}

						if (!alreadyHasClass) {
							ClassReader reader = new ClassReader(usedMethod.getFullownerName().replace('/', '.'));

							ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, this);
							ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, this);
							ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, this);

							reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
						}
						this.generateCallSequenceHelper(usedMethod, meth, callDepth);
					}
				}
			}
		}
	}

	private void generateCallSequenceHelper(UMLMethod method, UMLMethod prevLevelMethod, int callDepth) throws IOException {
		// Not sure if I can just look at the last class added, because there
		// could
		// be a method used from a class I added previously
		if(callDepth == 0)
		{
			return;
		}
		
		UMLClass clazz = null;
		for (UMLClass tempClass : this.classes) {
			if (method.getFullownerName().equals(tempClass.getName())) {
				clazz = tempClass;
				break;
			}
		}

		for (UMLMethod meth : clazz.getMethods()) {
			if (meth.sameFullQualifiedSignature(method)) {
				this.sdEditMethodData.add(meth.toSDGraphMethodData(prevLevelMethod));

				for (UMLMethod usedMethod : meth.getMethodCalls()) {
					if (!usedMethod.sameFullQualifiedSignature(meth)) {
						ClassReader reader = new ClassReader(usedMethod.getFullownerName().replace('/', '.'));

						ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, this);
						ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, this);
						ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, this);

						reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
					}
					this.generateCallSequenceHelper(usedMethod, meth, callDepth - 1);
				}
			}
		}
	}
}
