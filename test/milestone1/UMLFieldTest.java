package milestone1;

import static org.junit.Assert.*;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

import problem.asm.UMLField;

public class UMLFieldTest {
	
	
	private UMLField field;
	private String name;
	private String type;
	private int accessType;
	private String genericType;

	public UMLFieldTest() {
		this.name = "bestField";
		this.type = "String";
		this.genericType = null; 
		this.accessType = Opcodes.ACC_PUBLIC;
		
		field = new UMLField(name, type, genericType, accessType);
	}
	
	@Test
	public void testName() {
		assertEquals(this.name, field.getName());
	}
	
	@Test
	public void testType() {
		assertEquals(this.type, field.getType());
	}
	
	@Test
	public void testAccessType() {
		assertEquals(this.accessType, field.getAccessType());
	}
	
	@Test
	public void testGenericType() {
		assertNull(field.getGenericType());
	}
	
	
	
	
}
