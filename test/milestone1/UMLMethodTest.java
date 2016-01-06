package milestone1;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

import problem.asm.UMLMethod;

public class UMLMethodTest {
	
	private UMLMethod method;
	private ArrayList<String> argumentTypes;
	private String name;
	private String returnType;
	private String returnGenericType;
	private int accessType;
	
	public UMLMethodTest() {
		this.argumentTypes = new ArrayList<String>(); 
		this.argumentTypes.add("int");
		this.argumentTypes.add("String");
		this.name = "multiplyString";
		this.returnType = "String"; 
		this.returnGenericType = null;
		this.accessType = Opcodes.ACC_PUBLIC;
		method = new UMLMethod(name, accessType, argumentTypes, returnGenericType, returnType);
		
	}
	
	@Test
	public void testArgumentTypes() {
		ArrayList<String> argtypes = method.getArgumentTypes(); 
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
		assertEquals(this.returnType, method.getReturnType());
	}
	
	@Test
	public void testGenericType() {
		assertEquals(this.returnGenericType, method.getReturnGenericType());
	}
	
	@Test
	public void testAccessType() {
		assertEquals(this.accessType, method.getAccessType());
	}
		
	
	
}
