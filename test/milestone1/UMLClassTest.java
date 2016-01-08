package milestone1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

import problem.asm.TypeData;
import problem.asm.UMLClass;
import problem.asm.UMLField;
import problem.asm.UMLMethod;

public class UMLClassTest {

	private UMLClass clazz;
	private String name;
	private String extension;
	private ArrayList<UMLMethod> methods = new ArrayList<UMLMethod>();
	private ArrayList<UMLField> fields = new ArrayList<UMLField>();
	private String[] implementations = new String[1];

	public UMLClassTest() {
		this.name = "MyClass";
		this.extension = "MyDadsClass";
		
		
		ArrayList<TypeData> argumentTypes = new ArrayList<TypeData>();
		argumentTypes.add(new TypeData("int", null, "int"));
		argumentTypes.add(new TypeData("String", null, "java/lang/String"));

		clazz = new UMLClass(this.name, this.extension, Opcodes.ACC_SUPER, this.implementations);
		UMLMethod m = new UMLMethod("multiplyString", Opcodes.ACC_PUBLIC, argumentTypes, new TypeData("String", null, "java/lang/String"));
		methods.add(m);
		clazz.addMethod(m);

		UMLField f = new UMLField("bestField", new TypeData("String", null, "java/lang/String"),  Opcodes.ACC_PUBLIC);
		fields.add(f);
		clazz.addField(f);

	}

	@Test
	public void testName() {
		assertEquals(this.name, clazz.getName());
	}

	@Test
	public void testExtension() {
		assertEquals(this.extension, clazz.getExtension());
	}

	@Test
	public void testMethods() {
		ArrayList<UMLMethod> testmethods = clazz.getMethods();
		assertTrue(testmethods.size() == this.methods.size());
		for (int i = 0; i < testmethods.size(); i++) {
			assertEquals(testmethods.get(i), this.methods.get(i));
		}
	}

	@Test
	public void testFields() {
		ArrayList<UMLField> testfields = clazz.getFields();
		assertTrue(testfields.size() == this.fields.size());
		for (int i = 0; i < testfields.size(); i++) {
			assertEquals(testfields.get(i), this.fields.get(i));
		}
	}

	@Test
	public void testImplementations() {
		ArrayList<String> testimps = clazz.getImplementations();
		assertTrue(testimps.size() == this.implementations.length);
		for (int i = 0; i < testimps.size(); i++) {
			assertEquals(testimps.get(i), this.implementations[i]);
		}
	}

}
