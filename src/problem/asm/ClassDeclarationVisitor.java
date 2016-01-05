package problem.asm;

import org.objectweb.asm.ClassVisitor;

public class ClassDeclarationVisitor extends ClassVisitor {

	private UMLGraph graph;
	public ClassDeclarationVisitor(int arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ClassDeclarationVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	public ClassDeclarationVisitor(int arg0, UMLGraph g) {
		super(arg0);
		this.graph = g;
		// TODO Auto-generated constructor stub
	}

	public ClassDeclarationVisitor(int arg0, ClassVisitor arg1, UMLGraph g) {
		super(arg0, arg1);
		this.graph = g;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces)
	{
		//System.out.println("Class: " + name + " extends " + superName + " implements " + Arrays.toString(interfaces));
		this.graph.addClass(new UMLClass(name, superName, interfaces));
		super.visit(version, access, name, signature, superName, interfaces);
	}
}
