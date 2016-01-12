package milestone1;

import static org.junit.Assert.*;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

import problem.asm.TypeData;
import problem.asm.UMLField;

public class UMLFieldTest {
	
	
	private UMLField field;
	private TypeData type;
	private TypeData generic;
	private String name;
	private int accessType;

	public UMLFieldTest() {
		this.name = "bestField";
		this.generic = new TypeData("Box", null, "explosives/Box");
		this.type = new TypeData("T", this.generic, "T");
		this.accessType = Opcodes.ACC_PUBLIC;
		
		
		field = new UMLField(name, type, accessType);
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
		assertEquals(this.generic.getBaseDataType(), field.getType().getBaseDataType());
	}
	
	
	
	
}
