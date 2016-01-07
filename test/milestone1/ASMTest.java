package milestone1;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

import problem.asm.DesignParser;
import problem.asm.TypeData;
import problem.asm.UMLClass;
import problem.asm.UMLGraph;
import problem.asm.UMLMethod;

public class ASMTest {

	private UMLGraph shouldBeG;
	private UMLGraph actuallyG;
	
	//TODO include more files, along with ones that use arrows
	public ASMTest() throws IOException {
		File file1 = new File("./files/headfirst/factory/pizzaaf/BlackOlives.class");
		File file2 = new File("./files/headfirst/factory/pizzaaf/Veggies.class");
		File[] files = {file1, file2};
		actuallyG = DesignParser.visitFiles(files);
		
		shouldBeG  = new UMLGraph("Test_UML", "BT");
		shouldBeG.addClass(new UMLClass("headfirst/factory/pizzaaf/BlackOlives", null, new String [] {"headfirst/factory/pizzaaf/Veggies"}));
		shouldBeG.addMethod(new UMLMethod("init", Opcodes.ACC_PUBLIC, new ArrayList<TypeData>(), new TypeData("void", null)));
		shouldBeG.addMethod(new UMLMethod("toString", Opcodes.ACC_PUBLIC, new ArrayList<TypeData>(), new TypeData("String", null)));
		shouldBeG.addClass(new UMLClass("headfirst/factory/pizzaaf/Veggies", null, new String[] {}));
		shouldBeG.addMethod(new UMLMethod("toString", Opcodes.ACC_PUBLIC, new ArrayList<TypeData>(), new TypeData("String", null) ));
		

		
	}
	
	@Test
	public void testClass(){
		assertEquals(shouldBeG.toGraphVizString(), actuallyG.toGraphVizString());
		
	}
	
	
}
