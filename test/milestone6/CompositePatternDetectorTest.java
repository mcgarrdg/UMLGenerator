package milestone6;

import static org.junit.Assert.assertTrue;

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
import problem.asm.UMLClass;
import problem.asm.UMLGraph;

public class CompositePatternDetectorTest {

	public CompositePatternDetectorTest() {
		
	}
	
	@Test
	public void testJavaAwt() throws IOException {
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
		
		
		for (UMLClass c1 : graph.getClasses()) {
			if (c1.getName().contains("Panel")) {
				assertTrue(c1.getPatternNames().contains("composite"));
			}
			if (c1.getName().contains("Container")) {
				assertTrue(c1.getPatternNames().contains("composite"));
			}
			if (c1.getName().contains("Component")) {
				assertTrue(c1.getPatternNames().contains("component"));
			}
			if (c1.getName().contains("Button")) {
				assertTrue(c1.getPatternNames().contains("leaf"));
			}
		}
//		System.out.println(graph.toGraphVizString());
	}
	
	@Test
	public void testJavaSwing() throws IOException {
		ArrayList<String> files = new ArrayList<String>();
		files.add("java.awt.Container");
		files.add("java.awt.Component");
		files.add("javax.swing.JFrame");
		files.add("java.awt.Frame");
		files.add("java.awt.Window");
		files.add("javax.swing.JPanel");
		files.add("javax.swing.JButton");
		files.add("javax.swing.AbstractButton");
		files.add("javax.swing.JLabel");
		files.add("javax.swing.JComponent");
		
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
		
		for (UMLClass c1 : graph.getClasses()) {
			if (c1.getName().contains("JLabel")) {
				assertTrue(c1.getPatternNames().contains("leaf"));
			}
			if (c1.getName().contains("Container")) {
				assertTrue(c1.getPatternNames().contains("component"));
			}
			if (c1.getName().contains("JPanel")) {
				assertTrue(c1.getPatternNames().contains("composite"));
			}
			if (c1.getName().contains("JButton")) {
				assertTrue(c1.getPatternNames().contains("leaf"));
			}
			if (c1.getName().contains("JFrame")) {
				assertTrue(c1.getPatternNames().contains("composite"));
			}
		}
	}
}
