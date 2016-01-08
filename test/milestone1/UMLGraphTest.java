package milestone1;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

import problem.asm.TypeData;
import problem.asm.UMLClass;
import problem.asm.UMLField;
import problem.asm.UMLGraph;
import problem.asm.UMLMethod;

public class UMLGraphTest {

	private UMLGraph g; 
	private UMLClass c;
	private UMLMethod m; 
	private UMLField f; 
	private String name; 
	private static String rankdir = "BT"; 
	
	public UMLGraphTest() {
		this.name = "aGraph"; 
		g = new UMLGraph(name, rankdir); 
		
		c = new UMLClass("aClass", null, Opcodes.ACC_SUPER, new String[0]);
		g.addClass(c);
		
		m = new UMLMethod("aMethod", Opcodes.ACC_PUBLIC, new ArrayList<TypeData>(), new TypeData("int", null));
		f = new UMLField("aField", new TypeData("int", null), Opcodes.ACC_PRIVATE); 
		
		g.addMethod(m);
		g.addField(f);
	}
	
	
	@Test 
	public void testName() { 
		assertEquals(this.name, g.getName());
	}
	
	@Test 
	public void testRankdir() {
		assertEquals(rankdir, g.getRankdir());
	}
	
	@Test
	public void testAddClass() {
		assertEquals(g.getClasses().get(0), c);
	}
	
	@Test
	public void testAddField() {
		assertEquals(g.getClasses().get(0).getFields().get(0), f);
	}
	
	@Test
	public void testAddMethod() {
		assertEquals(g.getClasses().get(0).getMethods().get(0), m); 
	}
	
	
}
