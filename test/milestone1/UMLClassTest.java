package milestone1;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import problem.asm.UMLClass;
import problem.asm.UMLField;
import problem.asm.UMLMethod;

public class UMLClassTest {

	private UMLClass clazz;
	private String name;
	private String extension;
	private ArrayList<UMLMethod> methods = new ArrayList<UMLMethod>();
	private ArrayList<UMLField> fields = new ArrayList<UMLField>();
	private ArrayList<String> implementations= new ArrayList<String>();
	
	public UMLClassTest() {
		this.name = "MyClass";
		this.extension = "MyDadsClass"; 
//		methods.add(new UMLMethod("coolMethod", "+", ));
		

	}
	
	@Test
	public void testName() {
		assertEquals(this.name, clazz.getName() );
	}
	
	@Test
	public void testExtension() {
		 assertEquals(this.extension, clazz.getExtension());
	}
	
	@Test
	public void testMethods() {
		ArrayList<UMLMethod> testmethods = clazz.getMethods();
		assertTrue(testmethods.size() == this.methods.size());
		for(int i = 0; i < testmethods.size(); i++) {
			assertEquals(testmethods.get(i), this.methods.get(i));
		}
	}
	
	@Test
	public void testFields() {
		ArrayList<UMLField> testfields = clazz.getFields();
		assertTrue(testfields.size() == this.methods.size());
		for(int i = 0; i < testfields.size(); i++) {
			assertEquals(testfields.get(i), this.methods.get(i));
		}
	}
	
	
	
	
	
	
	
	
	
}
