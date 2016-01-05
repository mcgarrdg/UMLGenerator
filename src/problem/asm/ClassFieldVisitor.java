package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Type;

public class ClassFieldVisitor extends ClassVisitor {

	private UMLGraph graph;
	public ClassFieldVisitor(int arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	public ClassFieldVisitor(int asmVer, ClassVisitor visitor) {
		super(asmVer, visitor);
		// TODO Auto-generated constructor stub
	}
	
	public ClassFieldVisitor(int arg0, UMLGraph g) {
		super(arg0);
		this.graph = g;
		// TODO Auto-generated constructor stub
	}
	
	public ClassFieldVisitor(int asmVer, ClassVisitor visitor, UMLGraph g) {
		super(asmVer, visitor);
		this.graph = g;
		// TODO Auto-generated constructor stub
	}

	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value)
	{
		//System.out.println(Type.getType(signature).getElementType());
		this.graph.addField(new UMLField(name, access, desc, signature));
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		//String type = Type.getType(desc).getClassName();
		//System.out.println("    "+type+" "+name);
		
		//this.graph.addField(new UMLField(name, access, type));
		return toDecorate;
	}
	
}
