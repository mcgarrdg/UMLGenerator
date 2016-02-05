package milestone6;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import problem.asm.ClassDeclarationVisitor;
import problem.asm.ClassFieldVisitor;
import problem.asm.ClassMethodVisitor;
import problem.asm.CompositePatternDetector;
import problem.asm.UMLGraph;

public class CompositePatternDetectorTest {

	public CompositePatternDetectorTest() {
		
	}
	
	@Test
	public void testInputStreamReader() throws IOException {
		ArrayList<String> files = new ArrayList<String>();
		files.add("java.awt.Panel");
		files.add("java.awt.Container");
		files.add("java.awt.Component");
		files.add("java.awt.Button");
		
		UMLGraph graph = new UMLGraph("Test_UML", "BT");
		graph.addPatternDetector(new CompositePatternDetector());
		
		for (String f : files) {
			ClassReader reader = new ClassReader(f);

			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, graph);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, graph);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, graph);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		}
		graph.generateArrows();
		graph.detectPatterns();
		System.out.println(graph.toGraphVizString());
	}
}
