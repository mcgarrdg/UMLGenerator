package Core.Visitors;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import Core.UMLItems.UMLGraph;

public class InnerMethodVisitor extends MethodVisitor {

	private UMLGraph graph;

	public InnerMethodVisitor(int arg0, MethodVisitor arg1) {
		super(arg0, arg1);
	}

	public InnerMethodVisitor(int arg0) {
		super(arg0);
	}

	public InnerMethodVisitor(int arg0, MethodVisitor arg1, UMLGraph gr) {
		super(arg0, arg1);
		this.graph = gr;
	}

	/*
	 * opcode - the opcode of the type instruction to be visited. This opcode is
	 * either GETSTATIC, PUTSTATIC, GETFIELD or PUTFIELD. owner - the internal
	 * name of the field's owner class (see getInternalName). name - the field's
	 * name. desc - the field's descriptor (see Type).
	 */
	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		super.visitFieldInsn(opcode, owner, name, desc);

	}

	/*
	 * opcode - the opcode of the type instruction to be visited. This opcode is
	 * either INVOKEVIRTUAL, INVOKESPECIAL, INVOKESTATIC or INVOKEINTERFACE.
	 * owner - the internal name of the method's owner class (see
	 * getInternalName). name - the method's name. desc - the method's
	 * descriptor (see Type). itf - if the method's owner class is an interface.
	 */
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		super.visitMethodInsn(opcode, owner, name, desc, itf);
		// System.out.println("Owner: " + owner + " name: " + name + " desc: " +
		// desc);
		this.graph.addClassUsedToMethod(owner);
		// String methodSig = owner.replace("/", ".") + "."+ name;
		//Ignore the special invokings of things. We don't care about those.
//		if(name.equals("<init>") || ((opcode & Opcodes.INVOKESPECIAL) != Opcodes.INVOKESPECIAL && 
//				(opcode & Opcodes.INVOKESTATIC) != Opcodes.INVOKESTATIC &&
//				(opcode & Opcodes.INVOKEINTERFACE) != Opcodes.INVOKEINTERFACE &&
//				(opcode & Opcodes.INVOKEVIRTUAL) != Opcodes.INVOKEVIRTUAL))
//		if((opcode & Opcodes.INVOKESPECIAL) != Opcodes.INVOKESPECIAL)
//		{
			this.graph.addMethodUsedToMethod(owner, name, desc);
//		}

	}

	/*
	 * opcode - the opcode of the type instruction to be visited. This opcode is
	 * either NEW, ANEWARRAY, CHECKCAST or INSTANCEOF. type - the operand of the
	 * instruction to be visited. This operand must be the internal name of an
	 * object or array class (see getInternalName).
	 */
	@Override
	public void visitTypeInsn(int opcode, String type) {
		super.visitTypeInsn(opcode, type);

		// TODO Maybe more than just new are important?
		if ((opcode & Opcodes.NEW) == Opcodes.NEW) {
			this.graph.addClassUsedToMethod(type);
		}
	}

	/*
	 * opcode - the opcode of the local variable instruction to be visited. This
	 * opcode is either ILOAD, LLOAD, FLOAD, DLOAD, ALOAD, ISTORE, LSTORE,
	 * FSTORE, DSTORE, ASTORE or RET. var - the operand of the instruction to be
	 * visited. This operand is the index of a local variable.
	 */
	@Override
	public void visitVarInsn(int arg0, int arg1) {
		super.visitVarInsn(arg0, arg1);
	}

}
