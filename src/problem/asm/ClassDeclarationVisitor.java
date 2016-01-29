package problem.asm;

import org.objectweb.asm.ClassVisitor;

public class ClassDeclarationVisitor extends ClassVisitor {

	private UMLGraph graph;

	public ClassDeclarationVisitor(int arg0) {
		super(arg0);
	}

	public ClassDeclarationVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}

	public ClassDeclarationVisitor(int arg0, UMLGraph g) {
		super(arg0);
		this.graph = g;
	}

	public ClassDeclarationVisitor(int arg0, ClassVisitor arg1, UMLGraph g) {
		super(arg0, arg1);
		this.graph = g;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
//		System.out.println("Super(extends): " + superName); //TODO Remove these prints
//		System.out.println("Interfaces:");
//		for (String nme : interfaces ) System.out.println(nme);
		this.graph.addClass(new UMLClass(name, superName, access, interfaces));
		super.visit(version, access, name, signature, superName, interfaces);
	}
}
