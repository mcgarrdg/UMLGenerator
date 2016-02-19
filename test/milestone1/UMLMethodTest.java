package milestone1;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

import Core.TypeData;
import Core.UMLItems.UMLMethod;

public class UMLMethodTest {
	
	private UMLMethod method;
	private ArrayList<TypeData> argumentTypes;
	private String name;
	private TypeData type; 
	private int accessType;
	
	public UMLMethodTest() {
		this.argumentTypes = new ArrayList<TypeData>(); 
		this.argumentTypes.add(new TypeData("int", null, "int"));
		this.argumentTypes.add(new TypeData("String", null, "java/lang/String"));
		this.name = "multiplyString";
		this.type = new TypeData("String", null, "java/lang/String");
		this.accessType = Opcodes.ACC_PUBLIC;
		method = new UMLMethod(name, accessType, argumentTypes, type);
		
	}
	
	@Test
	public void testArgumentTypes() {
		ArrayList<TypeData> argtypes = method.getArgumentData(); 
		assertEquals(argtypes.size(), argumentTypes.size());
		for(int i = 0; i < argtypes.size(); i++) {
			assertEquals(argtypes.get(i), argumentTypes.get(i));
		}
	}
	
	@Test
	public void testName() {
		assertEquals(this.name, method.getName());
	}
	
	@Test
	public void testReturnType() {
		assertEquals(this.type.getBaseDataType(), method.getReturnType().getBaseDataType());
	}
	
	@Test
	public void testAccessType() {
		assertEquals(this.accessType, method.getAccessType());
	}
		
	
	
}
