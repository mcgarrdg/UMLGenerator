package Core.Visitors;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import Core.UMLItems.UMLGraph;
import Core.UMLItems.UMLMethod;

public class ClassMethodVisitor extends ClassVisitor {

	private UMLGraph graph;

	public ClassMethodVisitor(int arg0) {
		super(arg0);
	}

	public ClassMethodVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
	}

	public ClassMethodVisitor(int arg0, UMLGraph g) {
		super(arg0);
		this.graph = g;
	}

	public ClassMethodVisitor(int arg0, ClassVisitor arg1, UMLGraph g) {
		super(arg0, arg1);
		this.graph = g;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		// TODO Move the actual creation of the UMLMethod into the class, just
		// pass the strings down
		// so then the owner name can be added in right there
		this.graph.addMethod(new UMLMethod(name, access, desc, signature));
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		return new InnerMethodVisitor(Opcodes.ASM5, toDecorate, this.graph);
	}
}
