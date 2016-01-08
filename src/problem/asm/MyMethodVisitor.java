package problem.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class MyMethodVisitor extends MethodVisitor {
	
	private UMLMethod method;

	public MyMethodVisitor(int arg0, MethodVisitor arg1) {
		super(arg0, arg1);
	}

	public MyMethodVisitor(int arg0) {
		super(arg0);
	}
	
	public MyMethodVisitor(int arg0, MethodVisitor arg1, UMLMethod method) {
		super(arg0, arg1);
		this.method = method;
	}

	/*
	 * opcode - the opcode of the type instruction to be visited. This opcode is either GETSTATIC(178), PUTSTATIC(179), GETFIELD(180) or PUTFIELD(181).
	 * owner - the internal name of the field's owner class (see getInternalName).
	 * name - the field's name.
	 * desc - the field's descriptor (see Type).
	 */
	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
//		System.out.println("Opcode " + opcode + " Owner " + owner + " Name "+ name + " desc " + desc);
		super.visitFieldInsn(opcode, owner, name, desc);
		
	}

	/*
	 * opcode - the opcode of the type instruction to be visited. This opcode is
	 * either INVOKEVIRTUAL(182), INVOKESPECIAL(183), INVOKESTATIC(184) or INVOKEINTERFACE(185).
	 * owner - the internal name of the method's owner class (see getInternalName). 
	 * name - the method's name. 
	 * desc - the method's descriptor (see Type). 
	 * itf - if the method's owner class is an interface.
	 */
	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
//		String retType = Type.getReturnType(desc).getClassName().replace('.', '/');
//		TypeData returnType = new TypeData(retType.substring(retType.lastIndexOf('/') + 1), null, retType);
//		method.addPossibleUse(returnType);
//		System.out.println("possible use");
				
		super.visitMethodInsn(opcode, owner, name, desc, itf);
	}

	/*
	 * opcode - the opcode of the type instruction to be visited. This opcode is either NEW (187), ANEWARRAY(189), CHECKCAST(192) or INSTANCEOF(193).
	 * type - the operand of the instruction to be visited. This operand must be the internal name of an object or array class (see getInternalName).
	 */
	@Override
	public void visitTypeInsn(int opcode, String type) {
		super.visitTypeInsn(opcode, type);

	}

	/*
	 * opcode - the opcode of the local variable instruction to be visited. This opcode is either ILOAD, LLOAD, FLOAD, DLOAD, ALOAD, ISTORE, LSTORE, FSTORE, DSTORE, ASTORE or RET.
	 * var - the operand of the instruction to be visited. This operand is the index of a local variable.
	 */
	@Override
	public void visitVarInsn(int opcode, int var) {
		super.visitVarInsn(opcode, var);
	}

}
