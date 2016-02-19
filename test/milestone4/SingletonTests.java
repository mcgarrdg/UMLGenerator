package milestone4;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import Core.Visitors.ClassDeclarationVisitor;
import Core.Visitors.ClassFieldVisitor;
import Core.Visitors.ClassMethodVisitor;
import PatternDetectors.SingletonPatternDetector;
import Core.UMLItems.UMLGraph;

public class SingletonTests {

	
	public SingletonTests() {
		
	}
	
	@Test
	public void testLazyInitialization() throws IOException {
		UMLGraph graph = new UMLGraph("Test_UML", "BT");
		graph.addPatternDetector(new SingletonPatternDetector()); //Add detectors here
		ClassReader reader = new ClassReader("milestone4/ChocolateBoiler");

		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, graph);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, graph);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, graph);

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		graph.detectPatterns();
		
		assertTrue(graph.getClasses().get(0).patternNames.contains("Singleton"));
	}
	
	@Test
	public void testEagerInitialization() throws IOException {
		UMLGraph graph = new UMLGraph("Test_UML", "BT");
		graph.addPatternDetector(new SingletonPatternDetector()); //Add detectors here
		ClassReader reader = new ClassReader("milestone4/EagerChocolateBoiler");

		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, graph);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, graph);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, graph);

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		graph.detectPatterns();
		
		assertTrue(graph.getClasses().get(0).patternNames.contains("Singleton"));
	}
	
	
	
	@Test 
	public void testRuntime() throws IOException {
		//definitely a singleton
		UMLGraph graph = new UMLGraph("Test_UML", "BT");
		graph.addPatternDetector(new SingletonPatternDetector()); //Add detectors here
		ClassReader reader = new ClassReader("java.lang.Runtime");

		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, graph);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, graph);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, graph);

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		graph.detectPatterns();
		
		assertTrue(graph.getClasses().get(0).patternNames.contains("Singleton"));
	}
	
	@Test
	public void testDesktop() throws IOException {
		//Not a singlton, no static field of itself.
		UMLGraph graph = new UMLGraph("Test_UML", "BT");
		graph.addPatternDetector(new SingletonPatternDetector()); //Add detectors here
		ClassReader reader = new ClassReader("java.awt.Desktop");

		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, graph);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, graph);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, graph);

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		graph.detectPatterns();
		
		assertTrue(!graph.getClasses().get(0).patternNames.contains("Singleton"));
	}
	
	@Test
	public void testCalendar() throws IOException {
		//no static field of itsef, not a singleton
		UMLGraph graph = new UMLGraph("Test_UML", "BT");
		graph.addPatternDetector(new SingletonPatternDetector()); //Add detectors here
		ClassReader reader = new ClassReader("java.util.Calendar");

		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, graph);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, graph);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, graph);

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		graph.detectPatterns();
		
		assertTrue(!graph.getClasses().get(0).patternNames.contains("Singleton"));
	}
	
	@Test
	public void testFilterInputStream() throws IOException {
		//no getInstanceMethod, not a singleton
		UMLGraph graph = new UMLGraph("Test_UML", "BT");
		graph.addPatternDetector(new SingletonPatternDetector()); //Add detectors here
		ClassReader reader = new ClassReader("java.io.FilterInputStream");

		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, graph);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, graph);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, graph);

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		graph.detectPatterns();
		
		assertTrue(!graph.getClasses().get(0).patternNames.contains("Singleton"));
	}
}

