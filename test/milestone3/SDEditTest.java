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

	
	public SDEditTest() {
		
	}
	
	@Test
	public void testCollectionsShuffle() throws IOException {
		ClassReader reader = new ClassReader("java.util.Collections");
		UMLGraph graph = new UMLGraph("Test_SD", "BT");

		ClassVisitor declVisitor = new ClassDeclarationVisitor(Opcodes.ASM5, graph);
		ClassVisitor fieldVisitor = new ClassFieldVisitor(Opcodes.ASM5, declVisitor, graph);
		ClassVisitor methodVisitor = new ClassMethodVisitor(Opcodes.ASM5, fieldVisitor, graph);

		reader.accept(methodVisitor, ClassReader.EXPAND_FRAMES);
		graph.generateCallSequence("java.util.Collections.shuffle(List<T> list)", 5);
		
		String res = graph.toSDEditString();
		assertTrue(res.contains("Random:long=System.nanoTime()"));
		assertTrue(res.contains("Collections:ListIterator=List.listIterator()"));
		assertTrue(res.contains("Collections:Object=ListIterator.next()"));
		assertTrue(res.contains("Random:int=Random.next(int)"));
		
		
	}
}
