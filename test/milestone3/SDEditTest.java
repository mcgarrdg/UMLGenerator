package milestone3;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import problem.asm.ClassDeclarationVisitor;
import problem.asm.ClassFieldVisitor;
import problem.asm.ClassMethodVisitor;
import problem.asm.UMLGraph;

public class SDEditTest {

	UMLGraph graph;
	String res;
	public SDEditTest() throws IOException {
		ClassReader reader = new ClassReader("java.util.Collections");
		UMLGraph graph = new UMLGraph("Test_SD", "BT");

		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, graph);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, graph);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, graph);

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		graph.generateCallSequence("java.util.Collections.shuffle(List<T> list)", 3);
		
		res = graph.toSDEditString();
	}
	
	@Test
	public void testCollectionsShuffle() {
		assertTrue(res.contains("Random:long=System.nanoTime()"));
		assertTrue(res.contains("Collections:ListIterator=List.listIterator()"));
		assertTrue(res.contains("Collections:Object=ListIterator.next()"));
		assertTrue(res.contains("Random:int=Random.next(int)"));
		
	}
	
	@Test 
	public void testCallOrder() {
		String[] split = res.split("Collections:ListIterator=List.listIterator()");
		assertTrue(!split[0].contains("Collections:Object=ListIterator.next()"));
		assertTrue(split[1].contains("Collections:Object=ListIterator.next()"));
		split = res.split("Collections:Random.new()");
		assertTrue(!split[0].contains("Random:long=System.nanoTime()"));
		assertTrue(split[1].contains("Random:long=System.nanoTime()"));
		
	}
		
	@Test
	public void testCallDepth() {
		String[] split = res.split("\n");
		int i =0;
		System.out.println(split.length);
		while( !split[i].equals("Collections:Random.new()")) {
			System.out.println(split[i]);
			i++;
		}
		System.out.println(i);
		assertTrue(split[i].equals("Collections:Random.new()"));
		assertTrue(split[i+1].equals("Random:long=Random.seedUniquifier()"));
	}
}
