package milestone5;

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
import problem.asm.UMLGraph;

public class decoratorTest {

	ArrayList<File> labFiles = new ArrayList<File>(); 
	ArrayList<String> files = new ArrayList<String>();
	UMLGraph labgraph;
	UMLGraph graph;
	
	
	
	public decoratorTest() throws IOException {
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
		
		files.add("java.io.InputStreamReader"); 
		files.add("java.io.Reader");
		files.add("java.io.FileReader");
		files.add("java.io.BufferedReader");
		files.add("java.io.CharArrayReader");
		files.add("java.io.FilterReader");
		files.add("java.io.InputStreamReader");
		files.add("java.io.PipedReader");
		files.add("java.io.StringReader");
	
		
		files.add("java.io.OutputStreamWriter");
		files.add("java.io.Writer");
		files.add("java.io.FileWriter"); 
		files.add("java.io.BufferedWriter"); 
		files.add("java.io.CharArrayWriter");
		files.add("java.io.FilterWriter");
		files.add("java.io.PipedWriter");
		files.add("java.io.PrintWriter");
		files.add("java.io.StringWriter");
		
		files.add("java.awt.event.MouseAdapter");
		files.add("javax.swing.event.MouseInputAdapter");
		files.add("java.awt.event.MouseListener"); 
		files.add("java.awt.event.MouseWheelListener");
		files.add("java.util.EventListener");
		
		
		labgraph = new UMLGraph("Test_UML", "BT");
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
		System.out.println(labgraph.toGraphVizString());
		
		graph = new UMLGraph("Test_UML", "BT");
		graph.addPatternDetector(new DecoratorPatternDetector());
		for (String f : files) {
			System.out.println(f);
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
	
	@Test 
	public void testLab2_1() {
		
	}
	
	@Test 
	public void testInputStreamReader() {
		
	}
	
	@Test
	public void testOutputStreamreader() {
		
	}
	
	@Test
	public void testMouseAdapter() {
		
	}
}
