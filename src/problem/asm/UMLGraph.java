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

	public UMLGraph(String name, String rankdir) {
		this.name = name;
		this.rankdir = rankdir;
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

		 for(UMLClass firstClass : this.classes)
		 {
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

	public void addMethodUsedToMethod(String methodSig) {
		this.classes.get(this.classes.size() - 1).addUsedMethodToMethod(methodSig);
	}

	@Override
	public String toSDEditString() {
		return ret.toString();
	}

	StringBuilder ret = new StringBuilder();

	public void generateCallSequence(String methodName, String offset) throws IOException {
		// TODO Auto-generated method stub
		UMLClass clazz = this.classes.get(this.classes.size() - 1);
		for (UMLMethod meth : clazz.getMethods()) {
			if (meth.getName().equals(methodName)) {
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
						if (!methSig.contains("<init>"))
							this.generateCallSequence(
									methSig.substring(methSig.lastIndexOf(".") + 1), " - " + offset);
					}
				}
			}
		}

	}

}
