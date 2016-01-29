package milestone5;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import problem.asm.ClassDeclarationVisitor;
import problem.asm.ClassFieldVisitor;
import problem.asm.ClassMethodVisitor;
import problem.asm.DecoratorPatternDetector;
import problem.asm.UMLArrow;
import problem.asm.UMLClass;
import problem.asm.UMLGraph;

public class DecoratorTest {

	public DecoratorTest() {
	
	}

	@Test
	public void testLab2_1() throws IOException {
		ArrayList<File> labFiles = new ArrayList<File>();
		labFiles.add(new File("./files/Milestone 5/headfirst/Beverage.class"));
		labFiles.add(new File("./files/Milestone 5/headfirst/CondimentDecorator.class"));
		labFiles.add(new File("./files/Milestone 5/headfirst/DarkRoast.class"));
		labFiles.add(new File("./files/Milestone 5/headfirst/Decaf.class"));
		labFiles.add(new File("./files/Milestone 5/headfirst/Espresso.class"));
		labFiles.add(new File("./files/Milestone 5/headfirst/HouseBlend.class"));
		labFiles.add(new File("./files/Milestone 5/headfirst/InputTest.class"));
		labFiles.add(new File("./files/Milestone 5/headfirst/LowerCaseInputStream.class"));
		labFiles.add(new File("./files/Milestone 5/headfirst/LowerCaseInputStreamTest.class"));
		labFiles.add(new File("./files/Milestone 5/headfirst/Milk.class"));
		labFiles.add(new File("./files/Milestone 5/headfirst/Mocha.class"));
		labFiles.add(new File("./files/Milestone 5/headfirst/Soy.class"));
		labFiles.add(new File("./files/Milestone 5/headfirst/StarbuzzCoffee.class"));
		labFiles.add(new File("./files/Milestone 5/headfirst/Whip.class"));
		UMLGraph labgraph = new UMLGraph("Test_UML", "BT");
		labgraph.addPatternDetector(new DecoratorPatternDetector());

		for (File f : labFiles) {
			InputStream in = new FileInputStream(f);
			ClassReader reader = new ClassReader(in);

			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, labgraph);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, labgraph);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, labgraph);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			in.close();
		}

		labgraph.generateArrows();
		labgraph.detectPatterns();
		for (UMLClass clazz : labgraph.getClasses()) {
			if (clazz.getName().contains("CondimentDecorator")) {
				assertTrue(clazz.getPatternNames().contains("decorator"));
			}
			if (clazz.getName().contains("Beverage")) {
				assertTrue(clazz.getPatternNames().contains("component"));
			}
			if (clazz.getName().contains("Milk")) {
				assertTrue(clazz.getPatternNames().contains("decorator"));
				for (UMLArrow arrow : clazz.getUMLArrows()) {
					if (arrow.isAssociationArrow() && arrow.getEndClass().getName().contains("Beverage")) {
						assertTrue(arrow.getLabel().equals("decorates"));
					}
				}
			}
		}

	}
	
	@Test
	public void testLab5_1() throws IOException {
	
		ArrayList<File> labFiles = new ArrayList<File>();
		String path = "./files/Milestone 5/lab5-1-solution/bin/problem/";
		labFiles.add(new File(path + "client/App.class"));
		labFiles.add(new File(path + "client/IteratorToEnumerationAdapter.class"));
		// labFiles.add(new File(path + "client/IteratorToEnumerationAdapterTest.class"));
		labFiles.add(new File(path + "lib/LinearTransformer.class"));
		// labFiles.add(new File(path + "lib/LinearTransformerTest.class"));

		UMLGraph labgraph = new UMLGraph("Test_UML", "BT");
		labgraph.addPatternDetector(new DecoratorPatternDetector());

		for (File f : labFiles) {
			InputStream in = new FileInputStream(f);
			ClassReader reader = new ClassReader(in);

			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, labgraph);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, labgraph);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, labgraph);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
			in.close();
		}

		ArrayList<String> files = new ArrayList<String>();
		files.add("java.util.Enumeration");
		files.add("java.util.Iterator");
		for (String f : files) {
			ClassReader reader = new ClassReader(f);

			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, labgraph);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, labgraph);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, labgraph);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		}

		labgraph.generateArrows();
		labgraph.detectPatterns();

		// System.out.println(labgraph.toGraphVizString());
		assertTrue(!labgraph.toGraphVizString().contains("decorator"));
		assertTrue(!labgraph.toGraphVizString().contains("decorates"));
		assertTrue(!labgraph.toGraphVizString().contains("component"));
		
	}
	

	@Test
	public void testInputStreamReader() throws IOException {
		ArrayList<String> files = new ArrayList<String>();
		files.add("java.io.InputStreamReader");
		files.add("java.io.Reader");
		files.add("java.io.FileReader");
		files.add("java.io.BufferedReader");
		files.add("java.io.CharArrayReader");
		files.add("java.io.FilterReader");
		files.add("java.io.PipedReader");
		files.add("java.io.StringReader");
		files.add("sun.nio.cs.StreamDecoder");
		files.add("java.io.LineNumberReader");
		
		UMLGraph graph = new UMLGraph("Test_UML", "BT");
		graph.addPatternDetector(new DecoratorPatternDetector());
		
		for (String f : files) {
			ClassReader reader = new ClassReader(f);

			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, graph);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, graph);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, graph);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		}
		graph.generateArrows();
		graph.detectPatterns();
		
		for (UMLClass clazz : graph.getClasses()) {
			if (clazz.getName().contains("BufferedReader")) {
				assertTrue(clazz.getPatternNames().contains("decorator"));
				for (UMLArrow arrow : clazz.getUMLArrows()) {
					if (arrow.isAssociationArrow() && arrow.getEndClass().getName().equals("java/io/Reader")) {
						assertTrue(arrow.getLabel().equals("decorates"));
					}
				}
				
			}
			if (clazz.getName().equals("java/io/Reader")) {
				assertTrue(clazz.getPatternNames().contains("component"));
			}
			if (clazz.getName().contains("LineNumberReader")) {
				assertTrue(clazz.getPatternNames().contains("decorator"));
				
			}
		}
	}

	@Test
	public void testOutputStreamreader() throws IOException {
		
		ArrayList<String> files = new ArrayList<String>();
		files.add("java.io.OutputStreamWriter");
		files.add("java.io.Writer");
		files.add("java.io.FileWriter");
		files.add("java.io.BufferedWriter");
		files.add("java.io.CharArrayWriter");
		files.add("java.io.FilterWriter");
		files.add("java.io.PipedWriter");
		files.add("java.io.PrintWriter");
		files.add("java.io.StringWriter");
		files.add("sun.nio.cs.StreamEncoder");
		
		UMLGraph graph = new UMLGraph("Test_UML", "BT");
		graph.addPatternDetector(new DecoratorPatternDetector());
		
		for (String f : files) {
			ClassReader reader = new ClassReader(f);

			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, graph);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, graph);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, graph);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		}
		graph.generateArrows();
		graph.detectPatterns();
		
		assertTrue(!graph.toGraphVizString().contains("decorator"));
	}

	@Test
	public void testMouseAdapter() throws IOException {
		
		ArrayList<String> files = new ArrayList<String>();
		files.add("java.awt.event.MouseAdapter");
		files.add("javax.swing.event.MouseInputAdapter");
		files.add("java.awt.event.MouseListener");
		files.add("java.awt.event.MouseWheelListener");
		files.add("java.util.EventListener");
		UMLGraph graph = new UMLGraph("Test_UML", "BT");
		graph.addPatternDetector(new DecoratorPatternDetector());
		
		for (String f : files) {
			ClassReader reader = new ClassReader(f);

			ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, graph);
			ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, graph);
			ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, graph);

			reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		}
		graph.generateArrows();
		graph.detectPatterns();
		
		
		
		assertTrue(!graph.toGraphVizString().contains("decorator"));
	}
}
