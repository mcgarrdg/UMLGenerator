package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

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
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
	{
<<<<<<< Updated upstream
		UMLMethod newMethod =new UMLMethod(name, access, desc, signature );
		this.graph.addMethod(newMethod);
=======
//		System.out.println(name + " "+ desc);
		this.graph.addMethod(new UMLMethod(name, access, desc, signature));
>>>>>>> Stashed changes
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);
		MethodVisitor mine = new MyMethodVisitor(Opcodes.ASM5, toDecorate, newMethod);
		return mine;
	}
}
