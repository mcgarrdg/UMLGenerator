package problem.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;

public class ClassFieldVisitor extends ClassVisitor {

	private UMLGraph graph;
	public ClassFieldVisitor(int arg0) {
		super(arg0);
	}
	
	public ClassFieldVisitor(int asmVer, ClassVisitor visitor) {
		super(asmVer, visitor);
	}
	
	public ClassFieldVisitor(int arg0, UMLGraph g) {
		super(arg0);
		this.graph = g;
	}
	
	public ClassFieldVisitor(int asmVer, ClassVisitor visitor, UMLGraph g) {
		super(asmVer, visitor);
		this.graph = g;
	}

	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value)
	{
		this.graph.addField(new UMLField(name, access, desc, signature));
		FieldVisitor toDecorate = super.visitField(access, name, desc, signature, value);
		return toDecorate;
	}
	
}
