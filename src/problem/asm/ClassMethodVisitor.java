package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class ClassMethodVisitor extends ClassVisitor {

	UMLGraph graph;
	public ClassMethodVisitor(int arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ClassMethodVisitor(int arg0, ClassVisitor arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	public ClassMethodVisitor(int arg0, UMLGraph g) {
		super(arg0);
		this.graph = g;
		// TODO Auto-generated constructor stub
	}

	public ClassMethodVisitor(int arg0, ClassVisitor arg1, UMLGraph g) {
		super(arg0, arg1);
		this.graph = g;
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
	{
		this.graph.addMethod(new UMLMethod(name, access, desc));
		MethodVisitor toDecorate = super.visitMethod(access, name, desc, signature, exceptions);

//		String returnType = Type.getReturnType(desc).getClassName();
//		
//		Type[] argTypes = Type.getArgumentTypes(desc);
//		
//		 List<String> stypes = new ArrayList<String>();
//		 for(Type t:argTypes){
//		 stypes.add(t.getClassName());
//		 }
		
		/*
		String[] stypes = new String[argTypes.length];
		for(int i = 0; i < argTypes.length; i++)
		{
			ar
		}*/
		 
//		 String symbol = "";
//		 if((access & Opcodes.ACC_PUBLIC) != 0)
//		 {
//			 symbol = "+";
//		 }
		
		//System.out.println("    method " + symbol + returnType + " " + name + " " + stypes.toString());
		
		
		
		return toDecorate;
	}
}
