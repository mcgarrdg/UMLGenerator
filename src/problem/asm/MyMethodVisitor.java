package problem.asm;

import org.objectweb.asm.MethodVisitor;

public class MyMethodVisitor extends MethodVisitor {

	public MyMethodVisitor(int arg0, MethodVisitor arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public MyMethodVisitor(int arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/*
	 * opcode - the opcode of the type instruction to be visited. This opcode is either GETSTATIC, PUTSTATIC, GETFIELD or PUTFIELD.
	 * owner - the internal name of the field's owner class (see getInternalName).
	 * name - the field's name.
	 * desc - the field's descriptor (see Type).
	 */
	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		// TODO Auto-generated method stub
		super.visitFieldInsn(opcode, owner, name, desc);

	}

	/*
	 * opcode - the opcode of the type instruction to be visited. This opcode is
	 * either INVOKEVIRTUAL, INVOKESPECIAL, INVOKESTATIC or INVOKEINTERFACE.
	 * owner - the internal name of the method's owner class (see getInternalName). 
	 * name - the method's name. 
	 * desc - the method's descriptor (see Type). 
	 * itf - if the method's owner class is an interface.
	 */
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		// TODO Auto-generated method stub
		super.visitMethodInsn(opcode, owner, name, desc, itf);
	}

	/*
	 * opcode - the opcode of the type instruction to be visited. This opcode is either NEW, ANEWARRAY, CHECKCAST or INSTANCEOF.
	 * type - the operand of the instruction to be visited. This operand must be the internal name of an object or array class (see getInternalName).
	 */
	@Override
	public void visitTypeInsn(int opcode, String type) {
		// TODO Auto-generated method stub
		super.visitTypeInsn(opcode, type);
	}

	/*
	 * opcode - the opcode of the local variable instruction to be visited. This opcode is either ILOAD, LLOAD, FLOAD, DLOAD, ALOAD, ISTORE, LSTORE, FSTORE, DSTORE, ASTORE or RET.
	 * var - the operand of the instruction to be visited. This operand is the index of a local variable.
	 */
	@Override
	public void visitVarInsn(int arg0, int arg1) {
		// TODO Auto-generated method stub
		super.visitVarInsn(arg0, arg1);
	}

}
